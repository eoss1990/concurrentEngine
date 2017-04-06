package com;

import com.adapter.Adapter;
import com.threadPool.AdapterThreadPool;

import java.util.UUID;

/**
 * Created by yangyu on 16/11/21.
 */
public class TestNotifyAll {
    public static void main(String[] args) {

        AdapterClass adapter1;
        AdapterClass adapter2;
        AdapterClass adapter3;

        adapter3 = new AdapterClass(null);
        adapter2 = new AdapterClass(adapter3);
        adapter1 = new AdapterClass(adapter2);

        AdapterThreadPool.excute(adapter1);
        AdapterThreadPool.excute(adapter2);
        AdapterThreadPool.excute(adapter2);
        AdapterThreadPool.excute(adapter3);

//        new Thread(adapter1).start();
//        new Thread(adapter2).start();
//        new Thread(adapter3).start();

        System.out.println("线程全部初始化并且wait()");
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (adapter1){
            adapter1.notify();
        }
        System.out.println("主线程退出");

    }

    private static class AdapterClass implements Runnable{

        private AdapterClass next;

        public AdapterClass(AdapterClass next)
        {
            this.next=next;
        }

        @Override
        public void run() {
            doWait(10000);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (next!=null)
            doNotify(next);
            System.out.println(Thread.currentThread().getId()+"执行完毕");
        }

        private void doWait(long timeOut){
            synchronized (this){
                try {
                    System.out.println(Thread.currentThread().getId()+"wait");
                    this.wait(timeOut);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void doNotify(Object o){
            synchronized (o){
                o.notifyAll();
            }
        }
    }
}
