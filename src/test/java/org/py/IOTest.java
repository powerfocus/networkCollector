package org.py;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public class IOTest {
    @Test
    public void test() {
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream();
        try {
            pout.connect(pin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(pout);
        System.setOut(ps);
        System.setErr(ps);
        System.out.println("hello world.");
    }
}
