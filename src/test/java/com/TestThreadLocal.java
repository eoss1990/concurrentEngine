package com;

/**
 * Created by yangyu on 16/11/29.
 */
public class TestThreadLocal {

    private static ThreadLocal<People> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        /**
         * thread1初始化了一个People
         */
        new Thread(()->{
            threadLocal.set(new People("t1","nan"));
            System.out.println(threadLocal.get());
        }).start();

        /**
         * thread2是取不到thread1初始化的People的
         */
        new Thread(()->{
            System.out.println(threadLocal.get());
        }).start();
    }

    private static class People{
        public String name;
        public String sex;

        People(String name,String sex){
            this.name = name;
            this.sex = sex;
        }
    }
}
