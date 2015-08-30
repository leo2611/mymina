package com.leo.mina.core.filter.impl;

import com.leo.mina.core.filter.IOFilter;
import com.leo.mina.core.filter.code.*;
import com.leo.mina.core.filter.code.impl.ProtocolDecodeInPutImpl;
import com.leo.mina.core.filter.code.impl.ProtocolEncoderOutputImpl;
import com.leo.mina.core.session.IOSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leo.sz on 2015/7/26.
 */
public class ProtocolCodecFilter implements IOFilter {
    private Logger logger = LoggerFactory.getLogger(ProtocolCodecFilter.class);
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
        in = (ProtocolDecodeInPut)ioSession.getAttribute("decoderInPut");
        if(in == null){
            in = new ProtocolDecodeInPutImpl();
            ioSession.setAttribute("decoderInPut" , in);
        }
        byte [] remainingData = (byte [])ioSession.getAttribute("remainingData");
        byte [] data = (byte [])msg;
        if(remainingData != null || remainingData.length > 0){
            byte[] temp = new byte[ remainingData.length + data.length ];
            System.arraycopy(remainingData, 0, temp, 0, remainingData.length);
            System.arraycopy(data, 0, temp, 0, data.length);
            data = temp;
        }
        protocolDecoder.decode(ioSession,data,in);
        in.flush(next,ioSession);

    }

    public void messageSent(IOFilterNode next, IOSession ioSession, Object msg) {
        ProtocolEncoder protocolEncoder = null;
        try{
            protocolEncoder = protocolCodecFactory.getEncoder(ioSession);
        }catch (Exception e){

        }
        ProtocolEncoderOutput protocolEncoderOutput;
        protocolEncoderOutput =  (ProtocolEncoderOutput)ioSession.getAttribute("encoderOutPut");
        if(protocolEncoderOutput == null){
            protocolEncoderOutput = new ProtocolEncoderOutputImpl();
            ioSession.setAttribute("encoderOutPut",protocolEncoderOutput);
        }
        try {
            protocolEncoder.encode(ioSession, msg, protocolEncoderOutput);
        }catch (Exception e){
            logger.error("ProtocolCodecFilter.messageSent invoke protocolEncoder.encode error",e);
        }
        protocolEncoderOutput.flush(next,ioSession);
    }
}
