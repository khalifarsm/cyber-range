package mobi.foo.com.inpt.cyberrange.repository;

import mobi.foo.com.inpt.cyberrange.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
