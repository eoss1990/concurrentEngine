package com;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyu on 16/11/28.
 */

/**
 * ScheduledThreadPoolExecutor定时线程池使用方法
 */
public class TestScheduledThreadPoolExecutor {

    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("123");
            }
        };

        scheduledThreadPool.scheduleAtFixedRate(runnable,1,2, TimeUnit.SECONDS);

        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("关闭线程池");
        scheduledThreadPool.shutdownNow();
    }
}
