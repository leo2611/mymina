package com.leo.mina.core.servicce;

import com.leo.mina.core.biz.IOHandler;
import com.leo.mina.core.filter.IOFilterChain;
import com.leo.mina.core.filter.impl.IOFilterChainImpl;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo.sz on 2015/7/7.
 */
public abstract class AbstractIOService implements IOService {
    private Logger logger = Logger.getLogger(AbstractIOService.class);
    protected ExecutorService executorService ;
    protected Selector selector = null;
    protected IOprocessor [] iOprocessor ;
    protected IOFilterChain ioFilterChain;
    protected IOHandler ioHandler;
    protected volatile boolean activate ;

    protected LinkedBlockingQueue<SelectionKey> linkedBlockingQueue = new LinkedBlockingQueue<SelectionKey>();
    public AbstractIOService(){
        activate = true;
        ioFilterChain = new IOFilterChainImpl();
        int coreNum = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(2*coreNum);
        try {
            selector = Selector.open();
        }catch (IOException e){
            logger.error("打开selector出错",e);
        }

    }



    public boolean isDisposing() {
        return activate;
    }

    public boolean isDisposed() {
        return activate;
    }

    public void dispose() {
        activate = false;
    }



    public IOHandler getHandler() {
        return ioHandler;
    }

    public void setHandler(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    public IOFilterChain getIOFilterChain() {
        return ioFilterChain;
    }

    public boolean isActive() {
        return activate;
    }

    public void bind(SocketAddress localAddress)throws IOException  {
        if(localAddress ==null) {
            throw new IllegalArgumentException("localAddress");
        }
        bind0(localAddress);

    }

    protected abstract void bind0(SocketAddress localAddress);
}

