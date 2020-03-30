package io.github.housingfinancial.auth.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import io.github.housingfinancial.auth.config.JwtTokenProvider;
import io.github.housingfinancial.auth.domain.User;
import io.github.housingfinancial.auth.exception.InvalidJwtAuthenticationException;
import io.github.housingfinancial.auth.presentation.vo.AuthenticationRequest;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    /**
     * 가입된 유저가 로그인해서 유저 인증이 완료했을 경우, 유저 jwt 토큰을 발급하여 유저에게 전달해준다.
     */
    public ResponseEntity authenticateUser(AuthenticationRequest authRequest, HttpServletResponse res) {
        String userName = authRequest.getUserId();
        final Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(userName,
                                                    userService.findByUserId(userName)
                                                               .orElseThrow(() ->
                                                                                    new UsernameNotFoundException("Username " + userName + " not found"))
                                                               .getUserAuthorizations()
                                                               .stream()
                                                               .map(userAuthorization ->
                                                                            userAuthorization.getRoleName())
                                                               .collect(Collectors.toList()));
        jwtTokenProvider.addTokenInHeader(token, res);
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userName);
        model.put("token", token);
        return ok(model);
    }

    public ResponseEntity signUp(AuthenticationRequest authRequest, HttpServletResponse res) {
        userService.signUp(new User(authRequest.getUserId(), authRequest.getPassword()));
        return authenticateUser(authRequest, res);
    }

    public void signIn(AuthenticationRequest authRequest, HttpSession session, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authRequest.getUserId(), authRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                                 SecurityContextHolder.getContext());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("login fail", e);
        }
    }
}
