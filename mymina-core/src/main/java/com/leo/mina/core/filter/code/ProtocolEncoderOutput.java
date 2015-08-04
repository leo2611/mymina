package com.leo.mina.core.filter.code;

import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/27.
 */
public interface ProtocolEncoderOutput {
    void write(Object message);
    void flush(IOFilterNode nextFilter, IOSession session);
}
