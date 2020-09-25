package org.py.networkCollector.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProtocolTest {
    @Test
    public void attrTest() {
        String attr = "http://touimg.com/u/2020/09/07/qvqn22.jpg";
        System.out.println(!attr.startsWith(Protocol.http.get()) && !attr.startsWith(Protocol.https.get()));
    }
}