package com.leo.mina.core.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;

/**
 * Created by leo.sz on 2015/7/4.
 */
public abstract class IoBuffer {
    //private static IoBufferAllocator allocator = new SimpleBufferAllocator();
    private static boolean useDirectBuffer = false;

    public static boolean isUseDirectBuffer() {
        return useDirectBuffer;
    }

    public static void setUseDirectBuffer(boolean useDirectBuffer) {
        IoBuffer.useDirectBuffer = useDirectBuffer;
    }

    public abstract IoBuffer putString(CharSequence val, CharsetEncoder encoder) throws CharacterCodingException;
    public abstract void free();
    public abstract ByteBuffer buf();
//    public static void setAllocator(IoBufferAllocator newAllocator) {
//        if (newAllocator == null) {
//            throw new IllegalArgumentException("allocator");
//        }
//
//        IoBufferAllocator oldAllocator = allocator;
//
//        allocator = newAllocator;
//
//        if (null != oldAllocator) {
//            oldAllocator.dispose();
//        }
//    }
    public IoBuffer allocate(int capacity){
        return null;
    }
}
