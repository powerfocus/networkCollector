package org.py.networkCollector.service;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public abstract class Service {
    protected String separator;
    protected String dot;
    protected String urlDot;
    protected String newLine;

    public Service() {
        separator = "/";
        dot = ".";
        urlDot = "://";
        newLine = "\r";
    }

    protected String generateName() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    protected Path save(Path path, byte[] data) throws IOException {
        return Files.write(path, data, StandardOpenOption.CREATE_NEW);
    }

    public void printErrors(Exception ex) {
        final ByteArrayOutputStream bouts = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(bouts);
        ex.printStackTrace(ps);
        JOptionPane.showMessageDialog(null, bouts.toString());
    }

    public String getHost(String url) throws MalformedURLException {
        final StringBuffer re = new StringBuffer();
        final URL uri = new URL(url);
        re.append(uri.getProtocol());
        re.append(urlDot);
        re.append(uri.getHost());
        return re.toString();
    }
}
