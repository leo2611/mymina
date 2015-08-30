package com.leo.mina.core.filter.code.simple;

import com.leo.mina.core.filter.code.ProtocolDecodeInPut;
import com.leo.mina.core.filter.code.ProtocolDecoder;
import com.leo.mina.core.session.IOSession;
import com.leo.mina.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by leo.sz on 2015/8/29.
 */
public class SimpleProtocolDecoder implements ProtocolDecoder {
    public static final byte []  phoneNum = "phoneNum".getBytes();
    public static final int intLengthOfByte = 4;
    public static final int longLengthOfByte = 8;
    public void decode(IOSession session, Object message, ProtocolDecodeInPut in) {
//        if(message == null || session ==null || in ==null){
//            return;
//        }
        byte [] phoneMessageByte = (byte [])message;
        int index = 0;
        while(true) {
            long phoneNum;
            int phoneContentLength = 0;
            String phonecontent = null;
            if (phoneMessageByte.length >= index + longLengthOfByte) {
                phoneNum = ArrayUtil.bytesToLong(phoneMessageByte, index);
                index += longLengthOfByte;
            } else {
                break;
            }
            if (phoneMessageByte.length >= intLengthOfByte + index) {
                phoneContentLength = ArrayUtil.byteArrayToInt(phoneMessageByte, index);
                index += intLengthOfByte;
            } else {
                index -= intLengthOfByte;
                break;
            }
            if (phoneMessageByte.length >= index + phoneContentLength) {
                phonecontent = ArrayUtil.byteArrayToString(phoneMessageByte, index, phoneContentLength);
                index += phoneContentLength;
            }else {
                index = index - intLengthOfByte - longLengthOfByte;
                break;
            }
            PhoneMessage phoneMessage = new PhoneMessage();
            phoneMessage.setPhoneNum(phoneNum);
            phoneMessage.setPhoneContentLength(phoneContentLength);
            phoneMessage.setPhonecontent(phonecontent);
            in.write(phoneMessage);
        }
        byte [] remaining = ArrayUtil.subarray((byte [])message,index,((byte[]) message).length);
        session.setRemainingData(remaining);
    }
    public List<PhoneMessage> decode( Object message, ProtocolDecodeInPut in) {
//        if(message == null || session ==null || in ==null){
//            return;
//        }
        List<PhoneMessage> list = new ArrayList<PhoneMessage>();
        byte [] phoneMessageByte = (byte [])message;
        int index = 0;
        while(true) {
            long phoneNum;
            int phoneContentLength = 0;
            String phonecontent = null;
            if (phoneMessageByte.length >= index + longLengthOfByte) {
                phoneNum = ArrayUtil.bytesToLong(phoneMessageByte, index);
                index += longLengthOfByte;
            } else {
                break;
            }
            if (phoneMessageByte.length >= intLengthOfByte + index) {
                phoneContentLength = ArrayUtil.byteArrayToInt(phoneMessageByte, index);
                index += intLengthOfByte;
            } else {
                index -= intLengthOfByte;
                break;
            }
            if (phoneMessageByte.length >= index + phoneContentLength) {
                phonecontent = ArrayUtil.byteArrayToString(phoneMessageByte, index, phoneContentLength);
                index += phoneContentLength;
            }else {
                index = index - intLengthOfByte - longLengthOfByte;
                break;
            }
            PhoneMessage phoneMessage = new PhoneMessage();
            phoneMessage.setPhoneNum(phoneNum);
            phoneMessage.setPhoneContentLength(phoneContentLength);
            phoneMessage.setPhonecontent(phonecontent);
            list.add(phoneMessage) ;
        }
       return list;
    }

}
