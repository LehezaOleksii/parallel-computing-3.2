package oleksii.leheza.labs.lab8_tpo.multiplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxMatrixMultiplication {

    public void foxMatrixMultiply(Matrix firstMatrix, Matrix secondMatrix, Matrix result, int threads) {
        int matrixSize = firstMatrix.getMatrixSize();
        int blockSize = matrixSize / 2;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int startX = i * blockSize;
                int startY = j * blockSize;
                int endX = startX + blockSize;
                int endY = startY + blockSize;
                executor.submit(new FoxMatrixMultiplicationThread(firstMatrix.matrix, secondMatrix.matrix, result.matrix, startX, endX, startY, endY));
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(60000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error occurred while awaiting termination: " + e.getMessage());
        }
    }
}
