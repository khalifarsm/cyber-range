package mobi.foo.com.inpt.cyberrange.controllers.admin;

import lombok.RequiredArgsConstructor;
import mobi.foo.com.inpt.cyberrange.repository.AppUserRepository;
import mobi.foo.com.inpt.cyberrange.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AppUserRepository userRepository;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "admin/user";
    }
}
