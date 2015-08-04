package com.leo.mina.core.filter.code.impl;

import com.leo.mina.core.filter.code.ProtocolDecodeInPut;
import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo.sz on 2015/8/4.
 */
public class ProtocolDecodeInPutImpl implements ProtocolDecodeInPut {
    private List list = new ArrayList();
    public void write(Object message) {
        list.add(message);
    }

    public void flush(IOFilterNode nextFilter, IOSession session) {
        for(Object msg : list){
            nextFilter.messageReceived(session,msg);
            list.remove(msg);
        }
    }


}
