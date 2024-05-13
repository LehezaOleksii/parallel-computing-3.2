package oleksii.leheza.labs.task1;

import mpi.MPI;
import mpi.MPIException;

public class MatrixMultiplication {

    static int rows = 500;
    static int cols = 500;

    public static void main(String[] args) throws MPIException {

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int blockSize = rows / size;

        int[][] A = new int[rows][cols];
        int[][] B = new int[cols][rows];
        int[][] C = new int[blockSize][rows];

        int counter = 0;
        if (rank == 0) {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    A[i][j] = counter;
                    B[j][i] = counter;
                    counter++;
                }
            }
            MPI.COMM_WORLD.Bcast(B, 0, cols, MPI.OBJECT, 0);
        } else {
            MPI.COMM_WORLD.Bcast(B, 0, cols, MPI.OBJECT, 0);
        }

        long startTime = System.currentTimeMillis();

        int[][] subA = new int[blockSize][cols];
        MPI.COMM_WORLD.Scatter(A, 0, blockSize, MPI.OBJECT, subA, 0, blockSize, MPI.OBJECT, 0);

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    C[i][j] += subA[i][k] * B[k][j];
                }
            }
        }

        int[][] result = new int[rows][cols];
        MPI.COMM_WORLD.Gather(C, 0, blockSize, MPI.OBJECT, result, 0, blockSize, MPI.OBJECT, 0);

        if (rank == 0) {
            long endTime = System.currentTimeMillis();

//            System.out.println("result:");
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < rows; j++) {
//                    System.out.print(result[i][j] + " ");
//                }
//                System.out.println();
//            }

            System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
        }
        MPI.Finalize();
    }
}