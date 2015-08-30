package com.leo.mina.core.filter.code.simple;

import com.leo.mina.core.filter.code.ProtocolEncoder;
import com.leo.mina.core.filter.code.ProtocolEncoderOutput;
import com.leo.mina.core.session.IOSession;
import com.leo.mina.core.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by leo.sz on 2015/8/30.
 */
public class SimpleProtocolEncoder implements ProtocolEncoder {
    public void encode(IOSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        PhoneMessage phoneMessage = (PhoneMessage) message;
        byte [] _num = ArrayUtil.long2Bytes(phoneMessage.getPhoneNum());
        byte [] _phoneContentLength = ArrayUtil.intToByteArray(phoneMessage.getPhoneContentLength());
        byte [] temp = ArrayUtils.addAll(_num, _phoneContentLength);
        byte [] target = ArrayUtils.addAll(temp, phoneMessage.getPhonecontent().getBytes());
        out.write(target);

    }
}
