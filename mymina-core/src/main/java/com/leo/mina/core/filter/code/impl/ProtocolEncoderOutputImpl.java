package com.leo.mina.core.filter.code.impl;

import com.leo.mina.core.filter.code.ProtocolEncoderOutput;
import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/8/30.
 */
public class ProtocolEncoderOutputImpl implements ProtocolEncoderOutput {
    private byte [] target;
    public void flush(IOFilterNode nextFilter, IOSession session) {
        nextFilter.messageSent(session,target);
    }

    public void write(Object message) {
        target = ( byte [] )message;
    }
}
