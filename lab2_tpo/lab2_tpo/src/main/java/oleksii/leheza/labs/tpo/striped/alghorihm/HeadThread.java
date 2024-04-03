package oleksii.leheza.labs.tpo.striped.alghorihm;
import oleksii.leheza.labs.tpo.striped.Synchronizer;
import oleksii.leheza.labs.tpo.striped.matrix.Matrix;

import java.util.concurrent.atomic.AtomicInteger;

public class HeadThread extends ClassicThread {

    private final Object lock;

    private boolean isNeedNewData;

    public AtomicInteger lastNeedColumnIndex = new AtomicInteger();

    public AtomicInteger lastNeedRowIndex = new AtomicInteger();

    public Synchronizer synchronizer;


    public HeadThread(Matrix result, int iteration, int matrixLength, Object lock, Synchronizer synchronizer) {
        super(result, iteration, matrixLength, synchronizer);
        this.lock = lock;
        this.synchronizer = synchronizer;
    }

    @Override
    public void run() {
        int[] row;
        int[] column;
        while (!synchronizer.getAlgorithmEnd()) {
            for (int i = 0; i < matrixLength; i++) {
                while (rows.isEmpty() && columns.isEmpty()) {
                    isNeedNewData = true;
                    try {
                        synchronized (lock) {
                            lock.notify();
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    column = columns.take();
                    row = rows.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (nextThread != null) {
                    if (i == 0) {
                        nextThread.setValueToLastColumn(column);
                    } else {
                        nextThread.addColumnToQueue(column);
                    }
                    nextThread.addRowToQueue(row);
                    synchronized (nextThread.lockObj) {
                        nextThread.lockObj.notify();
                    }
                }
                int sum = 0;
                for (int n = 0; n < row.length; n++) {
                    sum += row[n] * column[n];
                }

                int columnNumber = (i + iteration) % matrixLength;
                result.matrix[i][columnNumber] = sum;
            }
            while (synchronizer.getCycleEnd()) {
                try {
                    synchronized (synchronizer) {
                        synchronizer.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void addColumnToQueue(int[] column) {
        columns.add(column);
    }

    public void addRowToQueue(int[] row) {
        rows.add(row);
    }

    public void setIsNeedNewData(boolean isNeedNewRow) {
        this.isNeedNewData = isNeedNewRow;
    }

    public boolean getIsNeedNewData() {
        return isNeedNewData;
    }

    public void incrementLastColumnIndex() {
        if (lastNeedColumnIndex.get() >= matrixLength - 1) {
            lastNeedColumnIndex.set(0);
        } else {
            lastNeedColumnIndex.getAndIncrement();
        }
    }

    public void incrementLastRowIndex() {
        lastNeedRowIndex.incrementAndGet();
    }

    public int getLastRowIndex() {
        return lastNeedRowIndex.get();
    }
}