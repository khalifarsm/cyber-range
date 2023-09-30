package mobi.foo.com.inpt.cyberrange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "exercise")
@Accessors(chain = true)
@Data
public class Exercise implements Serializable {

    private static final long serialVersionUID = -4314326804874L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private String timeToComplete;
    private long timeToClose;
    @Column(length = 5000)
    private String Description;
    @Column(length = 5000)
    private String todo;
    @Column(length = 5000)
    private String prohibited;
    private String solution;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    private String script;
    private String link;
}
