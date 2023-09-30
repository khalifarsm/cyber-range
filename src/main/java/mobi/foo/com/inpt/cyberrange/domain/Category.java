package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category")
@Accessors(chain = true)
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = -4314326804874L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
}
