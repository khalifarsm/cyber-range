package mobi.foo.com.inpt.cyberrange.controllers.anonyme;

import lombok.RequiredArgsConstructor;
import mobi.foo.com.inpt.cyberrange.domain.AppUser;
import mobi.foo.com.inpt.cyberrange.domain.User;
import mobi.foo.com.inpt.cyberrange.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String signup(@Valid User user) {
        userService.save(user);
        return "redirect:login";
    }

    @RequestMapping(value = "")
    public String home() throws IOException {
        return "redirect:/login";
    }

}
