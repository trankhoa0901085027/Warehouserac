package com.warehousemanagementsystem.warehousemanagementsystem.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class AccountAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1,
                                        Authentication authentication) throws IOException, ServletException {

        boolean hasUserRole = false;
        boolean hasAdminRole = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                hasUserRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
                break;
            }
        }

        if (hasUserRole) {
//            HttpSession session =arg0.getSession();
//           String a = (String) session.getAttribute("phuc");
//            System.out.println("phuc" + a);
            redirectStrategy.sendRedirect(arg0, arg1, "/user");
        } else if (hasAdminRole) {
            redirectStrategy.sendRedirect(arg0, arg1, "/admin");
        } else {
            throw new IllegalStateException();
        }
    }

}