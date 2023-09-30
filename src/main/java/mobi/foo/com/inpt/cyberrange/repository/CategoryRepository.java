package mobi.foo.com.inpt.cyberrange.repository;

import mobi.foo.com.inpt.cyberrange.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
