package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "stack")
@Accessors(chain = true)
@Data
@ToString
public class Stack implements Serializable {

    private static final long serialVersionUID = -4314326804874L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exercise exercise;
    @ManyToOne
    private AppUser user;
    @Column(length = 5000)
    private String log;
    private Date started;
    private Date closed;
    private Integer score;
    private String answers;
    private String file;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private Integer port;
    private String name;

    public String getLink() {
        String end = "";
        if (this.exercise.getLink() != null) {
            end = this.exercise.getLink();
        }
        return "http://localhost:" + port + end;
    }
}
