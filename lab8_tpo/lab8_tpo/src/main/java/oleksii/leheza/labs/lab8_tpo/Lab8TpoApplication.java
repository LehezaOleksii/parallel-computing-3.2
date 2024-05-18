package oleksii.leheza.labs.lab8_tpo;

import oleksii.leheza.labs.lab8_tpo.service.MatrixMultiplicationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab8TpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab8TpoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(MatrixMultiplicationService service) {
        return args -> {
            service.addMatrix(500, 1000, 1500, 2000, 3000);
        };
    }
}
