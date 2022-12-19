package com.example.api.configuration.security;

import com.example.api.application.auth.TokenDto;
import com.example.api.domain.Member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private String secretKey = "barogo/AES256/UTF-8";
    private Long accessExpirationTime = 60 * 60 * 24 * 7L;
    private Long refreshExpirationTime = 60 * 60 * 24 * 7 * 30L;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date validity = new Date(now + accessExpirationTime);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");

        return new TokenDto(Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeader(headerMap)
                .setSubject("security-jwt")
                .claim("user_name", authentication.getName())
                .claim("Bearer", authorities)
                .setExpiration(validity)
                .compact(), validity.toInstant().toEpochMilli());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(
                claims.get("Bearer").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Member principal = new Member(claims.get("user_name").toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return true;
    }
}
