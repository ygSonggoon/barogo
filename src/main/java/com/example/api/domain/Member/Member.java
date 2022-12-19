package com.example.api.domain.Member;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_password")
    private String memberPassword;

    @Transient
    private String roles;

    public Member(String user_name, String s, List<SimpleGrantedAuthority> authorities) {

    }

    public boolean isMatchPassword(String inputPassword) {
        return true;
    }

}
