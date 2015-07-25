package com.leo.mina.core.filter.impl;

import com.leo.mina.core.filter.IOFilter;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/9.
 */
public class SentinelIOFilter implements IOFilter {
    public void messageReceived(IOFilterNode next, IOSession ioSession) {
        return;
    }

    public void init() {

    }

    public void messageSent(IOFilterNode next, IOSession ioSession) {
        return;
    }
}
