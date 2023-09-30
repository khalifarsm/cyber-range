package mobi.foo.com.inpt.cyberrange.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CyberRangePasswordEncoder extends BCryptPasswordEncoder {
}
