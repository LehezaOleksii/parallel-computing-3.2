package oleksii.leheza.labs.synch;


public class TransferThreadSynch extends Thread {

    private BankSynch bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 100000;

    public TransferThreadSynch(BankSynch b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    @Override
    public void run() {
        for (int i = 0; i < REPS; i++) {
            int toAccount = (int) (bank.accountsNumber() * Math.random());
            int amount = (int) (maxAmount * Math.random() / REPS);
//                bank.transferAsync(fromAccount, toAccount, amount);
            bank.transferSyncMethod(fromAccount, toAccount, amount);
//                bank.transferSyncWaitNotify(fromAccount, toAccount, amount);
//                bank.transferSyncLock(fromAccount, toAccount, amount);
        }
    }
}
