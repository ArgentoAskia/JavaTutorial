package cn.argentoaskia.awt.widgets;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ModalDialog {

    public static void main(String[] args) {

        Frame frame = new Frame("Modal and non modal dialog!");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Dialog d1 = new Dialog(frame, "Modal Dialog", true);
        Dialog d2 = new Dialog(frame, "Non Modal Dialog", false);
        d1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                d1.setVisible(false);
            }
        });
        d2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                d2.setVisible(false);
            }
        });

        Button b1 = new Button("open modal dialog");
        Button b2 = new Button("open non modal dialog");

        //设置对话框的大小和位置
        d1.setBounds(20,30,300,400);
        d2.setBounds(20,30,300,400);

        //给b1和b2绑定监听事件
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d1.setVisible(true);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d2.setVisible(true);
            }
        });

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(2, 1, 5, 3));
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        label1.setText("this example shows you what is MODAL DIALOG!!");
        label2.setText("Chick the below buttons and click back this window");
        label3.setText("you will get the answer!:)");
        Panel textPanel = new Panel(new GridLayout(3, 1));
        textPanel.add(label1);
        textPanel.add(label2);
        textPanel.add(label3);
        //把按钮添加到frame中
        frame.add(textPanel);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //设置frame最佳大小并可见
        frame.pack();
        frame.setVisible(true);

    }
}
