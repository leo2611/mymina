package com.leo.mina.core.servicce.socket;

import com.leo.mina.core.servicce.AbstractIoService;
import com.leo.mina.core.servicce.IOprocessor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.Set;

/**
 * Created by leo.sz on 2015/7/7.
 */
public class ServerSocketIoService extends AbstractIoService {

    public ServerSocketIoService(){
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
                serverSocketChannel.socket().setReuseAddress(true);
                serverSocketChannel.socket().bind(inetSocketAddress);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                while(!Thread.currentThread().isInterrupted()){
                    if(selector.selectNow() <= 0){
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for(SelectionKey selectionKey : keys){

                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            getIOprocessor().register(sc);
                            keys.remove(selectionKey);
                        }

                    }
                }
            }catch (Exception e){
                System.out.print("出错 哦！！！.");
            }
        }
        /*
            通过算法 获取一个指定的ioprocess TODO 目前是随机获取 后期加上通过统计数字获得
         */
        public IOprocessor getIOprocessor(){
            return iOprocessor[new Random().nextInt(10)+1];
        }
    }
}
