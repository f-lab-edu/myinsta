package com.example.myinsta.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


public class AccountsDao {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleDao{
        private Long idAccount;
        private Long idAuthority;
        private String role;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDetailsDao implements UserDetails {
        private Long idAccount;
        private String email;
        private String nickName;
        private String password;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private RoleDao role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities () {
            ArrayList<GrantedAuthority> roleList = new ArrayList<>();
            roleList.add(new SimpleGrantedAuthority(role.getRole()));
            return roleList;
        }

        @Override
        public String getUsername () {
            return email;
        }
        @Override
        public boolean isAccountNonExpired () {
            return false;
        }

        @Override
        public boolean isAccountNonLocked () {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired () {
            return false;
        }

        @Override
        public boolean isEnabled () {
            return false;
        }
    }

}
