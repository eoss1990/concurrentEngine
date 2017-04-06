package com;

import java.util.concurrent.Exchanger;

/**
 * Created by yangyu on 16/11/28.
 */

/**
 * Exchanger用于进行线程间的数据交换；
 * 它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据；
 * 两个线程通过exchange方法交换数据，如果一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange方法
 * 当两个线程都达到同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方
 *
 * Exchanger使用场景：
 * 1.遗传算法：遗传算法里需要选出两个人作为交配对象，这时会交换两人的数据，并使用交叉规则得出两个人交配结果。
 * 2.用于校对工作：A、B同时录入数据，然后对A、B进行比较，看是否录入一致，保证数据正确性。
 */
public class TestExchanger {

    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String A = "yangyu";
            try {
                String B = exchanger.exchange(A);
                System.out.println("this is A,but B is:"+B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            String B = "java code";
            try {
                String A = exchanger.exchange(B);
                System.out.println("this is B,but A is:"+A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
