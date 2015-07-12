package com.leo.mina.core.servicce;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by leo.sz on 2015/7/8.
 */
public interface IOprocessor {
    public void register(SocketChannel socketChannel);
}
