package com.redis.queue.manager.impl;

import com.redis.queue.dao.QueueDao;
import com.redis.queue.manager.QueueManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by yangyu on 16/10/14.
 */
@Service("queueManager")
public class QueueManagerImpl implements QueueManager {

    @Autowired
    private QueueDao queueDao;

    @Override
    public Object pop(String queueName) {
        return this.queueDao.pop(queueName);
    }

    @Override
    public void push(String queueName, Object o) {
        this.queueDao.push(queueName,o);
    }

    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis.xml");
        QueueManager queueManager = (QueueManager) applicationContext.getBean("queueManager");
        ArrayList list = new ArrayList();
        list.add("data1");
        list.add("data2");
        queueManager.push("1",list);
        list.add("data100");
        queueManager.push("1",list);
    }
}
