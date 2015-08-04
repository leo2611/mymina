package com.leo.mina.core.filter.impl;

import com.leo.mina.core.filter.IOFilter;
import com.leo.mina.core.filter.code.*;
import com.leo.mina.core.filter.code.impl.ProtocolDecodeInPutImpl;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/26.
 */
public class ProtocolCodecFilter implements IOFilter {
    private ProtocolDecodeInPut in ;
    private ProtocolCodecFactory protocolCodecFactory;
    public ProtocolCodecFilter(ProtocolCodecFactory protocolCodecFactory){
        this.protocolCodecFactory = protocolCodecFactory;
    }
    public void init() {

    }

    public void messageReceived(IOFilterNode next, IOSession ioSession, Object msg) {
        ProtocolDecoder protocolDecoder = null;
        try{
            protocolDecoder = protocolCodecFactory.getdecoder(ioSession);
        }catch (Exception e){

        }
        in = (ProtocolDecodeInPut)ioSession.getAttribute("DecodeInPut");
        if(in == null){
            in = new ProtocolDecodeInPutImpl();
            ioSession.setAttribute( "DecodeInPut" , in);
        }
        protocolDecoder.decode(ioSession,msg,in);
        in.flush(next,ioSession);

    }

    public void messageSent(IOFilterNode next, IOSession ioSession, Object msg) {

    }
}
