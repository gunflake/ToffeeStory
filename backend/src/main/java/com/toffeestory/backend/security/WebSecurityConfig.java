package com.toffeestory.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Spring Security에서 권장하는 패스워드 인코더 방식 적용, 자세한건 Spring Security Docs 참조 (목차 10.10.2 DelegatingPasswordEncoder)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Spring Security에서 세션을 만들거나 사용하지 않음
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/accounts/**", "/api/test/**", "/api/images/**").permitAll() // '/api/account/**' 관련 요청만 인증 없이 조회 가능
                    .antMatchers("/api/posts/**").permitAll() // allow every URI, that begins with '/api/user/'
                    //.antMatchers("/api/secure/**").hasRole("USER") // 기본적으로 ROLE_* < 로 검색한다. 따라서 ROLE_ 을 붙여서 권한을 등록해줘야함
                    .anyRequest().authenticated() // '/api/account/**' 을 제외한 모든 요청에 인증이 필요함
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider)); // Jwt 인증 방식 적용
    }
}
