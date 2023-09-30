package mobi.foo.com.inpt.cyberrange.controllers.admin;

import lombok.RequiredArgsConstructor;
import mobi.foo.com.inpt.cyberrange.domain.*;
import mobi.foo.com.inpt.cyberrange.repository.QuestionRepository;
import mobi.foo.com.inpt.cyberrange.repository.StackRepository;
import mobi.foo.com.inpt.cyberrange.services.ExerciseService;
import mobi.foo.com.inpt.cyberrange.services.MachineService;
import mobi.foo.com.inpt.cyberrange.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin/exercise")
@RequiredArgsConstructor
public class AdminExerciseController {

    private final ExerciseService exerciseService;
    private final QuestionRepository questionRepository;
    private final MachineService machineService;
    private final UserService userService;
    private final StackRepository stackRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("exercises", exerciseService.list());
        return "admin/exercise/index";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        model.addAttribute("exercise", exerciseService.get(id));
        return "admin/exercise/details";
    }
}
