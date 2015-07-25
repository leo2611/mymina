package com.leo.mina.core.servicce.socket;

import com.leo.mina.core.biz.TestIOHanler;
import com.leo.mina.core.servicce.AbstractIOService1;
import com.leo.mina.core.servicce.IOprocessor;
import com.leo.mina.core.servicce.IOService1;

import java.io.IOException;
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
public class ServerSocketIOService1 extends AbstractIOService1 {

    public ServerSocketIOService1(){
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
            return iOprocessor[new Random().nextInt(iOprocessor.length)];
        }
    }
    public static void main(String [] args){
        IOService1 ioService1 = new ServerSocketIOService1();
        TestIOHanler testIOHanler = new TestIOHanler();
        ioService1.setHandler(testIOHanler);
        try {
            ioService1.bind(new InetSocketAddress("127.0.0.1", 9928));
        }catch (IOException e){
            System.out.println("wrong");
        }

    }
}
