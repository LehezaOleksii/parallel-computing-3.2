package oleksii.leheza.labs.task2;

import mpi.MPI;

public class Mpi_mm_I {
    private static int NRA = 1000; // number of rows in matrix A
    private static int NCA = 1000; // number of columns in matrix A
    private static int NCB = 1000; // number of columns in matrix B
    static final int MASTER = 0; // taskid of first task
    static final int FROM_MASTER = 1; // setting a message type
    static final int FROM_WORKER = 2; // setting a message type

    public static double[] c;

    public static void main(String[] args) {
        MPI.Init(args);
        int numtasks = MPI.COMM_WORLD.Size();
        int taskid = MPI.COMM_WORLD.Rank();
        int numworkers = numtasks - 1;

        if (numtasks < 2) {
            System.out.println("Need at least two MPI tasks. Quitting...");
            MPI.Finalize();
            System.exit(1);
        }

        if (taskid == MASTER) {
            long startUnblockingMultiplication = System.currentTimeMillis();
            // Matrix initialization
            double[] a = new double[NRA * NCA];
            double[] b = new double[NCA * NCB];
            c = new double[NRA * NCB];
            for (int i = 0; i < NRA * NCA; i++)
                a[i] = 10;
            for (int i = 0; i < NCA * NCB; i++)
                b[i] = 10;

            int averow = NRA / numworkers;
            int extra = NRA % numworkers;
            int offset = 0;
            for (int dest = 1; dest <= numworkers; dest++) {
                int rows = (dest <= extra) ? averow + 1 : averow;
//                System.out.println("Sending " + rows + " rows to task " + dest + " offset= " + offset);

                // Sending rows, offset, and a chunk of matrix A
                MPI.COMM_WORLD.Isend(new int[]{rows}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Isend(new int[]{offset}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Isend(a, offset * NCA, rows * NCA, MPI.DOUBLE, dest, FROM_MASTER);

                // Sending the entire matrix B
                MPI.COMM_WORLD.Isend(b, 0, NCA * NCB, MPI.DOUBLE, dest, FROM_MASTER);

                offset += rows;
            }

            // Receive results from worker tasks
            for (int source = 1; source <= numworkers; source++) {
                int sourceOffset = (source - 1) * averow + Math.min(source - 1, extra);
                int rows = (source <= extra) ? averow + 1 : averow;
                double[] chunkC = new double[rows * NCB];

                MPI.COMM_WORLD.Recv(chunkC, 0, rows * NCB, MPI.DOUBLE, source, FROM_WORKER);
                System.arraycopy(chunkC, 0, c, sourceOffset * NCB, rows * NCB);
//                System.out.println("Received results from task " + source);
            }

            // Print results
//            System.out.println("Result Matrix:");
//            for (int i = 0; i < NRA; i++) {
//                for (int j = 0; j < NCB; j++)
//                    System.out.print(String.format("%6.2f ", c[i * NCB + j]));
//                System.out.println();
//            }
//            System.out.println("Done.");

            long endBlockingMultiplication = System.currentTimeMillis();
            System.out.println("result time: " + (endBlockingMultiplication - startUnblockingMultiplication) + " ms");
            System.out.println(areMatrixEqualsValue(c, 200000));
        } else {
            int[] rows = new int[1];
            int[] offset = new int[1];
            double[] a = new double[NRA * NCA];
            double[] b = new double[NCA * NCB];

            // Receive the number of rows, offset, and chunk of matrix A
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(a, 0, rows[0] * NCA, MPI.DOUBLE, MASTER, FROM_MASTER);

            // Receive the entire matrix B
            MPI.COMM_WORLD.Recv(b, 0, NCA * NCB, MPI.DOUBLE, MASTER, FROM_MASTER);

            // Perform matrix multiplication
            double[] chunkC = new double[rows[0] * NCB];
            for (int k = 0; k < NCB; k++)
                for (int i = 0; i < rows[0]; i++) {
                    chunkC[i * NCB + k] = 0.0;
                    for (int j = 0; j < NCA; j++)
                        chunkC[i * NCB + k] += a[i * NCA + j] * b[j * NCB + k];
                }

            // Send the computed chunk of matrix C back to the master
            MPI.COMM_WORLD.Isend(chunkC, 0, rows[0] * NCB, MPI.DOUBLE, MASTER, FROM_WORKER);
        }
        MPI.Finalize();
    }

    public static boolean areMatrixEqualsValue(double[] firstMatrix, int value) {
        for (int i = 0; i < 500; i++) {
            if (firstMatrix[i] != value) {
                return false;
            }
        }
        return true;
    }
}
