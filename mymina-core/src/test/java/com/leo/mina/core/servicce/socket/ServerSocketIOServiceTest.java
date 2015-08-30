package com.leo.mina.core.servicce.socket;

import com.leo.mina.core.biz.TestIOHanler;
import com.leo.mina.core.servicce.IOService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.Assert.*;

/**
 * Created by leo.sz on 2015/8/29.
 */
public class ServerSocketIOServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(ServerSocketIOServiceTest.class);
    @Test
    public void testBind() throws Exception {
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