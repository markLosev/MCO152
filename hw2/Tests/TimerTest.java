import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimerTest {

    private Timer timer;

    @Before
    public void createTimer() {
        timer = new Timer();
    }

    @Test
    public void testStart() {
        timer.start();
        assertTrue(timer.getStartTime() > 0);
    }

    @Test
    public void testStop() {
        Timer timer = new Timer();
        timer.start();
        timer.stop();

        assertTrue(timer.getTimeWhenStopped() == 0);
    }

    @Test
    public void testReset() {
        timer.start();
        timer.stop();
        timer.reset();
        assertTrue(timer.getAccumulatedTime() == 0);
    }

    @Test
    public void testStartThenStopAndStartAgain() {
        timer.start();
        timer.stop();
        timer.start();
        assertTrue(timer.getAccumulatedTime() > 0);
    }

}