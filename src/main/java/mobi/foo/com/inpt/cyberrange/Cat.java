package mobi.foo.com.inpt.cyberrange;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Cat extends Animal{
    private String voice;
}
