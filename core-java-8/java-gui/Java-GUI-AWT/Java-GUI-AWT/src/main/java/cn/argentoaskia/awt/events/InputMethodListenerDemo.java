package cn.argentoaskia.awt.events;

import java.awt.*;
import java.awt.event.*;
import java.awt.im.InputMethodRequests;

public class InputMethodListenerDemo extends Frame{
    private TextField textField;
    private TextArea textArea;

    public void init(){
        textField = new TextField(50);
        textArea = new TextArea(10, 50);
        add(textField, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        addWindowListener(windowAdapter);
        textField.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                TextField source = (TextField) e.getSource();
                int id = e.getID();
                System.out.println(id);
            }
        });
        textField.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {
                event.paramString();
                System.out.println("13");
            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });
        textField.enableInputMethods(true);
        InputMethodRequests inputMethodRequests = textField.getInputMethodRequests();

        pack();
        setVisible(true);
    }
    public InputMethodListenerDemo(){
        init();
    }

    public static void main(String[] args) {
        new InputMethodListenerDemo();
    }
}
