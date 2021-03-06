package com.leo.mina.core.servicce;

import com.leo.mina.core.biz.IOHandler;
import com.leo.mina.core.filter.IOFilterChain;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Created by leo.sz on 2015/7/4.
 */
public interface IOService {

//    void addListener(IoServiceListener listener);
//    void removeListener(IoServiceListener listener);
    boolean isDisposing();
    boolean isDisposed();
    void dispose();
    IOHandler getHandler();
    void setHandler(IOHandler ioHandler);
//    void setHandler(IOHandler handler);
//    Map<Long, IoSession> getManagedSessions();
//    int getManagedSessionCount();
//    IoSessionConfig getSessionConfig();
    IOFilterChain getIOFilterChain();
//     void setIOFilterChain(IOFilterChainBuilder builder);
     boolean isActive();
    public void bind(SocketAddress localAddress) throws IOException;
//    IoSessionDataStructureFactory getSessionDataStructureFactory();
//    void setSessionDataStructureFactory(IoSessionDataStructureFactory sessionDataStructureFactory);

}
