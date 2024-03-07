package oleksii.leheza.labs.tpo.lines;

import lombok.Getter;

@Getter
public class SyncPrint {

    private boolean permission;
    private int rowLength = 70;
    private int columnNumber = 100;
    private boolean isStop;
    private int currentColumnPointer = 0;

    public synchronized void print(char symbol, boolean permission) {
        waitThread(permission);
        printSymbols(symbol);
    }

    private void waitThread(boolean permission) {
        while (this.permission != permission) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printSymbols(char symbol) {
        for (int i = 0; i < columnNumber; i++) {
            while (currentColumnPointer < rowLength) {
                System.out.print(symbol);
                this.permission = !permission;
                currentColumnPointer++;
                notifyAll();
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println();
            currentColumnPointer=0;
        }
        isStop = true;
    }
}
