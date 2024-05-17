package oleksii.leheza.labs.lab8_tpo.service;

import lombok.RequiredArgsConstructor;
import oleksii.leheza.labs.lab8_tpo.domain.MultiplicationResult;
import oleksii.leheza.labs.lab8_tpo.multiplication.FoxMatrixMultiplication;
import oleksii.leheza.labs.lab8_tpo.multiplication.Matrix;
import oleksii.leheza.labs.lab8_tpo.repository.demo.MultiplicationResultRepositoryDemo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatrixMultiplicationService {

    private Map<Integer, Matrix> matrixMap1 = new HashMap<>();
    private Map<Integer, Matrix> matrixMap2 = new HashMap<>();
    private final MultiplicationResultRepositoryDemo repository;
    private FoxMatrixMultiplication foxMatrixMultiplication = new FoxMatrixMultiplication();

    public Matrix multiply(Matrix firstMatrix, Matrix secondMatrix) {
        Matrix result = new Matrix(firstMatrix.matrix.length);
        foxMatrixMultiplication.foxMatrixMultiply(firstMatrix, secondMatrix, result, 4);
        double time = 0;
        repository.addResult(new MultiplicationResult(firstMatrix, secondMatrix, result, time));
        return result;
    }

    public Matrix multiply(int matrixSize) {
        Matrix matrix1 = matrixMap1.get(matrixSize);
        Matrix matrix2 = matrixMap2.get(matrixSize);

        Matrix result = new Matrix(matrix1.matrix.length);
        for (int i = 0; i < matrix1.getMatrix().length; i++) {
            for (int j = 0; j < matrix2.getMatrix().length; j++) {
                int sum = 0;
                for (int k = 0; k < matrix1.getMatrix().length; k++) {
                    sum += matrix1.matrix[i][k] * matrix2.matrix[k][j];
                }
                result.matrix[i][j] = sum;
            }
        }
        double time = 0;
        repository.addResult(new MultiplicationResult(matrix1, matrix2, result, time));
        return result;
    }

    public void addMatrix(int... sizes) {
        for (int size : sizes) {
            Matrix matrix = new Matrix(size);
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix.matrix[i][j] = counter++;
                }
            }
            matrixMap1.put(size, matrix);
        }
        for (int size : sizes) {
            Matrix matrix = new Matrix(size);
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix.matrix[i][j] = counter++;
                }
            }
            matrixMap2.put(size, matrix);
        }
        System.out.println("Finish initialize server matrix");
    }

    public List<MultiplicationResult> getResult() {
        return repository.getResults();
    }
}
