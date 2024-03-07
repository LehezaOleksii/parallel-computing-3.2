package oleksii.leheza.labs.tpo.counter;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Setter
@Getter
public class Counter {

    private int sum;
    private final Object sync = new Object();
    private Lock lock = new ReentrantLock();
    public void incrementBlock() {
        synchronized (this) {
            sum++;
        }
    }

    public void decrementBlock() {
        synchronized (this) {
            sum--;
        }
    }

    public void incrementLockMethod() {
        lock.lock();
        try {
            sum++;
        } finally {
            lock.unlock();
        }
    }

    public void decrementLockMethod() {
        lock.lock();
        try {
            sum--;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void incrementSyncMethod() {
        sum++;
    }

    public synchronized void decrementSyncMethod() {
        sum--;
    }
}
