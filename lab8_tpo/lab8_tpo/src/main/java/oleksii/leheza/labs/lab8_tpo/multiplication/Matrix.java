package oleksii.leheza.labs.lab8_tpo.multiplication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Matrix {

    public int[][] matrix;
    private int matrixSize;

    public Matrix(int matrixSize) {
        matrix = new int[matrixSize][matrixSize];
        this.matrixSize = matrixSize;
    }

    public Matrix() {
    }

    public Matrix(@JsonProperty("data") int[][] matrix) {
        this.matrixSize = matrix.length;
        this.matrix = matrix;
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
