package org.py.networkCollector.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWin extends AbstractFrame {
    private int width;
    private int height;
    @Override
    protected void init() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public MainWin(String title, int width, int height) throws HeadlessException {
        super(title);
        this.width = width;
        this.height = height;
        init();
    }
}
