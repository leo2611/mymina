package com.leo.mina.core.biz;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/25.
 */
public class HandleSession implements Runnable {
    private IOSession ioSession;
    public HandleSession(IOSession ioSession){
        this.ioSession = ioSession;
    }
    public IOSession getIoSession() {
        return ioSession;
    }

    public void setIoSession(IOSession ioSession) {
        this.ioSession = ioSession;
    }

    public void run() {
        ioSession.messageReceived();
    }
}
