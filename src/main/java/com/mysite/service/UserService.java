package com.mysite.service;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 현재 인증된 사용자의 ID 가져오기
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null; // 인증되지 않은 경우 null 반환
    }

    // 현재 인증된 사용자의 권한(Role) 가져오기
    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Iterator<? extends GrantedAuthority> iter = authorities.iterator();
            if (iter.hasNext()) {
                GrantedAuthority authority = iter.next();
                return authority.getAuthority();
            }
        }
        return null; // 권한이 없거나 인증되지 않은 경우 null 반환
    }
}
