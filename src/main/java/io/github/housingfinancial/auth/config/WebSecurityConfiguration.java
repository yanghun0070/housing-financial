package io.github.housingfinancial.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import io.github.housingfinancial.auth.service.LoginUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;

    public WebSecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Security 설정 값 무시
     * @param web
     * @throws Exception
     */
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "/resources/**");
    }

    /**
     * 인가, 로그인, 로그아웃 설정
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
            .frameOptions().sameOrigin()
            .httpStrictTransportSecurity().disable();

        http
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/auth/signin", "/auth/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().apply(new JwtConfigurer(jwtTokenProvider));

        http
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider),
                                 UsernamePasswordAuthenticationFilter.class);
    }

//
//    @Bean
//    public JwtTokenFilter authenticationTokenFilterBean() {
//        return new JwtTokenFilter(jwtTokenProvider);
//    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 인증 처리 관련 설정
     */
    @Configuration
    static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {

        private UserDetailsService userDetailsService;

        public AuthenticationConfiguration(LoginUserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        /**
         * 인증 처리 관련 설정
         * 인증 처리를 위해 사용자를 가져오는 userDetailsService의 암호를 대조할 때 사용하는
         * PasswordEncoder 를 설정
         * @param auth
         * @throws Exception
         */
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        }
    }

}
