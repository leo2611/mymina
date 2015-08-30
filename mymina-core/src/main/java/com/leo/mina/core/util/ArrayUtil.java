package com.leo.mina.core.util;

/**
 * Created by leo.sz on 2015/8/29.
 */
public class ArrayUtil {
    public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return null;
        }

        final byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }
    public static byte[] intToByteArray(int src) {
        byte[] result = new byte[4];

        result[0] = (byte)((src >> 24) & 0xFF);
        result[1] = (byte)((src >> 16) & 0xFF);
        result[2] = (byte)((src >> 8) & 0xFF);
        result[3] = (byte)(src & 0xFF);
        return result;
    }
    public static byte[] intToByteArray(int src ,int index) {
        byte[] result = new byte[4];

        result[index] = (byte)((src >> 24) & 0xFF);
        result[index + 1] = (byte)((src >> 16) & 0xFF);
        result[index + 2] = (byte)((src >> 8) & 0xFF);
        result[index + 3] = (byte)(src & 0xFF);
        return result;
    }
    public static int byteArrayToInt(byte[] src) {
        if(src == null)
            return 0;
        return   src[3] & 0xFF |
                (src[2] & 0xFF) << 8 |
                (src[1] & 0xFF) << 16 |
                (src[0] & 0xFF) << 24;
    }

    public static int byteArrayToInt(byte[] src ,int index) {
        if(src == null || index >= src.length || index + 3 > src.length){
            return 0;
        }
        return   src[index+ 3] & 0xFF |
                (src[index + 2] & 0xFF) << 8 |
                (src[index + 1] & 0xFF) << 16 |
                (src[index] & 0xFF) << 24;
    }
    public static String byteArrayToString(byte[] src , int index , int length){
        if(src == null || index >= src.length || index + length > src.length){
            return null;
        }
        return new String(src,index,length);
    }
    public static long bytesToLong(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }
    public static long bytesToLong(byte[] byteNum,int index) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix + index] & 0xff);
        }
        return num;
    }
    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static byte[] longToBytes(long num ,int index) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix + index] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static void main(String [] args){
        int a =2611;
        byte [] test = intToByteArray(a);
        System.out.print(byteArrayToInt(test));
    }
}
