package oleksii.leheza.labs;
import mpi.MPI;
import mpi.MPIException;

public class MatrixMultiplication {

    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int rows = 4; // Кількість рядків матриці
        int cols = 4; // Кількість стовпців матриці
        int blockSize = rows / size; // Кількість рядків на кожний процес

        // Ініціалізуємо матриці A та B на головному процесі
        int[][] A = new int[rows][cols];
        int[][] B = new int[cols][rows];
        int[][] C = new int[blockSize][rows]; // Змінив розмір C на blockSize

        if (rank == 0) {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    A[i][j] = 10;
                    B[j][i] = 10;
                }
            }
            MPI.COMM_WORLD.Bcast(B, 0, cols, MPI.OBJECT, 0);
        } else {
            MPI.COMM_WORLD.Bcast(B, 0, cols, MPI.OBJECT, 0);
        }

        // Розподіляємо матрицю A між усіма процесами
        int[][] subA = new int[blockSize][cols];
        MPI.COMM_WORLD.Scatter(A, 0, blockSize, MPI.OBJECT, subA, 0, blockSize, MPI.OBJECT, 0);

        // Кожен процес обчислює свою частину результуючої матриці C
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    C[i][j] += subA[i][k] * B[k][j]; // Змінив індексацію для C
                }
            }
        }

        // Збираємо результати з усіх процесів
        int[][] result = new int[rows][rows]; // Окремий буфер для результатів
        MPI.COMM_WORLD.Gather(C, 0, blockSize, MPI.OBJECT, result, 0, blockSize, MPI.OBJECT, 0);

        // Процес 0 виводить результат
        if (rank == 0) {
            System.out.println("result:");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    System.out.print(result[i][j] + " ");
                }
                System.out.println();
            }
        }
        MPI.Finalize();
    }
}