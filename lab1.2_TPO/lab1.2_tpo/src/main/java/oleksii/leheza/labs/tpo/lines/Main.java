package oleksii.leheza.labs.tpo.lines;

public class Main {
    public static void main(String[] args) {
        SyncPrint permission = new SyncPrint();
        Thread hyphenThread = new Thread(new PrintThread('-', false, permission));
        Thread verticalThread = new Thread(new PrintThread('|', true, permission));
        hyphenThread.start();
        verticalThread.start();
    }
}
