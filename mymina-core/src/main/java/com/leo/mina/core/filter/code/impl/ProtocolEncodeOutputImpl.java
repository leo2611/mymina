package com.leo.mina.core.filter.code.impl;

import com.leo.mina.core.buffer.IOBuffer;
import com.leo.mina.core.filter.code.ProtocolEncodeOutput;
import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

import java.nio.ByteBuffer;

/**
 * Created by leo.sz on 2015/8/4.
 */
public class ProtocolEncodeOutputImpl implements ProtocolEncodeOutput {


    public void write(Object message) {

    }

    public void flush(IOFilterNode nextFilter, IOSession session) {

    }

    public ByteBuffer getByteBuffer() {
        return null;
    }

    public void setIOBuffer(ByteBuffer byteBuffer) {

    }
}
