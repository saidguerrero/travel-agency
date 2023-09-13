package com.devas.travel.agency.infrastructure.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Gson gson = new Gson();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();

        try {
            authCredentials = new ObjectMapper()
                    .readValue(request.getReader(), AuthCredentials.class);

        } catch (IOException e) {

        }
//TODO: checar roles en emptyList
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getUserName(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();


        String token = TokenUtils.createToken(userDetails.getUsername(),
                userDetails.getRole(),
                userDetails.getRoleId());
        //userDetails to json

        String usuario = this.gson.toJson(userDetails.getName());

        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

}
