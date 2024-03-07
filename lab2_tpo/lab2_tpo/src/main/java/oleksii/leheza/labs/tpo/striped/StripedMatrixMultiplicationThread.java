package oleksii.leheza.labs.tpo.striped;

import oleksii.leheza.labs.tpo.Result;

public class StripedMatrixMultiplicationThread implements Runnable {

    private Result result;
    private int[] row;
    private int[] column;
    private int rowNumber;
    private int columnNumber;

    public StripedMatrixMultiplicationThread(int[] row, int[] column, int rowNumber, int columnNumber, Result result) {
        this.result = result;
        this.row = row;
        this.column = column;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }


    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < row.length; i++) {
            sum += row[i] * column[i];
        }
        result.setValue(rowNumber, columnNumber, sum);
    }
}
