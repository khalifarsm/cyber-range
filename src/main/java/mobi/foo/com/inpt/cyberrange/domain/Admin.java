package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@Accessors(chain = true)
@Data
public class Admin extends AppUser {
    public Admin() {
        this.setRole(Role.ADMIN);
    }
}
