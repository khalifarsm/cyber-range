package mobi.foo.com.inpt.cyberrange.config;

import mobi.foo.com.inpt.cyberrange.domain.Role;
import mobi.foo.com.inpt.cyberrange.domain.AppUser;
import mobi.foo.com.inpt.cyberrange.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AppUser user;
        try {
            user = userService.getAuthenticatedUser();
            if (user.getRole() == Role.ADMIN) {
                httpServletResponse.sendRedirect("/admin");
            } else if (user.getRole() == Role.ORGANIZER) {
                httpServletResponse.sendRedirect("/organizer");
            } else if (user.getRole() == Role.USER) {
                httpServletResponse.sendRedirect("/user");
            } else {
                httpServletResponse.sendRedirect("/login");
            }
        } catch (Exception e) {
            httpServletResponse.sendRedirect("/login");
        }
    }

}