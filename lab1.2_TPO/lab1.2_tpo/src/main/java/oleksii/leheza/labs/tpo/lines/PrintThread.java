package oleksii.leheza.labs.tpo.lines;

public class PrintThread extends Thread {

    private char symbol;
    private Boolean permission;
    private SyncPrint syncPrint;

    public PrintThread(char symbol, boolean permission, SyncPrint syncPrint) {
        this.symbol = symbol;
        this.permission = permission;
        this.syncPrint = syncPrint;
    }

    @Override
    public void run() {
        while (!syncPrint.isStop()) {
            syncPrint.print(symbol,permission);
        }
    }
}
