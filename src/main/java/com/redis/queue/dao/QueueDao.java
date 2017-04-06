package com.redis.queue.dao;

/**
 * Created by yangyu on 16/10/14.
 */
public interface QueueDao<T> {
    public T pop(String queueName);

    public void push(String queueName, T t);
}
