package com.leo.mina.core.filter;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/9.
 */
interface IOFilter {
    public void init();
    public void messageReceived(IOFilter next,IOSession ioSession);
    public void messageSent(IOFilter next,IOSession ioSession);
}
