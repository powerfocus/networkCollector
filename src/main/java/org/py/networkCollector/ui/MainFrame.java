package org.py.networkCollector.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

public class MainFrame {
    private JTextField textField_URL;
    private JLabel JLabel_URL;
    private JRadioButton IDRadioButton;
    private JRadioButton classRadioButton;
    private JTextField textField_Expre;
    private JButton JButton_GrabURL;
    private JButton JButton_Exit;
    private JButton JButton_BowerSavePath;
    private JTextArea textArea_Info;
    private JPanel JPanel_root;

    /**
     * 抓取文件保存路径
     * */
    private Path savePath;

    public MainFrame() {
        /**
         * 抓取网页按钮事件
         * */
        JButton_GrabURL.addActionListener(e -> {
            String url = textField_URL.getText();
            String elemType = IDRadioButton.isSelected() ? "#" : classRadioButton.isSelected() ? "." : "";
            String express = textField_Expre.getText();
            System.out.println("url: " + url);
            System.out.println("类型：" + elemType);
            System.out.println("表达式：" + express);
        });
        /**
         * 退出按钮事件
         * */
        JButton_Exit.addActionListener(e -> {
            final JFrame rootFrame = (JFrame) ((JButton) e.getSource()).getRootPane().getParent();
            System.gc();
            rootFrame.dispose();
        });

        /**
         * 选择保存位置按钮事件
         * */
        JButton_BowerSavePath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MainFrame");
            frame.setContentPane(new MainFrame().JPanel_root);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.gc();
                    frame.dispose();
                }
            });
            frame.setSize(300, 500);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
