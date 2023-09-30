package mobi.foo.com.inpt.cyberrange.services;

import mobi.foo.com.inpt.cyberrange.config.CyberRangePasswordEncoder;
import mobi.foo.com.inpt.cyberrange.domain.Role;
import mobi.foo.com.inpt.cyberrange.domain.AppUser;
import mobi.foo.com.inpt.cyberrange.domain.User;
import mobi.foo.com.inpt.cyberrange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CyberRangePasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findFirstByEmail(username);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.roles(user.getRole().toString());
            builder.disabled(!user.getEnabled());
            builder.password(user.getPassword());

        } else {
            //logger.info(messageService.get("error.loginNotValid"));
            throw new UsernameNotFoundException("user not found");
        }
        return builder.build();
    }

    public AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            AppUser user = new AppUser();
            user.setEmail("Anounymous");
            return user;
        }
        String currentPrincipalName = authentication.getName();
        AppUser user = userRepository.findFirstByEmail(currentPrincipalName);
        return user;
    }

    public AppUser save(User user) {
        user.setRole(Role.USER);
        user.setEnabled(true);
        AppUser old = userRepository.findFirstByEmail(user.getEmail());
        if (old == null || !old.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getId() == null) {
            user.setCreated(new Date());
        } else {
            user.setUpdated(new Date());
        }
        return userRepository.save(user);
    }
}
