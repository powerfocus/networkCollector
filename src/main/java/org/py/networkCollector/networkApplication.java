package org.py.networkCollector;

import lombok.extern.java.Log;
import org.py.networkCollector.ui.MainFrame;

import javax.swing.*;

@Log
public class networkApplication {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new MainFrame().show();
    }
}
