package mobi.foo.com.inpt.cyberrange.controllers.user;

import mobi.foo.com.inpt.cyberrange.repository.QuestionRepository;
import mobi.foo.com.inpt.cyberrange.repository.StackRepository;
import mobi.foo.com.inpt.cyberrange.services.ExerciseService;
import mobi.foo.com.inpt.cyberrange.services.MachineService;
import mobi.foo.com.inpt.cyberrange.services.UserService;
import lombok.RequiredArgsConstructor;
import mobi.foo.com.inpt.cyberrange.domain.Stack;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDashboardController {

    private final ExerciseService exerciseService;
    private final QuestionRepository questionRepository;
    private final MachineService machineService;
    private final UserService userService;
    private final StackRepository stackRepository;

    @GetMapping("")
    public String list(Model model) {
        List<Stack> stacks = stackRepository.findByUserId(userService.getAuthenticatedUser().getId());
        int completed = 0;
        int score = 0;
        for (Stack stack : stacks) {
            completed++;
            score += stack.getScore();
        }
        model.addAttribute("exercises", exerciseService.list().size());
        model.addAttribute("completed", completed);
        model.addAttribute("score", score);
        return "user/index";
    }
}
