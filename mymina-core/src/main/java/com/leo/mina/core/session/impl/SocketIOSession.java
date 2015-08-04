package com.leo.mina.core.session.impl;

import com.leo.mina.core.biz.HandleSession;
import com.leo.mina.core.buffer.IOBuffer;
import com.leo.mina.core.filter.IOFilterChain;
import com.leo.mina.core.servicce.IOService;
import com.leo.mina.core.session.IOSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leo.sz on 2015/7/8. TODO write method
 */
public class SocketIOSession implements IOSession {
    private ConcurrentHashMap<Object, Object> attributes = new ConcurrentHashMap<Object, Object>();
    private Logger logger = Logger.getLogger(SocketIOSession.class);
    private IOService ioService;
    private IOBuffer ioBuffer;
    private HandleSession handelSession;
    private SelectionKey selectionKey;
    private List writeFuture = new ArrayList();
    public SocketIOSession(IOService ioService,SelectionKey selectionKey){
        ioBuffer = IOBuffer.allocate();
        this.ioService = ioService;
        this.selectionKey = selectionKey;
    }

    public HandleSession getHandelSession() {
        return handelSession;
    }

    public void setHandelSession(HandleSession handelSession) {
        this.handelSession = handelSession;
    }

    public void write(String msg) throws CharacterCodingException {
        writeFuture.add(msg);
    }
    public String read(){
        return "test";
    }

    public void process() {

    }

    public Object getAttribute(Object key) {
        return attributes.get(key);
    }

    public void setAttribute(Object key, Object val) {
        attributes.put(key,val);
    }

    public void messageReceived() {

        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        byte [] msg = null;
        ByteBuffer buf = ByteBuffer.allocate(2048);
        int readNum = 0 ;
        ioBuffer.clear();
        try {
            while ((readNum = socketChannel.read(buf)) > 0) {
                buf.flip();
                ioBuffer.put(buf);
                buf.clear();
            }
        } catch (IOException e) {
            logger.error("读取通道失败", e);
        }
        if (readNum == -1) {
            try {
                socketChannel.close();
            }catch (IOException e) {
                logger.error("读取通道失败 通道关闭", e);
            }
            return;
        }
        ioBuffer.flip();
        msg = ioBuffer.array();
        if(msg.length <= 0)
            return;
        IOFilterChain ioFilterChain = ioService.getIOFilterChain();
        ioFilterChain.messageReceived(this, msg);
//        try {
//            this.write("\r\n");
//        }catch (CharacterCodingException e){
//            logger.error("socketIosession调用iobuffer的getString方法获取字符串失败 解码器问题");
//        }
        if( writeFuture.size() >0){
            messageWrite();
        }
    }

    public void messageWrite() {
       for(Object msg : writeFuture) {
           ioService.getIOFilterChain().messageWrited(this, msg);
       }
        if(ioBuffer.remaining() < ioBuffer.capacity()){
            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
        }

    }

    public IOBuffer getIOBuffer() {
        return ioBuffer;
    }

    public IOService getIOService() {
        return ioService;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

}
