package com;

import java.util.concurrent.*;

/**
 * Created by yangyu on 16/11/28.
 */

/**
 * Callable and Future用法
 * Callable可以被ExecutorService的submit方法使用，可以取线程执行的返回值；
 *
 * Future是返回值的封装类型：
 * get()方法阻塞当前线程直到获取到返回值
 * isDone()方法判断线程是否执行完成
 * isCancelled()方法判断线程是否被中断
 * cancel(boolean mayInterruptIfRunning)方法中断线程，但是线程方法内必须有中断判断interrupted()，否则是无法中断线程的
 */
public class TestCallableAndFuture {

    private static class Data{
        public int sum;
    }

    /**
     * Callable实现
     */
    private static class TaskCall implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum = sum+i;
            }
            return sum;
        }
    }

    /**
     * Runnable实现，并且判断了线程是否被中断interrupted()
     */
    private static class TaskRun implements Runnable{

        private Data data;

        TaskRun(Data data){
            this.data = data;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                /**
                 * 判断线程如果被中断则跳出循环
                 */
                if (Thread.interrupted())
                    break;
                data.sum = data.sum+i;
            }
        }
    }

    public static void main(String[] args) {
        Data data = new Data();
        /**
         * 初始化一个可缓存线程池
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        /**
         * submit方法执行Callable
         */
        Future<Integer> future1= executorService.submit(new TaskCall());
        /**
         * submit方法执行Runnable,其实future就是对data的封装，实际上使用future.get()返回的是data的引用
         */
        Future<Data> future2 = executorService.submit(new TaskRun(data),data);

        try {
            System.out.println("future1 isCancelled:"+future1.isCancelled());
            System.out.println("future1 result:"+future1.get());
            System.out.println("future1 isDone:"+future1.isDone());


            /**
             * 中断线程
             */
            //future2.cancel(true);
            if (!future2.isCancelled())
            {
                System.out.println("future2 result:"+future2.get().sum);
                System.out.println("future2 isDone:"+future2.isDone());
            }

            System.out.println(data.sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("完成");

    }
}
