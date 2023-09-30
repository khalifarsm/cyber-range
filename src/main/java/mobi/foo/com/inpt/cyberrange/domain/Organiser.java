package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "organisers")
@Accessors(chain = true)
@Data
public class Organiser extends AppUser {
    public Organiser() {
        this.setRole(Role.ORGANIZER);
    }
}
