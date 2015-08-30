package com.leo.mina.core.filter.impl;

import com.leo.mina.core.buffer.IOBuffer;
import com.leo.mina.core.filter.IOFilter;
import com.leo.mina.core.servicce.IOService;
import com.leo.mina.core.session.IOSession;

import java.nio.channels.SelectionKey;

/**
 * Created by leo.sz on 2015/7/9.
 */
public class SentinelIOFilter implements IOFilter {
    public void messageReceived(IOFilterNode next, IOSession ioSession,Object msg) {
        IOService ioService = ioSession.getIOService();
        ioService.getHandler().messageReceived(ioSession,msg);
    }

    public void init() {

    }

    public void messageSent(IOFilterNode next, IOSession ioSession,Object msg) {
        SelectionKey selectionKey = ioSession.getSelectionKey();
        IOBuffer ioBuffer = ioSession.getIOBuffer();
        byte [] bytes = (byte [])msg;
        ioBuffer.put(bytes);
    }
}
