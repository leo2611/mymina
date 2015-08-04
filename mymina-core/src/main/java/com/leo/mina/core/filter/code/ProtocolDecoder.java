package com.leo.mina.core.filter.code;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/8/3.
 */
public interface ProtocolDecoder {
    void decode(IOSession session, Object message, ProtocolDecodeInPut in) ;
}
