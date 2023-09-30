package mobi.foo.com.inpt.cyberrange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CyberRangeApplication {

	public static void main(String[] args) {
		Animal animal= new Cat();
		animal.setName("name");
		Cat cat = (Cat) animal;
		cat.setVoice("voice");
		SpringApplication.run(CyberRangeApplication.class, args);
	}

}
