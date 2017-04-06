package com;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangyu on 16/11/23.
 */
public class TestCondition {

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {

        new Thread(()->{
            String name = Thread.currentThread().getName();
            System.out.println(name+":begin");
            lock.lock();
            try {
                condition.await();
                System.out.println(name+"is wakeup");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"myThread").start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        condition.signal();
        lock.unlock();

    }
}
