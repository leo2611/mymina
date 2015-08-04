package com.leo.mina.core.filter;

import com.leo.mina.core.filter.impl.IOFilterNode;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/9.
 */
public interface IOFilter {
    public void init();
    public void messageReceived(IOFilterNode next,IOSession ioSession,Object msg );
    public void messageSent(IOFilterNode next,IOSession ioSession,Object msg);
}
