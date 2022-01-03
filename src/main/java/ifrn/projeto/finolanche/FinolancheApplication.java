package ifrn.projeto.finolanche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FinolancheApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinolancheApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("12345"));
	}

}
