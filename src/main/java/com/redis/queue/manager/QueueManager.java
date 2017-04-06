package com.redis.queue.manager;

/**
 * Created by yangyu on 16/10/14.
 */
public interface QueueManager<T> {
    public T pop(String queueName);

    public void push(String queueName, T t);
}
