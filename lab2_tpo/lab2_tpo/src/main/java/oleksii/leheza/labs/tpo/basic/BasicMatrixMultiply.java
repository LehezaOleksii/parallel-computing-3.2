package oleksii.leheza.labs.tpo.basic;

import oleksii.leheza.labs.tpo.Result;

public class BasicMatrixMultiply {

    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix, Result result) {
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
                    int[] row = firstMatrix[j];
                    int columnNumber = j - i;
                    if (columnNumber < 0) {
                        columnNumber += matrixLength;
                    }
                    int sum = 0;
                    for (int n = 0; n < matrixLength; n++) {
                        sum += row[n] * column[n];
                    }
                    result.setValue(j, columnNumber, sum);
                }
            }
        }
    }

    public void basicMultiply(int[][] matrixA, int[][] matrixB, Result result) {
        int size = matrixA.length;
        int[][] resultMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                resultMatrix[i][j] = 0;
                for (int k = 0; k < size; k++) {
                    result.plusValue(i,j,matrixA[i][k]* matrixB[k][j]);
                }
            }
        }
    }
}
