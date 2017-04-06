package com.adapter;

import com.redis.queue.manager.QueueManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

/**
 * Created by yangyu on 16/11/21.
 */
public class Mapper extends AbstractAdapter<ArrayList<String>> {

    @Override
    public ArrayList execute(ArrayList<String> arrayList) {
        for (String str : arrayList){
            System.out.println(str);
        }
        arrayList.add("data3");
        arrayList.add("data4");
        return arrayList;
    }

    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis.xml");
        QueueManager queueManager = (QueueManager) applicationContext.getBean("queueManager");
        this.queueManager = queueManager;
        this.serviceKey = "1";
        this.run();
    }
}
