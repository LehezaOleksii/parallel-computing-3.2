package oleksii.leheza.labs.synch;

public class SynchBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        BankSynch b = new BankSynch(NACCOUNTS, INITIAL_BALANCE);
        TransferThreadSynch[] threads = new TransferThreadSynch[NACCOUNTS];

        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThreadSynch t = new TransferThreadSynch(b, i, INITIAL_BALANCE);
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
    }
}