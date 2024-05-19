package oleksii.leheza.labs.lab8_tpo.service;

import lombok.RequiredArgsConstructor;
import oleksii.leheza.labs.lab8_tpo.domain.MultiplicationResult;
import oleksii.leheza.labs.lab8_tpo.domain.MultiplicationResultDto;
import oleksii.leheza.labs.lab8_tpo.multiplication.FoxMatrixMultiplication;
import oleksii.leheza.labs.lab8_tpo.multiplication.Matrix;
import oleksii.leheza.labs.lab8_tpo.repository.demo.MultiplicationResultRepositoryDemo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatrixMultiplicationService {

    private Map<Integer, Matrix> matrixMap1 = new HashMap<>();
    private Map<Integer, Matrix> matrixMap2 = new HashMap<>();
    private final MultiplicationResultRepositoryDemo repository;
    private FoxMatrixMultiplication foxMatrixMultiplication = new FoxMatrixMultiplication();

    public Matrix multiply(Matrix firstMatrix, Matrix secondMatrix, ZonedDateTime startTime) {
        Matrix result = new Matrix(firstMatrix.matrix.length);
        foxMatrixMultiplication.foxMatrixMultiply(firstMatrix, secondMatrix, result, 4);
        Duration duration = Duration.between(startTime, ZonedDateTime.now());
        double processingTimeMillis = duration.toMillis();
        repository.addResult(new MultiplicationResult(firstMatrix, secondMatrix, result, processingTimeMillis));
        return result;
    }

    public Matrix multiply(int matrixSize, ZonedDateTime startTime) {
        Matrix matrix1 = matrixMap1.get(matrixSize);
        Matrix matrix2 = matrixMap2.get(matrixSize);
        Matrix result = new Matrix(matrixSize);
        foxMatrixMultiplication.foxMatrixMultiply(matrix1, matrix2, result, 4);
        Duration duration = Duration.between(startTime, ZonedDateTime.now());
        double processingTimeMillis = duration.toMillis();
        repository.addResult(new MultiplicationResult(matrix1, matrix2, result, processingTimeMillis));
        return result;
    }

    public void addMatrix(int... sizes) {
        for (int size : sizes) {
            Matrix matrix = new Matrix(size);
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix.matrix[i][j] = 1;
                }
            }
            matrixMap1.put(size, matrix);
        }
        for (int size : sizes) {
            Matrix matrix = new Matrix(size);
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix.matrix[i][j] = 1;
                }
            }
            matrixMap2.put(size, matrix);
        }
        System.out.println("Finish initialize server matrix");
    }

    public List<MultiplicationResultDto> getResult() {
        return repository.getResults().stream()
                .map(this::multiplicationResultToDto)
                .collect(Collectors.toList());
    }

    private MultiplicationResultDto multiplicationResultToDto(MultiplicationResult result) {
        return new MultiplicationResultDto(
                result.getId(),
                result.getFirstMatrix().getMatrixSize(),
                result.getSecondMatrix().getMatrixSize(),
                result.getResultMatrix().getMatrixSize(),
                result.getCalculationTime());
    }

    public Matrix getLastResult() {
        return repository.getResults().get(repository.getResults().size() - 1).getResultMatrix();
    }
}
