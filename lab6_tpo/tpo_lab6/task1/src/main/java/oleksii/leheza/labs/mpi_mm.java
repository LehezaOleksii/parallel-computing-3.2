package oleksii.leheza.labs;

import mpi.*;

public class mpi_mm {
    static final int NRA = 62; // number of rows in matrix A
    static final int NCA = 15; // number of columns in matrix A
    static final int NCB = 7;  // number of columns in matrix B
    static final int MASTER = 0; // taskid of first task
    static final int FROM_MASTER = 1; // setting a message type
    static final int FROM_WORKER = 2; // setting a message type

    public static void main(String[] args) {
        MPI.Init(args);
        int numtasks = MPI.COMM_WORLD.Size();
        int taskid = MPI.COMM_WORLD.Rank();

        if (numtasks < 2) {
            System.out.println("Need at least two MPI tasks. Quitting...");
            MPI.Finalize();
            System.exit(1);
        }

        int numworkers = numtasks - 1;

        if (taskid == MASTER) {
            System.out.println("mpi_mm has started with " + numtasks + " tasks.");
            double[] a = new double[NRA * NCA];
            double[] b = new double[NCA * NCB];
            double[] c = new double[NRA * NCB];

            // Initialize matrices A and B
            for (int i = 0; i < NRA * NCA; i++)
                a[i] = 10;

            for (int i = 0; i < NCA * NCB; i++)
                b[i] = 10;

            int averow = NRA / numworkers;
            int extra = NRA % numworkers;
            int offset = 0;

            // Send matrix data to worker tasks
            for (int dest = 1; dest <= numworkers; dest++) {
                int rows = (dest <= extra) ? averow + 1 : averow;
                System.out.println("Sending " + rows + " rows to task " + dest + " offset= " + offset);
                MPI.COMM_WORLD.Send(new int[]{offset, rows}, 0, 2, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(a, offset * NCA, rows * NCA, MPI.DOUBLE, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(b, 0, NCA * NCB, MPI.DOUBLE, dest, FROM_MASTER);
                offset += rows;
            }

            // Receive results from worker tasks
            for (int source = 1; source <= numworkers; source++) {
                int[] status = new int[1];
                MPI.COMM_WORLD.Recv(status, 0, 2, MPI.INT, MASTER, FROM_MASTER);
                int recvOffset = status[0];
                int recvRows = status[1];
                MPI.COMM_WORLD.Recv(c, recvOffset * NCB, recvRows * NCB, MPI.DOUBLE, source, FROM_WORKER);
                System.out.println("Received results from task " + source);
            }

            // Print results
            System.out.println("****");
            System.out.println("Result Matrix:");
            for (int i = 0; i < NRA; i++) {
                System.out.println();
                for (int j = 0; j < NCB; j++)
                    System.out.printf("%6.2f ", c[i * NCB + j]);
            }
            System.out.println("\n********");
            System.out.println("Done.");
        } else { // Worker tasks
            int[] status = new int[2];
            MPI.COMM_WORLD.Recv(status, 0, 2, MPI.INT, MASTER, FROM_MASTER);
            int offset = status[0];
            int rows = status[1];
            double[] a = new double[rows * NCA];
            double[] b = new double[NCA * NCB];
            double[] c = new double[rows * NCB];

            MPI.COMM_WORLD.Recv(a, 0, rows * NCA, MPI.DOUBLE, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(b, 0, NCA * NCB, MPI.DOUBLE, MASTER, FROM_MASTER);

            // Perform matrix multiplication
            for (int k = 0; k < NCB; k++)
                for (int i = 0; i < rows; i++) {
                    c[i * NCB + k] = 0.0;
                    for (int j = 0; j < NCA; j++)
                        c[i * NCB + k] += a[i * NCA + j] * b[j * NCB + k];
                }

            MPI.COMM_WORLD.Send(new int[]{offset, rows}, 0, 2, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(c, 0, rows * NCB, MPI.DOUBLE, MASTER, FROM_WORKER);
        }

        MPI.Finalize();
    }
}
