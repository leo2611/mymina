package com.leo.mina.core.filter.code.simple;

import com.leo.mina.core.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by leo.sz on 2015/8/29.
 */
public class SimpleProtocolDecoderTest {

    @Test
    public void testDecode() throws Exception {
        StringBuffer obj = new StringBuffer();
        long num = 15836170817L;
        String content = "hello,worldfffffffff55555ggggggg";
        int phoneContentLength = content.length();
        byte [] _num = ArrayUtil.long2Bytes(num);
        byte [] _phoneContentLength = ArrayUtil.intToByteArray(phoneContentLength);
        byte [] temp = ArrayUtils.addAll(_num, _phoneContentLength);
        byte [] target = ArrayUtils.addAll(temp,content.getBytes());
        SimpleProtocolDecoder simpleProtocolDecoder = new SimpleProtocolDecoder();
        List<PhoneMessage> list = simpleProtocolDecoder.decode(target,null);
        System.out.print(list);

    }
}