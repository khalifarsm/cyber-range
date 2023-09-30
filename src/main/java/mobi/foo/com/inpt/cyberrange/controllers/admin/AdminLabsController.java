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
@RequestMapping("/admin/labs")
@RequiredArgsConstructor
public class AdminLabsController {

    private final ExerciseService exerciseService;
    private final QuestionRepository questionRepository;
    private final MachineService machineService;
    private final UserService userService;
    private final StackRepository stackRepository;

    @GetMapping("")
    public String list(Model model) {
        List<Stack> stacks = stackRepository.findAll();
        model.addAttribute("labs", stacks);
        return "admin/labs/index";
    }

    @GetMapping("/running")
    public String running(Model model) {
        List<Stack> stacks = stackRepository.findByStatus(Status.RUNNING);
        model.addAttribute("labs", stacks);
        return "admin/labs/running";
    }

    @GetMapping("/close")
    public String close(Model model) {
        List<Stack> stacks = stackRepository.findByStatus(Status.RUNNING);
        for(Stack stack:stacks){
            machineService.close(stack);
        }
        return "redirect:/admin";
    }
}
