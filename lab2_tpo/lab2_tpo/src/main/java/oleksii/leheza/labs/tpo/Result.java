package oleksii.leheza.labs.tpo;

public class Result {

    private int[][] resultMatrix;

    public Result(int matrixSize) {
        resultMatrix = new int[matrixSize][matrixSize];
    }

    public void setValue(int row, int column, int value) {
        resultMatrix[row][column] = value;
    }

    public void plusValue(int row, int column, int value) {
        resultMatrix[row][column] += value;
    }

    public int[][] getResultMatrix() {
        return resultMatrix;
    }
}
