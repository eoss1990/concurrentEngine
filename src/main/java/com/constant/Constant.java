package com.constant;

/**
 * Created by yangyu on 16/11/21.
 */
public class Constant {

    public static int PAGE_SIZE = 1000;

    public static int MINI_SZIE = 2000;

    public static long WAIT_TIME = 10*1000;

    public static int SPIN_LOCK = 500;

    public static void doSpin(){
        for (int i = 0; i < SPIN_LOCK; i++) {
        }
    }
}
