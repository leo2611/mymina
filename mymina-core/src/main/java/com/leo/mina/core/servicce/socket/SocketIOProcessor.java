package com.leo.mina.core.servicce.socket;

import com.leo.mina.core.biz.HandelSession;
import com.leo.mina.core.biz.HandleRead;
import com.leo.mina.core.biz.HandleWrite;
import com.leo.mina.core.servicce.IOprocessor;
import com.leo.mina.core.servicce.IoService;
import com.leo.mina.core.session.IOSession;
import com.leo.mina.core.session.impl.SocketIOSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo.sz on 2015/7/8.
 */
public class SocketIOProcessor implements IOprocessor,Runnable {
    private Logger logger = Logger.getLogger(SocketIOProcessor.class);
    private IoService ioService;
    protected Selector selector = null;
    protected ExecutorService executorService;
    protected LinkedBlockingQueue<SocketChannel> linkedBlockingQueue;
    public SocketIOProcessor(IoService ioService){
        this.linkedBlockingQueue = new LinkedBlockingQueue<SocketChannel>();
        this.ioService = ioService;
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
                if (selector.selectNow() <= 0) {
                    continue;
                }
            }catch(IOException e){
                logger.error("SocketIOProcessor 获取选择失败",e);
            }
            Set<SelectionKey> keys =selector.selectedKeys();
            try {
                for (SelectionKey selectionKey : keys) {

                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        sc.register(selector, SelectionKey.OP_READ);
                        keys.remove(selectionKey);
                        IOSession ioSession = new SocketIOSession(ioService, selectionKey);
                        HandelSession handelSession = new HandelSession(ioSession);
                        executorService.execute(handelSession);
                    } else if (selectionKey.isValid() && selectionKey.isWritable()) {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        IOSession ioSession = (IOSession)selectionKey.attachment();
                        sc.register(selector, SelectionKey.OP_READ);
                        keys.remove(selectionKey);
                        sc.write(ioSession.getIOBuffer().buf());
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
