package mobi.foo.com.inpt.cyberrange.repository;

import mobi.foo.com.inpt.cyberrange.domain.AppUser;
import mobi.foo.com.inpt.cyberrange.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    AppUser findFirstByEmail(String email);
}
