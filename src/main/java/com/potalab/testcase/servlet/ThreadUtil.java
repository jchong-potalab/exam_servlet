package com.potalab.testcase.servlet;

public class ThreadUtil {
    private ThreadUtil() {

    }

    public static void pause(long milles) {
        try {
            Thread.sleep(milles);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
