package com.message;

import java.util.Map;

/**
 * Created by yangyu on 16/11/19.
 */
public interface Message<T> {

    public Map getMessageHeader();

    public T getMessageData();
}
