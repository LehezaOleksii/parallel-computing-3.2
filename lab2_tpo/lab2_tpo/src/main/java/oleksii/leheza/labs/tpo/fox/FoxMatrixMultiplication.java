package oleksii.leheza.labs.tpo.fox;

import oleksii.leheza.labs.tpo.Result;
import oleksii.leheza.labs.tpo.matrix.Matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxMatrixMultiplication {

    public void foxMatrixMultiply(Matrix firstMatrix, Matrix secondMatrix, int threadAmount, Result result) {
        int matrixLength = firstMatrix.getMatrixSize();

        threadAmount = Math.min(threadAmount, matrixLength);

        int step = matrixLength / threadAmount;

        ExecutorService executorService = Executors.newFixedThreadPool(threadAmount);

        int[][] matrixOfSizesI = new int[threadAmount][threadAmount];
        int[][] matrixOfSizesJ = new int[threadAmount][threadAmount];

        int stepI = 0;
        for (int i = 0; i < threadAmount; i++) {
            int stepJ = 0;
            for (int j = 0; j < threadAmount; j++) {
                matrixOfSizesI[i][j] = stepI;
                matrixOfSizesJ[i][j] = stepJ;
                stepJ += step;
            }
            stepI += step;
        }

        for (int l = 0; l < threadAmount; l++) {
            for (int i = 0; i < threadAmount; i++) {
                for (int j = 0; j < threadAmount; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % threadAmount];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % threadAmount];

                    int stepI2 = matrixOfSizesI[(i + l) % threadAmount][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % threadAmount][j];

                    FoxMatrixMultiplicationThread t =
                            new FoxMatrixMultiplicationThread(
                                    copyBlock(firstMatrix, stepI1, stepJ1, step),
                                    copyBlock(secondMatrix, stepI2, stepJ2, step),
                                    result,
                                    stepI0,
                                    stepJ0);
                    executorService.submit(t);
                }
            }
        }

        executorService.shutdown();
        try {
            if (executorService.awaitTermination(20, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int[][] copyBlock(Matrix matrix, int i, int j, int size) {
        int[][] block = new int[size][size];
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getRow(k+i), j, block[k], 0, size);
        }
        return block;
    }
}