package oleksii.leheza.labs.tpo.fox;

import oleksii.leheza.labs.tpo.matrix.Matrix;
import oleksii.leheza.labs.tpo.matrix.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxMatrixMultiplication {

    public void foxMatrixMultiply(Matrix firstMatrix, Matrix secondMatrix, int threadAmount, Result result) {
        int matrixSize = firstMatrix.getMatrixSize();
        ExecutorService executor = Executors.newFixedThreadPool(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            int start = i * matrixSize / threadAmount;
            int end = (i + 1) * matrixSize / threadAmount;
            executor.submit(new FoxMatrixMultiplicationThread(firstMatrix.matrix, secondMatrix.matrix, result, start, end));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(60000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error occurred while awaiting termination: " + e.getMessage());
        }
    }
}
