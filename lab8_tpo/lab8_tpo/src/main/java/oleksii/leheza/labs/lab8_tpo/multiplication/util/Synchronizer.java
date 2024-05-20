package oleksii.leheza.labs.lab8_tpo.multiplication.util;

public class Synchronizer {

    private boolean isCycleEnd;

    public void setCycleEnd(boolean cycleEnd) {
        isCycleEnd = cycleEnd;
    }

    public boolean getCycleEnd() {
        return isCycleEnd;
    }
}
