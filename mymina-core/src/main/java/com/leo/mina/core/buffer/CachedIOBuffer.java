package com.leo.mina.core.buffer;

import org.apache.log4j.Logger;

import java.nio.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by leo.sz on 2015/7/18.
 */
public class CachedIOBuffer extends IOBuffer {
    private Logger logger = Logger.getLogger(CachedIOBuffer.class);
    private ByteBuffer buf;
    private CharsetEncoder defaultCharsetEncoder;
    private CharsetDecoder  defaultCharsetDecoder;
    public CachedIOBuffer(int capcity){
        buf = ByteBuffer.allocate(capcity);
        defaultCharsetDecoder = Charset.forName("UTF-8").newDecoder();
        defaultCharsetEncoder = Charset.forName("UTF-8").newEncoder();
    }

    @Override
    public void free() {

    }

    @Override
    public int capacity() {
        return buf.capacity();
    }

    @Override
    public IOBuffer capacity(int newCapacity) {
        return null;
    }

    @Override
    public int position() {
        return buf.position();
    }

    @Override
    public IOBuffer position(int newPosition) {
        buf.position(newPosition);
        return  this;
    }

    @Override
    public int limit() {
        return buf.limit();
    }

    @Override
    public IOBuffer limit(int newLimit) {
        buf.limit(newLimit);
        return this;
    }

    @Override
    public IOBuffer mark() {
        buf.mark();
        return this;
    }

    @Override
    public int markValue() {
        return 0;
    }

    @Override
    public IOBuffer reset() {
         buf.reset();
        return  this;
    }

    @Override
    public IOBuffer clear() {
        buf.clear();
        return this;
    }

    @Override
    public IOBuffer flip() {
        buf.flip();
        return this;
    }

    @Override
    public IOBuffer rewind() {
        buf.rewind();
        return this;
    }

    @Override
    public int remaining() {
        return buf.remaining();
    }

    @Override
    public boolean hasRemaining() {
        return buf.hasRemaining();
    }

//    @Override
//    public IOBuffer slice() {
//        return null;
//    }

    @Override
    public byte[] array() {
        return buf.array();
    }

    @Override
    public byte get() {
        return buf.get();
    }
    private IOBuffer extend(int newCapcity){
        ByteBuffer oldBuf = buf;
        ByteBuffer newBuf = ByteBuffer.allocate(buf.capacity() + newCapcity);
        newBuf.put(oldBuf);
        newBuf.limit(oldBuf.limit());
        int position = oldBuf.position();
        int mark = -1;
        try {
            oldBuf.reset();
        }catch (InvalidMarkException e){
            mark = 0;
        }
        if(mark != 0){
            mark = oldBuf.position();
            newBuf.position(mark);
            newBuf.mark();
        }
        newBuf.position(position);
        buf = newBuf ;
        return this;
    }
    @Override
    public IOBuffer put(byte b) {
        if(buf.hasRemaining()){
             buf.put(b);
        } else{
            extend(IOBuffer.capacity);
            buf.put(b);
        }
       return this;
    }

    @Override
    public byte get(int index) {
        return buf.get(index);
    }

    @Override
    public IOBuffer put(int index, byte b) {
        if(index >= buf.limit())
            return null;
        buf.put(index,b);
        return this;
    }

    @Override
    public IOBuffer get(byte[] dst, int offset, int length) {
        buf.get(dst,offset,length);
        return this;
    }

    @Override
    public IOBuffer get(byte[] dst) {
        buf.get(dst);
        return this;
    }

    @Override
    public IOBuffer put(ByteBuffer src) {
        if(buf.remaining() <= src.limit()){
            extend(src.limit());
        }
        buf.put(src);
        return this;
    }

    @Override
    public IOBuffer put(IOBuffer src) {
        return null;
    }

    @Override
    public IOBuffer put(byte[] src) {
        if(buf.remaining() <= src.length)
            extend(src.length);
        buf.put(src);
        return this;
    }

    @Override
    public char getChar() {
        return buf.getChar();
    }

    @Override
    public IOBuffer putChar(char value) {
        if( remaining() < 64 )
            extend(256);
        buf.putChar(value);
        return this;
    }

    @Override
    public char getChar(int index) {
        return buf.getChar(index);
    }

    @Override
    public IOBuffer putChar(int index, char value) {
        buf.putChar(index,value);
        return this;
    }

    @Override
    public CharBuffer asCharBuffer() {
        return buf.asCharBuffer();
    }

    @Override
    public int getInt() {
        return buf.getInt();
    }

    @Override
    public int getInt(int index) {
        return buf.getInt(index);
    }

    @Override
    public IOBuffer putInt(int value) {
        if(remaining() < 64)
            extend(256);
        buf.putInt(value);
        return this ;
    }

    @Override
    public IOBuffer putInt(int index, int value) {
        buf.putInt(index,value);
        return this;
    }

    @Override
    public IntBuffer asIntBuffer() {
        return buf.asIntBuffer();
    }

    @Override
    public double getDouble() {
        return buf.getDouble();
    }

    @Override
    public IOBuffer putDouble(double value) {
        if(remaining() < 64)
            extend(256);
        buf.putDouble(value);
        return this ;
    }

    @Override
    public double getDouble(int index) {
        return buf.getDouble();
    }

    @Override
    public IOBuffer putDouble(int index, double value) {
        if(remaining() < 64)
            extend(256);
        buf.putDouble(index,value);
        return this ;
    }

    @Override
    public DoubleBuffer asDoubleBuffer() {
        return buf.asDoubleBuffer();
    }

    @Override
    public String getString(CharsetDecoder decoder) throws CharacterCodingException {
        String ret = decoder.decode(buf).toString();
        return ret;
    }

    @Override
    public String getString() throws CharacterCodingException {
        String ret = null;
        try {
            ret = defaultCharsetDecoder.decode(buf).toString();
        }catch (CharacterCodingException e){
            logger.error("cachedIObuffer调用默认uft-8 解码器 出错 ",e);
            e.printStackTrace();
            throw e;
        }
        return ret;
    }

    @Override
    public IOBuffer putString(CharSequence val, CharsetEncoder encoder) throws CharacterCodingException {
        CharBuffer charBuffer = CharBuffer.wrap(val);
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        buf.put(byteBuffer);
        return this;
    }

    @Override
    public IOBuffer putString(CharSequence val) throws CharacterCodingException{
        CharBuffer charBuffer = CharBuffer.wrap(val);
        ByteBuffer byteBuffer = null;
        try {
            byteBuffer = defaultCharsetEncoder.encode(charBuffer);
        }catch (CharacterCodingException e){
            logger.error("cachedIObuffer调用默认uft-8 编码器 出错 ",e);
            e.printStackTrace();
            throw e;
        }
        buf.put(byteBuffer);
        return this;
    }

    @Override
    public ByteBuffer buf() {
        return buf;
    }

//    @Override
//    public IOBuffer allocate(int capacity) {
//        return new CachedIOBuffer(capacity);
//    }
//
//    @Override
//    public IOBuffer allocate() {
//        return new CachedIOBuffer(IOBuffer.capacity);
//    }

}
