package com.adapter;

/**
 * Created by yangyu on 16/11/19.
 */
public interface Adapter extends Runnable{

    public Adapter getNext();

    public Adapter getPrev();

    public void doWait() throws InterruptedException;

    public void doNotify(Adapter next);

    public int getSurvivorCount();

    public String getAdapterId();

    public String getKey();

    public void setServiceKey(String serviceKey);
}
