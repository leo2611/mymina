package com.leo.mina.core.biz;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/18.
 */
public class HandleRead implements Runnable {
    private IOSession ioSession;
    public HandleRead(IOSession ioSession){
        this.ioSession = ioSession;
    }
    public void run() {
        ioSession.messageReceived();
    }
}
