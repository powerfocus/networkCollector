package org.py.networkCollector.service;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class MainService extends Service {
    public File chooserDir(Component parent) {
        final JFileChooser fileChooser = new JFileChooser(new File("/"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(parent);
        return fileChooser.getSelectedFile();
    }

    public boolean valid(String url, String query, String attr, String saveDir) {
        boolean result = true;
        if (url.isEmpty() || query.isEmpty() || attr.isEmpty() || saveDir.isEmpty())
            result = false;
        return result;
    }
}
