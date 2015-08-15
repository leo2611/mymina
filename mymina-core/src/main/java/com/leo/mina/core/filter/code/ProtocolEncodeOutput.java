package com.leo.mina.core.filter.code;

import com.leo.mina.core.buffer.IOBuffer;
import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

import java.nio.ByteBuffer;

/**
 * Created by leo.sz on 2015/8/4.
 */
public interface ProtocolEncodeOutput {
    void write(Object message);
    void flush(IOFilterNode nextFilter, IOSession session);
    ByteBuffer getByteBuffer();
    public void setIOBuffer(ByteBuffer byteBuffer);
}
