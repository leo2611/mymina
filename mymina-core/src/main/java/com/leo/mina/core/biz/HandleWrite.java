package com.leo.mina.core.biz;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/18.
 */
public class HandleWrite implements Runnable {
    private IOSession ioSession;
    public HandleWrite(IOSession ioSession){
        this.ioSession = ioSession;
    }
    public void run() {
        ioSession.messageWrite();
    }
}
