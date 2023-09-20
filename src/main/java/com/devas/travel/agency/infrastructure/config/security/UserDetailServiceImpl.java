package com.devas.travel.agency.infrastructure.config.security;

import com.devas.travel.agency.infrastructure.adapter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var usuario = userRepository
                .findByUserLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
        return new UserDetailsImpl(usuario);

    }

}
