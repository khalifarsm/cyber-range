package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "app_users")
@Accessors(chain = true)
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser implements Serializable {

    private static final long serialVersionUID = -4314326804874L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private Boolean enabled;
    private Date created;
    private Date updated;
}
