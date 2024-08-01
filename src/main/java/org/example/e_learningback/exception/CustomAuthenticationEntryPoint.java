package org.example.e_learningback.exception;

import jakarta.servlet.ServletException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {



    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
        } else if (authException instanceof DisabledException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account is disabled.");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.");
        }
    }
}
