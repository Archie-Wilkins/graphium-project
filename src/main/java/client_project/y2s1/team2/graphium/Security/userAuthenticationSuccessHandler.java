package client_project.y2s1.team2.graphium.Security;

import client_project.y2s1.team2.graphium.service.auditing.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class userAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    AuditService auditService;

    // https://www.baeldung.com/spring_redirect_after_login
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException{

        String username = authentication.getName();
        auditService.userLoggedIn(username,true, username + " has sucessfully logged in via the login in page - handled by userAuthenticationSuccessHandler within WebSecurityConfig");
        response.sendRedirect("/");
    }
}
