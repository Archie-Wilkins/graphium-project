package client_project.y2s1.team2.graphium.Security;

import client_project.y2s1.team2.graphium.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class userAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    AuditService auditService;

    // https://www.baeldung.com/spring_redirect_after_login
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException, ServletException {

        String errorMessage = authenticationException.getMessage();
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        auditService.userLoggedIn("invalid_username",false, errorMessage + "- Username:" + username + ", Password: " + password + " - handled by userAuthenticationSuccessHandler within WebSecurityConfig");
//        redirect to error

        getRedirectStrategy().sendRedirect(request, response, "/login?error=true");
    }
}
