package com.leo.mina.core.biz;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/8.
 */
public interface IOHandler {
    void messageReceived(IOSession session, Object message);
    void messageWrite(IOSession session);

}
