package com.service;

import com.adapter.Adapter;
import com.constant.Constant;
import com.message.Message;
import com.message.ObjectMessage;
import com.threadPool.AdapterThreadPool;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yangyu on 16/11/19.
 */
public abstract class AbstractConcurrentService<T> implements Service {

    /**
     * lastAdapter最后一个适配器
     * 用于反向初始化以后进行wait()操作
     */
    protected Adapter lastAdapter;

    /**
     * 数据流转对象
     */
    protected T message;

    public void run() {
        String serviceKey = buildKey();

        if (serviceKey==null)
            throw new RuntimeException("runTimeId不能为空");

        dataSplit2Redis(serviceKey,message);

        Adapter tmp = lastAdapter;
        while (tmp!=null){
            tmp.setServiceKey(serviceKey);
            AdapterThreadPool.excute(tmp);
            tmp = tmp.getPrev();
        }
    }

    /**
     * 判断是否需要拆分数据，并且放入redis
     * @param key redis list key
     * @param message 数据
     */
    public abstract void dataSplit2Redis(String key,T message);

    /**
     * 构建名称，要求，同个service任何时候执行key都不同
     * @return 数据在redis中的list名称
     */
    public String buildKey(){
        return UUID.randomUUID().toString();
    }
}
