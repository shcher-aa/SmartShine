package com.smartshine.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String redirectUrl = "/";

        // Получаем роли пользователя
        var authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/admin/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            redirectUrl = "/manager/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            redirectUrl = "/employee/dashboard";
        }

        response.sendRedirect(redirectUrl);
    }
}