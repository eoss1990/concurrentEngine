package com;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by yangyu on 16/11/28.
 */

/**
 * FutureTask由于实现了Runnable接口，所以可以被Thread执行
 * 但是FutureTask又实现了Future接口，所以可以获取返回值，并且监控线程状态
 *
 * 原理：
 * FutureTask实现是基于AbstractQueuedSynchronizer同步框架（下面简称AQS）
 * ReentrantLock，Semaphore，ReentrantReadWriteLock，CountDownLatch，FutureTask都是基于AQS同步框架而实现的
 * 基于AQS实现的同步器都会包含如下两种类型的操作：
 * 1、至少一个acquire操作
 * 2、至少一个release操作
 *
 * Future的get()方法就会检查线程执行状态，如果状态为已经执行完成那么就会返回Callable的返回值，如果状态为没有执行完成，那么会阻塞当前线程并放入等待队列中
 * 当其它线程执行release操作以后（如FutureTask.run()或者FutureTask.cancel），就会去唤醒等待队列中的线程
 */
public class TestFutureTask {

    private static class call implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3*1000);
            int sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum = sum + i;
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new call());

        new Thread(()->{
            try {
                /**
                 * 这里仅仅是get，就是被阻塞等待返回值，一直阻塞到后面的Thread执行以后有了返回值
                 */
                System.out.println("线程执行完成："+futureTask1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(futureTask1).start();
        System.out.println("futureTask开始执行");

        try {
            System.out.println(futureTask1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
