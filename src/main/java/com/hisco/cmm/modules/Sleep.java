package com.hisco.cmm.modules;

import java.awt.Robot;
import java.awt.AWTException;

public class Sleep {
    public static void Call(int millisecond) {
        /*
         * try
         * {
         * Thread.sleep(millisecond);
         * }
         * catch (InterruptedException e) {}
         */
        try {
            Robot robot = new Robot();
            if (robot != null)
                robot.delay(millisecond);
        } catch (AWTException e) {
            // e.printStackTrace();
        }
    }
}