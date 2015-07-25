package com.leo.mina.core.servicce.socket;

import com.leo.mina.core.biz.HandelSession;
import com.leo.mina.core.servicce.IOprocessor;
import com.leo.mina.core.servicce.IOService1;
import com.leo.mina.core.session.IOSession;
import com.leo.mina.core.session.impl.SocketIOSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo.sz on 2015/7/8.
 */
public class SocketIOProcessor implements IOprocessor,Runnable {
    private Logger logger = Logger.getLogger(SocketIOProcessor.class);
    private IOService1 ioService1;
    protected Selector selector = null;
    protected ExecutorService executorService;
    protected LinkedBlockingQueue<SocketChannel> linkedBlockingQueue;
    public SocketIOProcessor(IOService1 ioService1){
        this.linkedBlockingQueue = new LinkedBlockingQueue<SocketChannel>();
        this.ioService1 = ioService1;
        executorService = Executors.newCachedThreadPool();
        try {
            selector = Selector.open();
        }catch (IOException e){
            logger.error("SocketIOProcessor 打开selector出错",e);
        }
    }

    public void register(SocketChannel socketChannel) {
        linkedBlockingQueue.add(socketChannel);
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            SocketChannel socketChannel ;

            if((socketChannel = linkedBlockingQueue.poll()) != null){
                try {
                    socketChannel.register(selector, SelectionKey.OP_READ );
                }catch (ClosedChannelException e){
                    logger.error("SocketIOProcessor 注册通道失败",e);
                }
            }
            try {
                if (selector.select(1000L) <= 0) {
                    continue;
                }
            }catch(IOException e){
                logger.error("SocketIOProcessor 获取选择失败",e);
            }
            Set<SelectionKey> keys =selector.selectedKeys();
            try {
                for (SelectionKey selectionKey : keys) {

                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        keys.remove(selectionKey);
                        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_READ);
                        IOSession ioSession = new SocketIOSession(ioService1, selectionKey);
                        HandelSession handelSession = new HandelSession(ioSession);
                        executorService.execute(handelSession);
                    } else if (selectionKey.isValid() && selectionKey.isWritable()) {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        IOSession ioSession = (IOSession)selectionKey.attachment();
                        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        keys.remove(selectionKey);
                        ByteBuffer byteBuffer = ioSession.getIOBuffer().buf();
                        byteBuffer.flip();
                        while(byteBuffer.hasRemaining()){
                            sc.write(byteBuffer);
                        }

                    }

                }
            }catch (ClosedChannelException e){
                logger.error("SocketIOProcessor 注册关闭通道失败",e);
            }catch (IOException e){
                logger.error("SocketIOProcessor 注册通道失败",e);
            }


        }
    }
}
