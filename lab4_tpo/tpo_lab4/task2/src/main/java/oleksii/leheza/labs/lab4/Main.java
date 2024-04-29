package oleksii.leheza.labs.lab4;

import oleksii.leheza.labs.lab4.bank.synch.BankSynch;
import oleksii.leheza.labs.lab4.bank.synch.TransferThreadSynch;
import oleksii.leheza.labs.lab4.fork.TransferTask;

import java.util.concurrent.ForkJoinPool;

class Main {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {


        long startTime1 = System.currentTimeMillis();

        BankSynch b1 = new BankSynch(NACCOUNTS, INITIAL_BALANCE);
        TransferThreadSynch[] threads = new TransferThreadSynch[NACCOUNTS];

        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThreadSynch t = new TransferThreadSynch(b1, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
            threads[i] = t;
        }

        for (TransferThreadSynch thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long elapsedTime1 = System.currentTimeMillis() - startTime1;

        System.out.println("\n================================\n");

        long startTime2 = System.currentTimeMillis();

        BankSynch b2 = new BankSynch(NACCOUNTS, INITIAL_BALANCE);
        ForkJoinPool pool = new ForkJoinPool(NACCOUNTS);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferTask task = new TransferTask(b2, i, INITIAL_BALANCE);
            pool.execute(task);
        }
        pool.shutdown();

        while (!pool.isTerminated()) {
        }

        long elapsedTime2 = System.currentTimeMillis() - startTime2;

        System.out.println("Elapsed time for the base thread approach: " + elapsedTime1 + " ms");
        System.out.println("Elapsed time for the fork join approach: " + elapsedTime2 + " ms");
        System.out.println("speed up: " + (double)elapsedTime1 / elapsedTime2 + " ms");
    }
}
