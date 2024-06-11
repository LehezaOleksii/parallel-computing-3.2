package oleksii.leheza.labs.lab8_tpo.multiplication.fox;

import oleksii.leheza.labs.lab8_tpo.multiplication.Matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class FoxMatrixMultiplication {

    public void foxMatrixMultiply(Matrix firstMatrix, Matrix secondMatrix, Matrix result, int thread) {
        int matrixSize = firstMatrix.getMatrixSize();
        int blockSize = matrixSize / 2;
        ExecutorService executor = Executors.newFixedThreadPool(thread);
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
            executor.awaitTermination(460000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error occurred while awaiting termination: " + e.getMessage());
        }
    }
}
