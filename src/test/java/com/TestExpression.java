package com;

/**
 * Created by yangyu on 16/11/24.
 */
public class TestExpression {
    public static void main(String[] args) {

        int t = 0;
        int p = 0;
        int q = 0;
        int tail = 1;
        int head = 2;

        /**
         * 这个(t != (t = tail))表达式进行判断的时候
         * 第一个t还是未复制之前的值，第二个t是赋值之后的值
         * 所以这里不是想象中的false，而是会出现true的
         * 注意这个细节点
         */
        p = (t != (t = tail)) ? t : head;
        System.out.println(p);
    }
}
