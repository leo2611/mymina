package com.leo.mina.core.servicce.socket;

import com.leo.mina.core.biz.TestIOHanler;
import com.leo.mina.core.servicce.AbstractIOService;
import com.leo.mina.core.servicce.IOprocessor;
import com.leo.mina.core.servicce.IOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.Set;


/**
 * Created by leo.sz on 2015/7/7.
 */
public class ServerSocketIOService extends AbstractIOService {
    public static final Logger logger = LoggerFactory.getLogger(ServerSocketIOService.class);
    public ServerSocketIOService(){
        super();
        int coreNum = Runtime.getRuntime().availableProcessors();
        iOprocessor = new SocketIOProcessor[coreNum];
        for(int i = 0 ;i<coreNum ;i++){
            iOprocessor[i] = new SocketIOProcessor(this);
        }

    }

    @Override
    protected void bind0(SocketAddress localAddress) {
        if (isDisposing()) {
            throw new IllegalStateException("Already disposed.");
        }
        IOConnector ioConnector = new IOConnector((InetSocketAddress)localAddress);
        executorService.execute(ioConnector);
    }

    private class IOConnector implements Runnable{
        InetSocketAddress inetSocketAddress = null ;
        public IOConnector(InetSocketAddress inetSocketAddress){
            this.inetSocketAddress = inetSocketAddress;
            for( int i = 0 ; i< iOprocessor.length ;i++){

                executorService.execute((SocketIOProcessor)iOprocessor[i]);
            }
        }
        public void run() {
            try {

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(inetSocketAddress);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            }catch (ClosedChannelException e){
                logger.error("IOConnector invoke nio error ");
            }catch (IOException e){
                logger.error("IOConnector invoke configureBlocking or bind error ",e);
            }
                while(activate){
                    try {
                        if (selector.selectNow() <= 0) {
                            continue;
                        }
                     } catch (Exception e) {

                        logger.error("IOConnector invoke selector.selectNow error",e);

                    }
                    try {

                        Set<SelectionKey> keys = selector.selectedKeys();
                        for (SelectionKey selectionKey : keys) {

                            if (selectionKey.isAcceptable()) {
                                ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                                SocketChannel sc = ssc.accept();
                                sc.configureBlocking(false);
                                getIOprocessor().register(sc);
                                keys.remove(selectionKey);
                            }

                        }
                    }catch (IOException e){
                        logger.error("IOConnector invoke ssc.accept or sc.configureBlocking(false)  error  ",e);
                    }
                }

        }
        /*
            通过算法 获取一个指定的ioprocess TODO 目前是随机获取 后期加上通过统计数字获得
         */
        public IOprocessor getIOprocessor(){
            return iOprocessor[new Random().nextInt(iOprocessor.length)];
        }
    }
    public static void main(String [] args){
        IOService ioService = new ServerSocketIOService();
        TestIOHanler testIOHanler = new TestIOHanler();
        ioService.setHandler(testIOHanler);
        try {
            ioService.bind(new InetSocketAddress("127.0.0.1", 9928));
        }catch (IOException e){
            System.out.println("wrong");
        }

    }
}
