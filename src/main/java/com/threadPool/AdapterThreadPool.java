package com.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangyu on 16/10/18.
 */
public class AdapterThreadPool {

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private AdapterThreadPool(){};

    public static void excute(Runnable runnable){
        cachedThreadPool.execute(runnable);
    }
}
