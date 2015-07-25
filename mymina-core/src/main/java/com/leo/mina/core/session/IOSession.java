package com.leo.mina.core.session;

import com.leo.mina.core.buffer.IOBuffer;

import java.nio.charset.CharacterCodingException;

/**
 * Created by leo.sz on 2015/7/8.
 */
public interface IOSession {
    public void write(String msg) throws CharacterCodingException;
    public String read();
    public void process();
    public void messageReceived();
    public void messageWrite();
    public IOBuffer getIOBuffer();

}
