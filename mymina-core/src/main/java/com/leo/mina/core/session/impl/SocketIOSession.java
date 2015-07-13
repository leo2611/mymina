package com.leo.mina.core.session.impl;

import com.leo.mina.core.filter.IOFilterChain;
import com.leo.mina.core.servicce.IoService;
import com.leo.mina.core.session.IOSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Created by leo.sz on 2015/7/8. TODO 职责链 实现
 */
public class SocketIOSession implements IOSession {
    private Logger logger = Logger.getLogger(SocketIOSession.class);
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
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        String msg = null;
        ByteBuffer buf = ByteBuffer.allocate(2048);
        try {
            int readNum = socketChannel.read(buf);
            if (readNum == -1) {
                socketChannel.close();
                return;
            } else {
                buf.flip();
                msg = Charset.forName("GBK").newDecoder().decode(buf).toString();
                buf.clear();
            }
        }catch (IOException e){
            logger.error("读取通道失败",e);
        }catch (UnsupportedCharsetException e){
            logger.error("字符转换失败",e);
        }
        ioService.getHandler().messageReceived(this,msg);
    }

    public void messageWrite() {

    }
}
