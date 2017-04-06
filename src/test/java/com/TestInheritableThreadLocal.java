package com;

/**
 * Created by yangyu on 16/11/29.
 */
public class TestInheritableThreadLocal {
    /**
     * 继承型InheritableThreadLocal
     * 线程不安全
     */
    private static InheritableThreadLocal<People> inheritableThreadLocal = new InheritableThreadLocal();

    public static void main(String[] args) {
        inheritableThreadLocal.set(new People("1", "yangyu"));
        inheritableThreadLocal.get();

        /**
         * 在new Thread的时候会执行Thread的init方法
         * init方法会检测当前线程的parent线程的inheritableThreadLocals属性是否为空
         * 如果不为空则会拷贝inheritableThreadLocals属性
         */
        Thread t = new Thread(() -> {
            People people = inheritableThreadLocal.get();
            people.sex = "nihao";
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 这里会打印出"nihao"
         * 说明子线程拷贝inheritableThreadLocals是拷贝的引用
         */
        System.out.println(inheritableThreadLocal.get().sex);

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
