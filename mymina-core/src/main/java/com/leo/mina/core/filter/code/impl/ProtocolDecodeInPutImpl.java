package com.leo.mina.core.filter.code.impl;

import com.leo.mina.core.buffer.IOBuffer;
import com.leo.mina.core.filter.code.ProtocolDecodeInPut;
import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo.sz on 2015/8/4.
 */
public class ProtocolDecodeInPutImpl implements ProtocolDecodeInPut {
    private List list = new ArrayList();
    private ByteBuffer byteBuffer;
    public void write(Object message) {
        list.add(message);
    }

    public void flush(IOFilterNode nextFilter, IOSession session) {
        for(Object msg : list){
            nextFilter.messageReceived(session,msg);
            list.remove(msg);
        }
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
