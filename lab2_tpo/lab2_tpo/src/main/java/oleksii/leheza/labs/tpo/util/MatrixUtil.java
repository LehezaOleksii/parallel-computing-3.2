package oleksii.leheza.labs.tpo.util;

import oleksii.leheza.labs.tpo.matrix.Matrix;

import java.util.Random;

public class MatrixUtil {

    public Matrix initializeRandomMatrix(int matrixSize) {
        Random random = new Random();
        Matrix matrix = new Matrix(matrixSize);
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix.matrix[i][j] = random.nextInt() % 10;
            }
        }
        return matrix;
    }

    public static boolean isMatricesEqual(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
