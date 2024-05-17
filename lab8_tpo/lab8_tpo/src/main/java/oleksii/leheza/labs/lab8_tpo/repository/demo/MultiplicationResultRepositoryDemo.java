package oleksii.leheza.labs.lab8_tpo.repository.demo;

import lombok.Getter;
import oleksii.leheza.labs.lab8_tpo.domain.MultiplicationResult;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class MultiplicationResultRepositoryDemo {

    private List<MultiplicationResult> results = new ArrayList<>();

    public void addResult(MultiplicationResult result) {
        results.add(result);
    }
}
