package oleksii.leheza.labs.lab4.bank.synch;

public class BankSynch {

    private static final int NTEST = 1000;
    public final int[] accounts;
    private long ntransacts = 0;

    public BankSynch(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
    }

    public synchronized void transferSyncMethod(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
        }
    }

    private void test() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            sum += accounts[i];
        }
        System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
    }

    public int accountsNumber() {
        return accounts.length;
    }
}
