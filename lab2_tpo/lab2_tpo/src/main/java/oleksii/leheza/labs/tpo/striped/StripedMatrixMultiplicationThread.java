package oleksii.leheza.labs.tpo.striped;

import oleksii.leheza.labs.tpo.Result;
import oleksii.leheza.labs.tpo.matrix.Matrix;

public class StripedMatrixMultiplicationThread implements Runnable {

    private Result result;
    private int iteration;
    private int matrixLength;
    private Matrix firstMatrix;
    private Matrix secondMatrix;

    public StripedMatrixMultiplicationThread(Matrix firstMatrix, Matrix secondMatrix, int iteration, int matrixLength, Result result) {
        this.result = result;
        this.iteration = iteration;
        this.matrixLength = matrixLength;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
    }

    @Override
    public void run() {
        for (int j = 0; j < matrixLength; j++) {
            int[] column = new int[matrixLength];
            for (int k = 0; k < matrixLength; k++) {
                if (j - iteration >= 0) {
                    column[k] = secondMatrix.getValue(k,j - iteration);
                } else {
                    column[k] = secondMatrix.getValue(k,matrixLength + (j - iteration));
                }
            }
            int columnNumber = j - iteration;
            if (columnNumber < 0) {
                columnNumber += matrixLength;
            }
            int[] row = firstMatrix.getRow(j);

            int sum = 0;
            for (int n = 0; n < row.length; n++) {
                sum += row[n] * column[n];
            }
            setValueToMatrix(j, columnNumber, sum);
        }
    }

    private void setValueToMatrix(int rowNumber, int columnNumber, int sum) {
        result.setValue(rowNumber, columnNumber, sum);
    }
}
