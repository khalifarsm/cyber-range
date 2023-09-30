package mobi.foo.com.inpt.cyberrange.controllers.user;

import mobi.foo.com.inpt.cyberrange.domain.*;
import mobi.foo.com.inpt.cyberrange.repository.QuestionRepository;
import mobi.foo.com.inpt.cyberrange.repository.StackRepository;
import mobi.foo.com.inpt.cyberrange.services.ExerciseService;
import mobi.foo.com.inpt.cyberrange.services.MachineService;
import mobi.foo.com.inpt.cyberrange.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/user/exercise")
@RequiredArgsConstructor
public class UserExerciseController {

    private final ExerciseService exerciseService;
    private final QuestionRepository questionRepository;
    private final MachineService machineService;
    private final UserService userService;
    private final StackRepository stackRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("exercises", exerciseService.list());
        model.addAttribute("lab", stackRepository.findFirstByUserIdAndStatus(userService.getAuthenticatedUser().getId(), Status.RUNNING));
        return "user/exercise/index";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        model.addAttribute("exercise", exerciseService.get(id));
        return "user/exercise/details";
    }

    @GetMapping("/lab/{id}")
    public String lab(Model model, @PathVariable("id") Long id) {
        Stack lab = exerciseService.getLab(id);
        if (lab == null) {
            Exercise exercise = exerciseService.get(id);
            AppUser user = userService.getAuthenticatedUser();
            lab = machineService.run(exercise, user);
        }
        model.addAttribute("lab", lab);
        return "user/exercise/lab";
    }

    @GetMapping("/lab/close/{id}")
    public String close(Model model, @PathVariable("id") Long id) {
        Stack lab = exerciseService.getLab(id);
        machineService.close(lab);
        return "redirect:/user/exercise";
    }

    @GetMapping("/answer/{id}")
    public String response(Model model, @PathVariable("id") Long id) {
        Stack lab = exerciseService.getLab(id);
        if (lab == null) {
            return "redirect:/user/exercise";
        }
        model.addAttribute("lab", lab);
        model.addAttribute("questions", questionRepository.findByExerciseId(id));
        return "user/exercise/answer";
    }

    @PostMapping("/answer")
    public String response(Model model, HttpServletRequest request) {
        String exerciseId = request.getParameter("exercise");
        String questionId = request.getParameter("question");
        String answer = request.getParameter("answer");
        Question question = questionRepository.findById(Long.valueOf(questionId)).orElse(null);
        if (question.getAnswer().equalsIgnoreCase(answer)) {
            Stack lab = exerciseService.getLab(Long.valueOf(exerciseId));
            String answersString = lab.getAnswers();
            if (answersString == null) answersString = "";
            List<String> answers = Arrays.asList(answersString.split(";"));
            if (!answers.contains(questionId)) {
                lab.setScore(lab.getScore() + score(lab, question));
                if (lab.getAnswers() == null) {
                    lab.setAnswers(questionId);
                } else {
                    lab.setAnswers(lab.getAnswers() + ";" + questionId);
                }
            }
            stackRepository.save(lab);
        }
        return "redirect:/user/exercise/answer/" + exerciseId;
    }

    public int score(Stack lab, Question question) {
        int points = question.getPoints();
        long started = lab.getStarted().getTime();
        long time = new Date().getTime() - started;
        time = time - (10 * 60 * 1000);
        if (time > 0) {
            return (int) (points * time / lab.getExercise().getTimeToClose());
        } else {
            return points;
        }
    }
}
