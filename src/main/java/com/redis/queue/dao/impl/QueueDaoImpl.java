package com.redis.queue.dao.impl;

import com.redis.queue.dao.QueueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by yangyu on 16/10/14.
 */
@Repository
public class QueueDaoImpl implements QueueDao {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object pop(String queueName) {
        ListOperations listOperations = redisTemplate.opsForList();
        return listOperations.rightPop(queueName);
    }

    @Override
    public void push(String queueName, Object o) {
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush(queueName, o);
    }
}
