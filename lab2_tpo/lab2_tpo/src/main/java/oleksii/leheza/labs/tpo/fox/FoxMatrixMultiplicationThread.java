package oleksii.leheza.labs.tpo.fox;

import oleksii.leheza.labs.tpo.Result;

public class FoxMatrixMultiplicationThread implements Runnable {


    private final int[][] A;
    private final int[][] B;
    private final Result result;
    private final int stepI;
    private final int stepJ;

    public FoxMatrixMultiplicationThread(int[][] A, int[][] B, Result result, int stepI, int stepJ) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.stepI = stepI;
        this.stepJ = stepJ;
    }

    @Override
    public void run() {
        int[][] blockRes = multiplyBlock();

        for (int i = 0; i < blockRes.length; i++) {
            for (int j = 0; j < blockRes.length; j++) {
                result.plusValue(i + stepI, j + stepJ, blockRes[i][j]);
            }
        }
        // System.out.println(Thread.currentThread().getName());
    }

    private int[][] multiplyBlock() {
        int[][] blockRes = new int[A.length][B.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                for (int k = 0; k < A.length; k++) {
                    blockRes[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return blockRes;
    }
}
