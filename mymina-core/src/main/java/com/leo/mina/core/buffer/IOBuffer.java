package com.leo.mina.core.buffer;

import java.nio.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by leo.sz on 2015/7/4.
 */
public abstract class IOBuffer {
    //private static IoBufferAllocator allocator = new SimpleBufferAllocator();
    private static boolean useDirectBuffer = false;

    public static int capacity = 2048;
    public static boolean isUseDirectBuffer() {
        return useDirectBuffer;
    }

    public static void setUseDirectBuffer(boolean useDirectBuffer) {
        IOBuffer.useDirectBuffer = useDirectBuffer;
    }


    public abstract void free();
    public abstract ByteBuffer buf();

    public abstract int capacity() ;


    public abstract IOBuffer capacity(int newCapacity);


    public abstract int position();


    public abstract IOBuffer position(int newPosition) ;


    public abstract int limit() ;


    public abstract IOBuffer limit(int newLimit);

    public abstract IOBuffer mark() ;


    public abstract int markValue();


    public abstract IOBuffer reset() ;


    public abstract IOBuffer clear() ;


    public abstract IOBuffer flip() ;


    public abstract IOBuffer rewind();


    public abstract int remaining();


    public abstract boolean hasRemaining();


    public abstract byte[] array();


    public abstract byte get();

    public abstract IOBuffer put(byte b);


    public abstract byte get(int index) ;


    public abstract IOBuffer put(int index, byte b) ;


    public abstract IOBuffer get(byte[] dst, int offset, int length);


    public abstract IOBuffer get(byte[] dst);


    public abstract IOBuffer put(ByteBuffer src);


    public abstract IOBuffer put(IOBuffer src) ;


    public abstract IOBuffer put(byte[] src) ;


    public abstract char getChar() ;

    public abstract IOBuffer putChar(char value);


    public abstract char getChar(int index);


    public abstract IOBuffer putChar(int index, char value);


    public abstract CharBuffer asCharBuffer() ;


    public abstract int getInt();


    public abstract int getInt(int index) ;


    public abstract IOBuffer putInt(int value) ;


    public abstract IOBuffer putInt(int index, int value) ;


    public abstract IntBuffer asIntBuffer() ;


    public abstract double getDouble() ;


    public abstract IOBuffer putDouble(double value);


    public abstract double getDouble(int index);

    public abstract IOBuffer putDouble(int index, double value);


    public abstract DoubleBuffer asDoubleBuffer() ;


    public abstract String getString(CharsetDecoder decoder) throws CharacterCodingException ;


    public abstract IOBuffer putString(CharSequence val, CharsetEncoder encoder) throws CharacterCodingException;


    public abstract IOBuffer allocate(int capacity) ;


    public abstract IOBuffer allocate() ;


}
