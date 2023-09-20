package com.devas.travel.agency.infrastructure.config.security;


import com.devas.travel.agency.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();

    }

    @Override
    public String getPassword() {
        return user.getPassword();

    }

    @Override
    public String getUsername() {
        return user.getUserLogin();

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

    public String getName() {
        return user.getFullName();

    }

    public String getRole() {
        return user.getRole().getName();

    }

    public int getRoleId() {
        return user.getRole().getRoleId();

    }
}
