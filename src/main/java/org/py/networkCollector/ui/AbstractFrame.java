package org.py.networkCollector.ui;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractFrame extends JFrame {
    protected abstract void init();
    public AbstractFrame() throws HeadlessException {
        super();
    }

    public AbstractFrame(GraphicsConfiguration gc) {
        super(gc);
    }

    public AbstractFrame(String title) throws HeadlessException {
        super(title);
    }

    public AbstractFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    public void exit() {
        System.gc();
        dispose();
    }
}
