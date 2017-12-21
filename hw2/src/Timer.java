
/**
 *
 * @author Mark
 */
public class Timer {
    private long accumulatedTime;
    private long timeWhenStopped;
    private long startTime;
    private boolean started;

    public void start() {
        if (started) {
            throw new IllegalStateException();
        }
        started = true;
        startTime = System.currentTimeMillis();
    }

    public long getaccumulatedTime() {
        return accumulatedTime;
    }

    public long getTimeWhenStopped() {
        return timeWhenStopped;
    }

    public long getStartTime() {
        return startTime;
    }

    public void stop(){
        if (started) {
            started = false;
            timeWhenStopped = calculateTime();
        }
    }

    public void reset() {
        accumulatedTime = 0;
        System.out.println(accumulatedTime);
    }

    public long getAccumulatedTime() {
        if (started) {
            return calculateTime();
        }
        return timeWhenStopped;
    }

    private long calculateTime() {
        accumulatedTime = System.currentTimeMillis() - startTime + accumulatedTime;
        return accumulatedTime;
    }

}


