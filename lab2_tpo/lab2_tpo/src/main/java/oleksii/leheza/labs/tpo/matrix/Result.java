package oleksii.leheza.labs.tpo.matrix;

public class Result {

    public final int[][] matrix;
    private final int matrixSize;

    public Result(int matrixSize) {
        matrix = new int[matrixSize][matrixSize];
        this.matrixSize = matrixSize;
    }

    public Result(int[][] matrix) {
        this.matrix = matrix;
        this.matrixSize = matrix.length;
    }

    public int[] getRow(int rowNumber) {
        return matrix[rowNumber];
    }

    public int[] getColumn(int columnNumber) {
        int[] column = new int[matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            column[i] = matrix[i][columnNumber];
        }
        return column;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
