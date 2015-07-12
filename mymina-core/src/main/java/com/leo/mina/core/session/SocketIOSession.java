package com.leo.mina.core.session;

import com.leo.mina.core.filter.IOFilterChain;
import com.leo.mina.core.servicce.IoService;
import com.leo.mina.core.session.IOSession;

import java.nio.channels.SelectionKey;

/**
 * Created by leo.sz on 2015/7/8. TODO 职责链 实现
 */
public class SocketIOSession implements IOSession {
    private IoService ioService;
    private SelectionKey selectionKey;
    public SocketIOSession(IoService ioService,SelectionKey selectionKey){
        this.ioService = ioService;
        this.selectionKey = selectionKey;
    }

    public void write(String msg) {

    }
    public String read(){
        return "test";
    }

    public void process() {

    }

    public void messageReceived() {
        IOFilterChain ioFilterChain = ioService.getIOFilterChain();
        ioFilterChain.messageReceived(this);

    }

    public void messageReceivedWrite() {

    }
}
