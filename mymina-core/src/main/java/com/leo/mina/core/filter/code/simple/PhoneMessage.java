package com.leo.mina.core.filter.code.simple;

/**
 * Created by leo.sz on 2015/8/29.
 */
public class PhoneMessage {

    public long phoneNum;
    public int phoneContentLength;
    public String phonecontent;


    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getPhoneContentLength() {
        return phoneContentLength;
    }

    public void setPhoneContentLength(int phoneContentLength) {
        this.phoneContentLength = phoneContentLength;
    }

    public String getPhonecontent() {
        return phonecontent;
    }

    public void setPhonecontent(String phonecontent) {
        this.phonecontent = phonecontent;
    }
}
