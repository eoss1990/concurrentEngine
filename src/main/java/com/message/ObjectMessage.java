package com.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyu on 16/11/21.
 */
public class ObjectMessage implements Message<ArrayList> {

    private Map messageHeader = new HashMap();

    private ArrayList messageData = new ArrayList();


    @Override
    public Map getMessageHeader() {
        return this.messageHeader;
    }

    @Override
    public ArrayList getMessageData() {
        return this.messageData;
    }

    public void setMessageHeader(Map messageHeader) {
        this.messageHeader = messageHeader;
    }

    public void setMessageData(ArrayList messageData) {
        this.messageData = messageData;
    }
}
