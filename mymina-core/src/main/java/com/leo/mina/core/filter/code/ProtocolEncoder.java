package com.leo.mina.core.filter.code;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/27.
 */
public interface ProtocolEncoder {
    void encode(IOSession session, Object message, ProtocolEncoderOutput out) throws Exception;
}
