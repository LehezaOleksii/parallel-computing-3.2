package oleksii.leheza.labs.task2;

import mpi.MPI;

public class Mpi_mm_I {
    static final int NRA = 2000; // number of rows in matrix A
    static final int NCA = 2000; // number of columns in matrix A
    static final int NCB = 2000; // number of columns in matrix B
    static final int MASTER = 0; // taskid of first task
    static final int FROM_MASTER = 1; // setting a message type
    static final int FROM_WORKER = 2; // setting a message type

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

            // Matrix initialization
            double[] a = new double[NRA * NCA];
            double[] b = new double[NCA * NCB];
            double[] c = new double[NRA * NCB];
            for (int i = 0; i < NRA * NCA; i++)
                a[i] = 10;
            for (int i = 0; i < NCA * NCB; i++)
                b[i] = 10;
            long startUnblockingMultiplication = System.currentTimeMillis();

            int averow = NRA / numworkers;
            int extra = NRA % numworkers;
            int offset = 0;
            for (int dest = 1; dest <= numworkers; dest++) {
                int rows = (dest <= extra) ? averow + 1 : averow;

                MPI.COMM_WORLD.Isend(new int[]{offset, rows}, 0, 2, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Isend(a, offset * NCA, rows * NCA, MPI.DOUBLE, dest, FROM_MASTER);
                MPI.COMM_WORLD.Isend(b, 0, NCA * NCB, MPI.DOUBLE, dest, FROM_MASTER);

                // Update offset for the next chunk
                offset += rows;
            }

            // Receive results from worker tasks
            for (int source = 1; source <= numworkers; source++) {
                int sourceOffset = (source - 1) * averow + Math.min(source - 1, extra);
                int rows = (source <= extra) ? averow + 1 : averow;

                // Receive computed chunk of matrix C
                MPI.COMM_WORLD.Recv(c, sourceOffset * NCB, rows * NCB, MPI.DOUBLE, source, FROM_WORKER);
            }

            // Print results
            long endBlockingMultiplication = System.currentTimeMillis();
            System.out.println("result time: " + (endBlockingMultiplication - startUnblockingMultiplication) + " ms");
            System.out.println(areMatrixEqualsValue(c, 50000));
        } else {
            int[] info = new int[2]; // Array to store offset and rows

            // Receive offset and rows
            MPI.COMM_WORLD.Recv(info, 0, 2, MPI.INT, MASTER, FROM_MASTER);
            int rows = info[1];

            double[] a = new double[rows * NCA]; // Local chunk of matrix A

            // Receive chunk of matrix A
            MPI.COMM_WORLD.Recv(a, 0, rows * NCA, MPI.DOUBLE, MASTER, FROM_MASTER);

            double[] b = new double[NCA * NCB]; // Matrix B (same for all workers)

            // Receive matrix B
            MPI.COMM_WORLD.Recv(b, 0, NCA * NCB, MPI.DOUBLE, MASTER, FROM_MASTER);

            double[] c = new double[rows * NCB]; // Local chunk of matrix C

            // Compute local chunk of matrix C
            for (int k = 0; k < NCB; k++)
                for (int i = 0; i < rows; i++) {
                    c[i * NCB + k] = 0.0;
                    for (int j = 0; j < NCA; j++)
                        c[i * NCB + k] += a[i * NCA + j] * b[j * NCB + k];
                }

            // Send computed chunk of matrix C back to master
            MPI.COMM_WORLD.Isend(c, 0, rows * NCB, MPI.DOUBLE, MASTER, FROM_WORKER);
        }

        MPI.Finalize();
    }

    public static boolean areMatrixEqualsValue(double[] firstMatrix, int value) {
        for (int i = 0; i < NCA * NRA; i++) {
            if (firstMatrix[i] != value) {
                return false;
            }
        }
        return true;
    }
}
