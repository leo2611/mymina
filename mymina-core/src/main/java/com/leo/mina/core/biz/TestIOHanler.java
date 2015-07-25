package com.leo.mina.core.biz;

import com.leo.mina.core.session.IOSession;

import java.nio.charset.CharacterCodingException;
import java.util.Date;

/**
 * Created by leo.sz on 2015/7/25.
 */
public class TestIOHanler implements IOHandler {
    public void messageReceived(IOSession session, Object message) {
        String msg = message.toString();
        if( msg.trim().equalsIgnoreCase("exit") ){
            return;
        }
        Date date = new Date();
        try {
            session.write(date.toString());
        }catch (CharacterCodingException e){
            System.out.println("调用出错");
        }
        System.out.println(msg);
        System.out.println("Message written...");
    }

    public void messageWrite(IOSession session) {

    }
}
