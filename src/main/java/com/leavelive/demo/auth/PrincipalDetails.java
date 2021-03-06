package com.leavelive.demo.auth;

// 시큐리티가 /login 주소를 낚아채 로그인을 진행시킴.
// 로그인 진행이 완료되면 session에 넣어줌 (Security ContextHolder)
// 오브젝트 -> Authentication 타입의 객체
// Authentication 안에는 User정보가 있음
// User 오브젝트의 타입은 UserDetails 타입 객체

import com.leavelive.demo.domain.Role;
import com.leavelive.demo.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

// 시큐리티가 가지고 있는 세션(Security Session) -> Authentication 객체 -> UserDetails 객체
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; //콤포지션
    private Map<String,Object> attributes;

    // 일반 로그인
    public PrincipalDetails(User user){
        this.user=user;
    }
    // OAuth 로그인
    public PrincipalDetails(User user,Map<String,Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("principalDetails 확인 :: "+user);
        Collection<GrantedAuthority> collect= new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(user.getRole());
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
