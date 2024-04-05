package oleksii.leheza.labs.tpo.fox;

import oleksii.leheza.labs.tpo.matrix.Result;

public class FoxMatrixMultiplicationThread implements Runnable {

    private final int[][] firstMatrixBlock;
    private final int[][] secondMatrixBlock;
    private final Result result;
    private final int startStep;
    private final int endStep;

    public FoxMatrixMultiplicationThread(int[][] firstMatrixBlock, int[][] secondMatrixBlock, Result result, int startStep, int endStep) {
        this.firstMatrixBlock = firstMatrixBlock;
        this.secondMatrixBlock = secondMatrixBlock;
        this.result = result;
        this.startStep = startStep;
        this.endStep = endStep;
    }

    @Override
    public void run() {
        int n = firstMatrixBlock.length;
        int matrixSize = result.matrix.length;
        for (int i = startStep; i < endStep; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += firstMatrixBlock[i][(i + j + k) % n] * secondMatrixBlock[(i + j + k) % n][j];
                }
                result.matrix[i][j] += sum;
            }
        }
    }
}
