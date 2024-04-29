package oleksii.leheza.labs.lab4.fork;

import oleksii.leheza.labs.lab4.bank.synch.BankSynch;

import java.util.concurrent.RecursiveAction;

public class TransferTask extends RecursiveAction {
    private BankSynch bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 10000;
    private long ntransacts = 10000;

    public TransferTask(BankSynch b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    @Override
    protected void compute() {
        for (int i = 0; i < REPS; i++) {
            int toAccount = (int) (bank.accountsNumber() * Math.random());
            int amount = (int) (maxAmount * Math.random() / REPS);
            bank.transferSyncMethod(fromAccount, toAccount, amount);

        }
    }

    private void test() {
        int sum = 0;
        for (int i = 0; i < bank.accounts.length; i++) {
            sum += bank.accounts[i];
        }
        System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
    }
}