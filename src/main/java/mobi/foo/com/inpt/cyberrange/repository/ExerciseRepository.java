package mobi.foo.com.inpt.cyberrange.repository;

import mobi.foo.com.inpt.cyberrange.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
