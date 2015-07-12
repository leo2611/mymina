package com.leo.mina.core.filter;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/9.
 */
public class SentinelIOFilter implements IOFilter{
    public void messageReceived(IOFilter next, IOSession ioSession) {
        return;
    }

    public void init() {

    }

    public void messageSent(IOFilter next, IOSession ioSession) {
        return;
    }
}
