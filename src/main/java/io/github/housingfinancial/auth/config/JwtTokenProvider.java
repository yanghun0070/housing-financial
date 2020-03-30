package io.github.housingfinancial.auth.config;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.github.housingfinancial.auth.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {


    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    /**
     * JWT Token 생성
     * @param username 유저명
     * @param roles 권한명 목록
     * @return
     */
    public String createToken(String username, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(validity)
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compact();
    }

    /**
     * JWT Subject 명(유저명) 을 얻는다.
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).
                parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    /**
     *
     * @param token
     * @return
     */
    @Transactional
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                                                       userDetails.getAuthorities());
    }

    /**
     *
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization"); //권한 부여
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * Token 유효성 검사
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }


    /**
     * Request Header 안에 Token 을 추가시킨다.
     * @param token jwt token
     */
    public void addTokenInHeader(String token, HttpServletResponse res) {
        res.addHeader("Authorization", "Bearer " + token);
    }


}
