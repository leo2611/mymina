package com.leo.mina.core.filter;

import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/9.
 */
public interface IOFilterChain {
    public void process();
    public IOFilterChain addLast(String ioFilterName,IOFilter ioFilter);
    public void removeLast();
    public void remove(String ioFilterName);
    public void replace(String ioFilterName,IOFilter ioFilter);
    public void messageReceived(IOSession ioSession);
    public void messageWrited(IOSession ioSession);
}
