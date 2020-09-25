package org.py.networkCollector.ui;

import lombok.extern.java.Log;
import org.py.networkCollector.service.MainService;
import org.py.networkCollector.service.UrlTakeService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Log
public class MainFrame {
    private JTextField textField_URL;
    private JLabel JLabel_URL;
    private JTextField textField_query;
    private JButton JButton_GrabURL;
    private JButton JButton_Exit;
    private JButton JButton_BowerSavePath;
    private JTextArea textArea_Info;
    private JPanel JPanel_root;
    private JTextField textField_attr;
    private JTextField textField_savePath;
    private JButton JButton_reset;
    private JTextField textField_findInfo;

    private MainService mainService;

    private void init() {
        mainService = new MainService();
        /**
         * 抓取网页按钮事件
         * */
        JButton_GrabURL.addActionListener(e -> {
            String url = textField_URL.getText();
            String query = textField_query.getText();
            String attr = textField_attr.getText();
            String saveDir = textField_savePath.getText();
            textArea_Info.setText("");
            if (mainService.valid(url, query, attr)) {
                final UrlTakeService urlTakeService = new UrlTakeService(url, query, attr, Paths.get(saveDir));
                try {
                    urlTakeService.tack(textArea_Info, textField_findInfo);
                } catch (IOException ioException) {
                    mainService.printErrors(ioException);
                }
            } else {
                JOptionPane.showMessageDialog(null, "必要信息未填写完整！请填写完整后重试。");
                textField_URL.grabFocus();
            }
        });

        /**
         * 选择保存位置按钮事件
         * */
        JButton_BowerSavePath.addActionListener(e -> {
            final File file = mainService.chooserDir((JFrame)((JButton)e.getSource()).getRootPane().getParent());
            textField_savePath.setText(file.getAbsolutePath());
        });

        /**
         * 重置按钮事件
         * */
        JButton_reset.addActionListener(e -> {
            textArea_Info.setText("");
            textField_URL.setText("");
            textField_savePath.setText("");
            textField_attr.setText("");
            textField_query.setText("");
            textArea_Info.setText("");
            textField_findInfo.setText("");
            textField_URL.grabFocus();
        });
    }

    public MainFrame() {
        init();
    }

    public void show() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> {
            MainWin frame = new MainWin("网络收集器", 300, 700);
            frame.setContentPane(new MainFrame().JPanel_root);
            frame.setVisible(true);
        });
    }
}
