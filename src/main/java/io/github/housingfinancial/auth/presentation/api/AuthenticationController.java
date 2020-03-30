package io.github.housingfinancial.auth.presentation.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.housingfinancial.auth.presentation.vo.AuthenticationRequest;
import io.github.housingfinancial.auth.service.AuthenticationService;
import io.github.housingfinancial.bank.presentation.api.BankController;
import io.github.housingfinancial.common.presentation.vo.GlobalMessage;
import io.github.housingfinancial.common.presentation.vo.Result;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final MessageSourceAccessor messageSourceAccessor;

    public AuthenticationController(AuthenticationService authenticationService,
                                    MessageSourceAccessor messageSourceAccessor) {
        this.authenticationService = authenticationService;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @PostMapping("signup")
    public Result<ResponseEntity> signUp(@RequestBody AuthenticationRequest authRequest,
                                         HttpServletResponse response) {
        ResponseEntity responseEntity = authenticationService.signUp(authRequest, response);
        Result<ResponseEntity> result = new Result<>(responseEntity);
        result.add(
                linkTo(methodOn(AuthenticationController.class).signUp(authRequest, response)).withSelfRel());
        return result;
    }

    @PostMapping("signin")
    public Result<GlobalMessage> signIn(@RequestBody AuthenticationRequest authRequest,
                                         HttpSession session,
                                         HttpServletResponse response) {
        authenticationService.signIn(authRequest, session, response);
        GlobalMessage globalMessage = new GlobalMessage(HttpStatus.OK.value(),
                                                        messageSourceAccessor.getMessage(String.valueOf(HttpStatus.OK.value())));
        Result<GlobalMessage> result = new Result<>(globalMessage);
        result.add(
                linkTo(methodOn(AuthenticationController.class).signIn(authRequest, session, response)).withSelfRel());
        return result;
    }
}