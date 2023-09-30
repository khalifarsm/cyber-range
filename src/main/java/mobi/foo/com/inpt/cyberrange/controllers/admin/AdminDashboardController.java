package mobi.foo.com.inpt.cyberrange.controllers.admin;

import lombok.RequiredArgsConstructor;
import mobi.foo.com.inpt.cyberrange.domain.Stack;
import mobi.foo.com.inpt.cyberrange.domain.Status;
import mobi.foo.com.inpt.cyberrange.repository.QuestionRepository;
import mobi.foo.com.inpt.cyberrange.repository.StackRepository;
import mobi.foo.com.inpt.cyberrange.services.ExerciseService;
import mobi.foo.com.inpt.cyberrange.services.MachineService;
import mobi.foo.com.inpt.cyberrange.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final ExerciseService exerciseService;
    private final QuestionRepository questionRepository;
    private final MachineService machineService;
    private final UserService userService;
    private final StackRepository stackRepository;

    @GetMapping("")
    public String list(Model model) {
        List<Stack> stacks = stackRepository.findAll();
        int completed = 0;
        int score = 0;
        for (Stack stack : stacks) {
            completed++;
            if(stack.getStatus() == Status.RUNNING){
                score++;
            }
        }
        model.addAttribute("exercises", exerciseService.list().size());
        model.addAttribute("completed", completed);
        model.addAttribute("score", score);
        return "admin/index";
    }
}
