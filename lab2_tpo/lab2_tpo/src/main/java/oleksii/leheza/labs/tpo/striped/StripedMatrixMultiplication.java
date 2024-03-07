package oleksii.leheza.labs.tpo.striped;

import oleksii.leheza.labs.tpo.Result;
import oleksii.leheza.labs.tpo.matrix.MatrixUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class StripedMatrixMultiplication {

    private int threadAmount = 6;
    private MatrixUtil matrixUtil;

    public StripedMatrixMultiplication(MatrixUtil matrixUtil) {
        this.matrixUtil = matrixUtil;
    }

        public void multiply(int[][] firstMatrix, int[][] secondMatrix, int threadsAmount, Result result) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount);
        if (firstMatrix.length == secondMatrix.length) {
            int matrixLength = firstMatrix.length;
            for (int i = 0; i < matrixLength; i++) {
                for (int j = 0; j < matrixLength; j++) {
                    int[] column = new int[matrixLength];
                    for (int k = 0; k < matrixLength; k++) {
                        if (j - i >= 0) {
                            column[k] = secondMatrix[k][j - i];
                        } else {
                            column[k] = secondMatrix[k][matrixLength + (j - i)];
                        }
                    }
                    int columnNumber = j - i;
                    if (columnNumber < 0) {
                        columnNumber += matrixLength;
                    }
                    executorService.execute(new StripedMatrixMultiplicationThread(firstMatrix[j], column, j, columnNumber, result));
                }
            }
        }
        executorService.shutdown();
//        try {
//            if (executorService.awaitTermination(20, TimeUnit.SECONDS)) {
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

//    public void multiply(int[][] firstMatrix, int[][] secondMatrix, int threadsAmount, Result result) {
//        ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount);
//        if (firstMatrix.length == secondMatrix.length) {
//            int matrixLength = firstMatrix.length;
//            for (int i = 0; i < matrixLength; i++) {
//                for (int j = 0; j < matrixLength; j++) {
//                    int[] column = new int[matrixLength];
//                    for (int k = 0; k < matrixLength; k++) {
//                        if (j - i >= 0) {
//                            column[k] = secondMatrix[k][j - i];
//                        } else {
//                            column[k] = secondMatrix[k][matrixLength + (j - i)];
//                        }
//                    }
//                    int columnNumber = j - i;
//                    if (columnNumber < 0) {
//                        columnNumber += matrixLength;
//                    }
//                    executorService.execute(new StripedMatrixMultiplicationThread(firstMatrix[j], column, j, columnNumber, result));
//                }
//            }
//        }
//        executorService.shutdown();
//        try {
//            if (executorService.awaitTermination(20, TimeUnit.SECONDS)) {
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
