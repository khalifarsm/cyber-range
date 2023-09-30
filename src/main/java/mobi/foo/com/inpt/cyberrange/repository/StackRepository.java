package mobi.foo.com.inpt.cyberrange.repository;

import mobi.foo.com.inpt.cyberrange.domain.Stack;
import mobi.foo.com.inpt.cyberrange.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StackRepository extends JpaRepository<Stack, Long> {
    Stack findFirstByExerciseIdAndStatus(Long id, Status status);

    Stack findFirstByUserIdAndStatus(Long id, Status status);

    List<Stack> findByUserId(Long id);

    List<Stack> findByUserIdAndStatus(Long id,Status status);
    List<Stack> findByStatus(Status status);
}
