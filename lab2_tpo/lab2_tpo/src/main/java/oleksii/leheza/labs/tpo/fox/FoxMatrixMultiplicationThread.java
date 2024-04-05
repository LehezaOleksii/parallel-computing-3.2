package oleksii.leheza.labs.tpo.fox;

public class FoxMatrixMultiplicationThread implements Runnable {
    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int[][] resultMatrix;
    private final int startX;
    private final int endX;
    private final int startY;
    private final int endY;

    public FoxMatrixMultiplicationThread(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startX, int endX, int startY, int endY) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    public void run() {
        int blockSize = endX - startX;
        int n = firstMatrix.length;

        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                for (int k = 0; k < n; k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        // Perform shift operation
        int[][] tempMatrix = new int[blockSize][blockSize];
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                tempMatrix[i - startX][j - startY] = resultMatrix[i][j];
            }
        }

        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                int shiftRow = (i + startX + 1) % blockSize;
                resultMatrix[i][j] = tempMatrix[shiftRow][j - startY];
            }
        }
    }
}