package com;

import java.util.concurrent.Semaphore;

/**
 * Created by yangyu on 16/11/28.
 */

/**
 * Semaphore控制并发线程数量
 *
 * 使用场景：
 * 当大批量的并发请求来到系统当中时，为了保证系统稳定，真正执行业务逻辑的线程其实数量有限；
 * 为了保证业务系统的稳定，不会被峰值请求给击垮，那么应该对执行业务逻辑的线程进行并发控制；
 * 而Semaphore就可以用于控制并发线程数量
 */
public class TestSemaphore {

    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {

        /**
         * 先启动5个线程，将限流占满
         */
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getId()+"启动");
                    Thread.sleep(10*1000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        /**
         * 只有当前面5个线程执行semaphore.release()释放以后，后续线程才能执行
         */
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId()+"后续线程");
                semaphore.release();
            }).start();
        }
    }
}
