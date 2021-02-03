package interaction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class Interact {
    public static void main(String[] args) throws InterruptedException, AWTException {
        int hours = 3;
        int minutes = 25;
        int seconds = 0;

        long sleepMillisecondDuration = Duration.ofHours(hours).toMillis() + Duration.ofMinutes(minutes).toMillis() + Duration.ofSeconds(seconds).toMillis();

        Thread.sleep(sleepMillisecondDuration);

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_F10);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_F10);
    }
}
