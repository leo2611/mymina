package com.leo.mina;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Set;

/**
 * Created by leo.sz on 2015/7/6.
 */
public class SocketNIOTest extends TestCase {
    private static final int ListenPort = 9994;
    @Test
    public void testNio(){
        Selector selector = null;
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
           // serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.socket().bind(new InetSocketAddress(ListenPort));


            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(!Thread.currentThread().isInterrupted()){
                if(selector.select() <= 0){
                   // System.out.print("独自等待.");
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                for(SelectionKey selectionKey : keys){

                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking( false );
                        sc.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                        keys.remove(selectionKey);
                    }
                    else if(selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int readNum = socketChannel.read(buf);
                        if(readNum == -1){
                            socketChannel.close();
                            continue;
                        }else{
                            buf.flip();
                            String receivedString= Charset.forName("GBK").newDecoder().decode(buf).toString();
                            System.out.println(receivedString);
                          //  String sendString="hahah";
                            buf.clear();
                           // buf=ByteBuffer.wrap(sendString.getBytes("GBK"));
                            //socketChannel.write(buf);
                            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        }
                    }


                }
            }
        }catch (Exception e){
            System.out.print("初五哦.");
        }
    }
}
