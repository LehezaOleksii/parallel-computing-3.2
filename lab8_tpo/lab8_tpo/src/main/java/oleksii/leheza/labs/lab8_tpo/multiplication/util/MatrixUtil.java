package oleksii.leheza.labs.lab8_tpo.multiplication.util;

import oleksii.leheza.labs.lab8_tpo.domain.Matrix;

import java.util.Random;

public class MatrixUtil {

    public Matrix initializeRandomMatrix(int matrixSize) {
        Random random = new Random();
        Matrix matrix = new Matrix(matrixSize);
        int counter = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
//                matrix.setValue(i, j, random.nextInt() % 10);
                matrix.matrix[i][j] = 1;
            }
        }
        return matrix;
    }

    public static boolean isMatrixsEqual(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getMatrixSize() != matrix2.getMatrixSize()) {
            return false;
        }
        for (int i = 0; i < matrix1.getMatrixSize(); i++) {
            for (int j = 0; j < matrix1.getMatrixSize(); j++) {
                if (matrix1.matrix[i][j] != matrix2.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
