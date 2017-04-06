package com.adapter;

import com.constant.Constant;
import com.redis.queue.manager.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangyu on 16/11/21.
 */
public abstract class AbstractAdapter<T> implements Adapter {

    /**
     * Adapter ID
     */
    protected String adapterId;

    /**
     * 链表：上一个Adapter
     */
    protected Adapter prev;

    /**
     * 链表：下一个Adapter
     */
    protected Adapter next;

    /**
     * 当前Service runTimeId
     */
    protected String serviceKey;

    /**
     * 开启了多少个线程
     */
    protected volatile int survivorCount;

    @Autowired
    protected QueueManager queueManager;

    @Override
    public void doWait() throws InterruptedException {
        if (prev==null)
            return;
        synchronized (this) {
            this.wait(Constant.WAIT_TIME);
        }
    }

    @Override
    public void doNotify(Adapter next) {
        if (next != null) {
            synchronized (next) {
                next.notifyAll();
            }
        }
    }

    @Override
    public void run() {
        addSurvivor();
        try {
            doWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        execute();

    }

    /**
     * 当Adapter没有从redis取到值，那么判断上一个Adapter是否执行完成
     * 如果没有prev Adatper 或者上个Adapter已经执行完成
     * 那么可以完成自己
     *
     * 如果有prev Adapter并且上一个适配器没有执行完成
     * 那么执行自旋锁
     */
    public void execute() {
        while (true) {
            T t = (T) queueManager.pop(getPrevKey());
            if (t==null){
                if (prev==null||prev.getSurvivorCount()==0){
                    subSurvivor();
                    break;
                }else{
                    Constant.doSpin();
                    continue;
                }
            }

            t = execute(t);
            queueManager.push(getKey(),t);
            doNotify(next);
        }
    }

    public abstract T execute(T t);

    public String getKey(){
        return serviceKey + adapterId;
    }

    protected String getPrevKey(){
        if (prev==null)
            return serviceKey;
        return prev.getKey();
    }

    public synchronized void addSurvivor() {
        survivorCount = survivorCount+1;
    }

    public synchronized void subSurvivor(){
        survivorCount = survivorCount-1;
    }

    @Override
    public Adapter getPrev() {
        return prev;
    }

    public void setPrev(Adapter prev) {
        this.prev = prev;
    }

    @Override
    public Adapter getNext() {
        return next;
    }

    public void setNext(Adapter next) {
        this.next = next;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    @Override
    public String getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(String adapterId) {
        this.adapterId = adapterId;
    }

    @Override
    public int getSurvivorCount() {
        return survivorCount;
    }

    public void setSurvivorCount(int survivorCount) {
        this.survivorCount = survivorCount;
    }
}
