package oleksii.leheza.labs.synch;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankSynch {

    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    private Lock lock = new ReentrantLock();

    public BankSynch(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    public void transferAsync(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
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

    public void transferSyncWaitNotify(int from, int to, int amount) {
        try {
            while (accounts[from] < amount) {
                synchronized (this) {
                    wait();
                }
            }
            synchronized (this) {
                accounts[from] -= amount;
                accounts[to] += amount;
                ntransacts++;
                this.notifyAll();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        synchronized (this) {
            if (ntransacts % NTEST == 0) {
                test();
            }
        }
    }

    public void transferSyncLock(int from, int to, int amount) {
        try {
            lock.lock();
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0) {
                test();
            }
        } finally {
            lock.unlock();
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
