package com.leo.mina.core.session;

/**
 * Created by leo.sz on 2015/7/8.
 */
public interface IOSession {
    public void write(String msg);
    public String read();
    public void process();
    public void messageReceived();
    public void messageReceivedWrite();

}
