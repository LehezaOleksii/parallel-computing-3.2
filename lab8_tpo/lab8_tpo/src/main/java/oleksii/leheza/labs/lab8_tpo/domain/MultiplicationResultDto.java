package oleksii.leheza.labs.lab8_tpo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultiplicationResultDto {

    private static Long staticId = 1L;

    private Long id;
    private int firstMatrixSize;
    private int secondMatrixSize;
    private int resultSize;
    private double calculationTime;

    public MultiplicationResultDto(long id, int firstMatrixSize, int secondMatrixSize, int resultSize, double calculationTime) {
        this.id = id;
        this.firstMatrixSize = firstMatrixSize;
        this.secondMatrixSize = secondMatrixSize;
        this.resultSize = resultSize;
        this.calculationTime = calculationTime;
    }
}