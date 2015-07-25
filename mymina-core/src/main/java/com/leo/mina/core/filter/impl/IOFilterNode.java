package com.leo.mina.core.filter.impl;

import com.leo.mina.core.filter.IOFilter;
import com.leo.mina.core.session.IOSession;

/**
 * Created by leo.sz on 2015/7/25.
 */
public class IOFilterNode {

        private IOFilterNode next;
        private IOFilterNode prev;
        private IOFilter self;
        private String IOFilterName;

        public IOFilterNode() {
            super();
        }

        public void init() {

        }

        public void messageReceived(IOSession ioSession) {
            self.messageReceived(prev,ioSession);
        }

        public void messageSent( IOSession ioSession) {
            self.messageSent(prev,ioSession);
        }

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
