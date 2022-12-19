package com.example.api.configuration.security;

import com.example.api.domain.Member.Member;
import com.example.api.domain.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Member member = memberRepository.findByClientId(name)
                .orElseThrow(NullPointerException::new);

        if (!member.isMatchPassword(password)) {

        }

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(member.getRoles());

        return new UsernamePasswordAuthenticationToken(name, password,
                new ArrayList<>(Collections.singletonList(simpleGrantedAuthority)));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}