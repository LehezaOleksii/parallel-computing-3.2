package oleksii.leheza.labs.task2;

import mpi.MPI;
import mpi.MPIException;

public class MatrixMultiplication {

    static int rows = 1998;
    static int cols = 1998;

    public static void main(String[] args) throws MPIException {

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] counts = new int[size];
        int[] displacements = new int[size]; // зміщення для кожного процесу

        int[][] A = new int[rows][cols];
        int[][] B = new int[cols][rows];
        int[][] C = new int[rows][cols];

        if (rank == 0) {
            int counter = 0;
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    A[i][j] = 1;
                    B[j][i] = 1;
                    counter++;
                }
            }
        }

        int blockSize = rows / size;
        int remainder = rows % size;

        MPI.COMM_WORLD.Bcast(B, 0, cols, MPI.OBJECT, 0);

        // Обчислення кількості елементів для кожного процесу та їх зміщень
        for (int i = 0; i < size; i++) {
            counts[i] = (i < remainder) ? blockSize + 1 : blockSize;
            displacements[i] = (i == 0) ? 0 : displacements[i - 1] + counts[i - 1];
        }

        long startTime = System.currentTimeMillis();

        int[][] subA = new int[counts[rank]][cols];
        MPI.COMM_WORLD.Scatterv(A, 0, counts, displacements, MPI.OBJECT, subA, 0, counts[rank], MPI.OBJECT, 0);

        for (int i = 0; i < counts[rank]; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    C[i][j] += subA[i][k] * B[k][j];
                }
            }
        }

        int[][] result = new int[rows][cols];
        MPI.COMM_WORLD.Allgather(C, 0, counts[rank], MPI.OBJECT, result, 0, counts[rank], MPI.OBJECT);

        if (rank == 0) {
            long endTime = System.currentTimeMillis();
//            System.out.println("result:");
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < rows; j++) {
//                    System.out.print(result[i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println(areMatrixEqualsValue(result, 50000));
            System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
        }

        MPI.COMM_WORLD.Barrier();

        MPI.Finalize();
    }

    public static boolean areMatrixEqualsValue(int[][] result, int value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (result[i][j] != value) {
                    return false;
                }
            }
        }
        return true;
    }
}
