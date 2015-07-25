package com.leo.mina.core.session.impl;

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
import java.nio.charset.UnsupportedCharsetException;

/**
 * Created by leo.sz on 2015/7/8. TODO write method
 */
public class SocketIOSession implements IOSession {
    private Logger logger = Logger.getLogger(SocketIOSession.class);
    private IOService ioService;
    private IOBuffer ioBuffer;
    private SelectionKey selectionKey;
    public SocketIOSession(IOService ioService,SelectionKey selectionKey){
        ioBuffer = IOBuffer.allocate();
        this.ioService = ioService;
        this.selectionKey = selectionKey;
    }

    public void write(String msg) throws CharacterCodingException {
        ioBuffer.putString(msg);
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
        } catch (UnsupportedCharsetException e) {
            logger.error("字符转换失败", e);
        }
        if (readNum == -1) {
            try {
                socketChannel.close();
            }catch (IOException e) {
                logger.error("读取通道失败", e);
            }
            return;
        }
        ioBuffer.flip();
        try{
        msg = ioBuffer.getString();
        }
        catch (CharacterCodingException e){
            logger.error("socketIosession调用iobuffer的getString方法获取字符串失败 解码器问题");
            return;
        }
        if(msg.trim().equalsIgnoreCase(""))
            return;
        ioBuffer.clear();
        ioService.getHandler().messageReceived(this,msg);
//        try {
//            this.write("\r\n");
//        }catch (CharacterCodingException e){
//            logger.error("socketIosession调用iobuffer的getString方法获取字符串失败 解码器问题");
//        }
        if(ioBuffer.remaining() < ioBuffer.capacity()){
            messageWrite();
        }
    }

    public void messageWrite() {
        ioService.getIOFilterChain().messageWrited(this);
        selectionKey.attach(this);
        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
    }

    public IOBuffer getIOBuffer() {
        return ioBuffer;
    }
}
