package mobi.foo.com.inpt.cyberrange;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Animal {
    private String name;
}
