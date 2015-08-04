package com.leo.mina.core.session;

import com.leo.mina.core.biz.HandleSession;
import com.leo.mina.core.buffer.IOBuffer;
import com.leo.mina.core.servicce.IOService;

import java.nio.channels.SelectionKey;
import java.nio.charset.CharacterCodingException;

/**
 * Created by leo.sz on 2015/7/8.
 */
public interface IOSession {
    public void write(String msg) throws CharacterCodingException;
    public IOService getIOService();
    public SelectionKey getSelectionKey();
    public String read();
    public void process();
    public void messageReceived();
    public void messageWrite();
    public IOBuffer getIOBuffer();
    public HandleSession getHandelSession() ;
    public void setHandelSession(HandleSession handelSession);
    public Object getAttribute(Object key);
    public void setAttribute(Object key,Object val);

}
