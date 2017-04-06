package com;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by yangyu on 16/11/29.
 */
public class TestReentrantReadWriteLock {

    /**
     * ReentrantReadWriteLock读写锁
     * 1、读读共享
     * 2、写写互斥
     * 3、读写、写读互斥
     */
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ReadAndWrite readAndWrite = new ReadAndWrite(lock);

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                readAndWrite.write();
            }).start();
        }

    }

    private static class ReadAndWrite {

        private ReentrantReadWriteLock lock;
        private ReentrantReadWriteLock.ReadLock readLock;
        private ReentrantReadWriteLock.WriteLock writeLock;

        public ReadAndWrite(ReentrantReadWriteLock lock){
            this.lock =lock;
            this.readLock = lock.readLock();
            this.writeLock = lock.writeLock();
        }
        public void read(){
            readLock.lock();
            System.out.println(Thread.currentThread().getId()+":获取读锁");
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getId()+":read");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                readLock.unlock();
                System.out.println(Thread.currentThread().getId()+":释放读锁");
            }
        }

        public void write(){
            writeLock.lock();
            System.out.println(Thread.currentThread().getId()+":获取写锁");
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getId()+":write");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                writeLock.unlock();
                System.out.println(Thread.currentThread().getId()+":释放写锁");
            }


        }
    }
}
