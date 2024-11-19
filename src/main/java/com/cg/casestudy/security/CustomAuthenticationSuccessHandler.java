package com.cg.casestudy.security;

import com.cg.casestudy.models.user.Role;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Get the email of the user who logged in
        String email = authentication.getName();

        User theUser = userService.findByEmail(email);

        // Set the user in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        List<Role> roles = theUser.getRoles();

        if(roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            response.sendRedirect(request.getContextPath() + "/admin/home");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/newsfeed");
    }
}
