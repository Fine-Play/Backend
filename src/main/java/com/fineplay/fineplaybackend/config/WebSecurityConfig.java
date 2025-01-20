// 일단.. 이게 안됨
package com.fineplay.fineplaybackend.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.fineplay.fineplaybackend.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

// 수정... 어렵다 ㅠ
@Configurable
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .cors(withDefaults()) // CORS 설정
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/auth/**", "/api/search/**", "/file/**").permitAll() // 특정 요청 허용
                        .requestMatchers(HttpMethod.GET, "/api/v1/**", "/api/user/*").permitAll() // GET 요청 허용
                        .anyRequest().authenticated() // 그 외 인증 필요
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint()) // 인증 실패 핸들링
                );
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

// 인증 실패 처리
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed.\" }");
    }
}
