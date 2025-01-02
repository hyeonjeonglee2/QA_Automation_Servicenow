/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 이현정
 * Create Date : 2024. 11. 06
 * DESC : JwtAuthenticationFilter.java
*****************************************************************/
package kr.co.ecoletree.commons.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;

@Order(1)
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Key key; // JWT 검증을 위한 Key

    public JwtAuthenticationFilter(Key key) {
        this.key = key;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && validateToken(token)) {
            // 유효한 토큰인 경우, 다음 필터로 진행
            filterChain.doFilter(request, response);
        } else {
            // 유효하지 않은 토큰의 경우 403 상태 코드 반환
            log.info("Invalid or missing token");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid JWT token");
        }
    }

    // Request Header 에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증 메서드
    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}