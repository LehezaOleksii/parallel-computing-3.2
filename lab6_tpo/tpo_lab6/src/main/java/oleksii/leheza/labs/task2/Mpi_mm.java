package oleksii.leheza.labs.task2;

import mpi.MPI;

import java.util.Random;

public class Mpi_mm {
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

            // Matrix initialization
            double[] a = new double[NRA * NCA];
            double[] b = new double[NCA * NCB];
            c = new double[NRA * NCB];
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
//                System.out.println("Sending " + rows + " rows to task " + dest + " offset= " + offset);
                MPI.COMM_WORLD.Send(new int[]{offset}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(new int[]{rows}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(a, offset * NCA, rows * NCA, MPI.DOUBLE, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(b, 0, NCA * NCB, MPI.DOUBLE, dest, FROM_MASTER);
                offset += rows;
            }

            // Receive results from worker tasks
            for (int source = 1; source <= numworkers; source++) {
                int sourceOffset = (source - 1) * averow + Math.min(source - 1, extra);
                int rows = (source <= extra) ? averow + 1 : averow;
                MPI.COMM_WORLD.Recv(new int[]{sourceOffset}, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(new int[]{rows}, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(c, sourceOffset * NCB, rows * NCB, MPI.DOUBLE, source, FROM_WORKER);
//                System.out.println("Received results from task " + source);
            }

            // Print results
//            System.out.println("****");
//            System.out.println("Result Matrix:");
//            for (int i = 0; i < NRA; i++) {
//                for (int j = 0; j < NCB; j++)
//                    System.out.print(String.format("%6.2f ", c[i * NCB + j]));
//                System.out.println();
//            }
//            System.out.println("********");
//            System.out.println("Done.");

            long endBlockingMultiplication = System.currentTimeMillis();
            System.out.println("result time: " + (endBlockingMultiplication - startUnblockingMultiplication) + " ms");

        } else {
            int[] offset = new int[1];
            int[] rows = new int[1];
            double[] a = new double[NRA * NCA];
            double[] b = new double[NCA * NCB];
            double[] c = new double[NRA * NCB];

            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(a, 0, rows[0] * NCA, MPI.DOUBLE, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(b, 0, NCA * NCB, MPI.DOUBLE, MASTER, FROM_MASTER);

            for (int k = 0; k < NCB; k++)
                for (int i = 0; i < rows[0]; i++) {
                    c[i * NCB + k] = 0.0;
                    for (int j = 0; j < NCA; j++)
                        c[i * NCB + k] += a[i * NCA + j] * b[j * NCB + k];
                }

            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(c, 0, rows[0] * NCB, MPI.DOUBLE, MASTER, FROM_WORKER);
        }
        MPI.Finalize();
    }

    public double[] getResultMatrix(String[] args) {
        main(args);
        return c;
    }
}