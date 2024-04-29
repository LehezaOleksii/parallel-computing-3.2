package oleksii.leheza.labs.tpo;

import oleksii.leheza.labs.tpo.basic.BasicMatrixMultiply;
import oleksii.leheza.labs.tpo.fox.FoxMatrixMultiplication;
import oleksii.leheza.labs.tpo.matrix.Matrix;
import oleksii.leheza.labs.tpo.matrix.Result;
import oleksii.leheza.labs.tpo.util.MatrixUtil;

public class Main {

    public static void main(String[] args) {
        int matrixSize = 1000;
        int threadAmount = 10;
        MatrixUtil matrixUtil = new MatrixUtil();
        Matrix matrix = matrixUtil.initializeRandomMatrix(matrixSize);
//       printMatrix(matrix);
        Result result1 = new Result(matrixSize);
        Result result2 = new Result(matrixSize);
        Result result3 = new Result(matrixSize);


        BasicMatrixMultiply basicMatrixMultiply = new BasicMatrixMultiply();
        FoxMatrixMultiplication foxMatrixMultiplication = new FoxMatrixMultiplication();
        long startTime1 = System.currentTimeMillis();
        basicMatrixMultiply.multiplyMatrix(matrix, matrix, result1);
        long endTime1 = System.currentTimeMillis();
        long startTime3 = System.currentTimeMillis();
        foxMatrixMultiplication.foxMatrixMultiply(matrix, matrix, result3, threadAmount);
        long endTime3 = System.currentTimeMillis();
        double resultTime1 = endTime1 - startTime1;
        double resultTime3 = endTime3 - startTime3;
        System.out.println("---------------Result---------------" +
                "\nSimple matrix multiplication: " + resultTime1 +
                "\nFox matrix multiplication: " + resultTime3 + "; speedup = " + resultTime1 / resultTime3
        );
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
