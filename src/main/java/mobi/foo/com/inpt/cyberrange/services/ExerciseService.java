package mobi.foo.com.inpt.cyberrange.services;

import mobi.foo.com.inpt.cyberrange.domain.Exercise;
import mobi.foo.com.inpt.cyberrange.domain.Stack;
import mobi.foo.com.inpt.cyberrange.domain.Status;
import mobi.foo.com.inpt.cyberrange.repository.ExerciseRepository;
import mobi.foo.com.inpt.cyberrange.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final StackRepository stackRepository;

    public List<Exercise> list() {
        return exerciseRepository.findAll();
    }

    public Exercise get(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    public Stack getLab(Long id) {
        return stackRepository.findFirstByExerciseIdAndStatus(id, Status.RUNNING);
    }
}
