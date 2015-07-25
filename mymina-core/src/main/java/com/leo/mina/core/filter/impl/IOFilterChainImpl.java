package com.leo.mina.core.filter.impl;

import com.leo.mina.core.filter.IOFilter;
import com.leo.mina.core.filter.IOFilterChain;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/9.
 */
public class IOFilterChainImpl implements IOFilterChain {
    protected IOFilterNode head;
    protected IOFilterNode tail;

    public void process() {

    }

    public IOFilterChainImpl() {
        head = new IOFilterNode();
        head.setIOFilterName("SentinelIOFilter");
        head.setNext(null);
        head.setSelf(new SentinelIOFilter());
        head.setPrev(null);
        tail = head;
    }

    public IOFilterChain addLast(String ioFilterName, IOFilter ioFilter) {
        IOFilterNode ioFilterNode = new IOFilterNode();
        ioFilterNode.setSelf(ioFilter);
        ioFilterNode.setNext(null);
        ioFilterNode.setPrev(head);
        ioFilterNode.setIOFilterName(ioFilterName);
        head = ioFilterNode;
        return this;
    }

    public void removeLast() {
        if (head.getIOFilterName().equalsIgnoreCase("SentinelIOFilter"))
            return;
        head = head.getPrev();
    }

    public void remove(String ioFilterName) {
        if (ioFilterName == null || ioFilterName.trim().equalsIgnoreCase(""))
            return;
        if (head.getIOFilterName() == ioFilterName) {
            removeLast();
        }
        for (IOFilterNode ioFilterNode = head; !(ioFilterNode.getIOFilterName().equalsIgnoreCase("SentinelIOFilter")); ioFilterNode = ioFilterNode.getPrev()) {
            if (ioFilterNode.getIOFilterName().equalsIgnoreCase(ioFilterName.trim())) {
                ioFilterNode.getPrev().setNext(ioFilterNode.getNext());
                ioFilterNode.getNext().setPrev(ioFilterNode.getPrev());
                return;
            }
        }
    }

    public void replace(String ioFilterName, IOFilter ioFilter) {
        if (ioFilterName == null || ioFilterName.trim().equalsIgnoreCase(""))
            return;

        for (IOFilterNode ioFilterNode = head; !(ioFilterNode.getIOFilterName().equalsIgnoreCase("SentinelIOFilter")); ioFilterNode = ioFilterNode.getPrev()) {
            if (ioFilterNode.getIOFilterName().equalsIgnoreCase(ioFilterName.trim())) {
                ioFilterNode.setSelf(ioFilter);
                return;
            }
        }
    }

    public void messageReceived(IOSession ioSession) {
        head.getSelf().messageReceived(head.getNext().getSelf(),ioSession);
    }

    public void messageWrited(IOSession ioSession) {

    }

    private class IOFilterNode {
        private IOFilterNode next;
        private IOFilterNode prev;
        private IOFilter self;
        private String IOFilterName;

        public IOFilterNode getNext() {
            return next;
        }

        public void setNext(IOFilterNode next) {
            this.next = next;
        }

        public String getIOFilterName() {
            return IOFilterName;
        }

        public void setIOFilterName(String IOFilterName) {
            this.IOFilterName = IOFilterName;
        }

        public IOFilterNode getPrev() {
            return prev;
        }

        public void setPrev(IOFilterNode prev) {
            this.prev = prev;
        }

        public IOFilter getSelf() {
            return self;
        }

        public void setSelf(IOFilter self) {
            this.self = self;
        }
    }
}