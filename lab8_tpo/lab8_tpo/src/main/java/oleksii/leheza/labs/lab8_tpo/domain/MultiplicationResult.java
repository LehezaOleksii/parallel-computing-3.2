package oleksii.leheza.labs.lab8_tpo.domain;


import lombok.Getter;
import lombok.Setter;
import oleksii.leheza.labs.lab8_tpo.multiplication.Matrix;

@Getter
@Setter
public class MultiplicationResult {

    private static Long staticId = 1L;

    public MultiplicationResult(Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix, double calculationTime) {
        id = staticId++;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.calculationTime = calculationTime;
    }

    private Long id;
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private Matrix resultMatrix;
    private double calculationTime;
}
