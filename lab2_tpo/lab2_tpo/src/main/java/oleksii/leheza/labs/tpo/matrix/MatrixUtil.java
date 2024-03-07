package oleksii.leheza.labs.tpo.matrix;

public class MatrixUtil {

    public int[] getRow(int[][] matrix, int i) {

        return matrix[i];
    }

    public int[] getColumn(int[][] matrix, int j) {
        int[] column = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][j];
        }
        return column;
    }
}
