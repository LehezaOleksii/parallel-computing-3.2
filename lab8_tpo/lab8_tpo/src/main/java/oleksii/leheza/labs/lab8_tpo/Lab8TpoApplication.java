package oleksii.leheza.labs.lab8_tpo;

import com.fasterxml.jackson.databind.ObjectMapper;
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
            int size = 2;
            int[][] matrix = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = 1;
                }
            }
            // Convert the matrix to JSON format
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Convert the matrix to JSON string
                String jsonMatrix = mapper.writeValueAsString(matrix);

                // Print the JSON string
                System.out.println(jsonMatrix);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
