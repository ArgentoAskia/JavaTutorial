package cn.argentoaskia.awt.events;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerDemo extends Frame{
    private Panel buttonPanel;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private Panel textFieldPanel;
    private TextField textField;
    private Panel listPanel;
    private List list;

    public ActionListenerDemo(){
        initWidgets();
    }

    public void initWidgets(){
        buttonPanel = new Panel();
        button0 = new Button("button0");
        button1 = new Button("button1");
        button2 = new Button("button2");
        button3 = new Button("button3");
        button4 = new Button("button4");
        button5 = new Button("button5");
        button6 = new Button("button6");
        button7 = new Button("butto7");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getModifiers());
            }
        });
        button8 = new Button();
        button9 = new Button();
        listPanel = new Panel();
        list = new List();
        textFieldPanel = new Panel();
        textField = new TextField();
        add(button1);
        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        new ActionListenerDemo();
    }

}
