package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Accessors(chain = true)
@Data
public class User extends AppUser {
    private String firstName;
    private String lastName;
    public User() {
        this.setRole(Role.USER);
    }
}
