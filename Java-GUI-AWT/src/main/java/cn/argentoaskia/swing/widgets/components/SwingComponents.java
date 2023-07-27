package cn.argentoaskia.swing.widgets.components;

import javax.swing.*;
import java.awt.*;

public class SwingComponents extends JFrame {
    private JPanel ComponentsPanel;
    private JTabbedPane tabbedPane1;
    private JPanel buttonPanel;
    private JPanel checkBoxPanel;
    private JPanel radioButtonPanel;
    private JPanel labelPanel;
    private JPanel tabbedPane;

    private JButton[] buttons;

    public static void main(String[] args) {
        SwingComponents swingComponents = new SwingComponents();
        swingComponents.setTitle("Swing组件大全！");
        swingComponents.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        swingComponents.setBounds(100, 123, 100, 123);
        swingComponents.setVisible(true);
//        new SwingComponents();
    }

    public SwingComponents() {
        $$$setupUI$$$();
        setContentPane(ComponentsPanel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 5, 10, 10));
        buttons = new JButton[25];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("按钮" + i);
            buttons[i].setToolTipText("这是一个普通的按钮！");
            buttonPanel.add(buttons[i]);
        }
        buttons[3].setText("<h1 style='color:green;'>按钮3</h1>");
        buttons[3].setSelected(true);

    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        ComponentsPanel = new JPanel();
        ComponentsPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1 = new JTabbedPane();
        ComponentsPanel.add(tabbedPane1, BorderLayout.CENTER);
        tabbedPane1.addTab("Button", buttonPanel);
        tabbedPane1.addTab("CheckBox", checkBoxPanel);
        tabbedPane1.addTab("RadioButton", radioButtonPanel);
        tabbedPane1.addTab("Label", labelPanel);
        tabbedPane1.addTab("TabbedPane", tabbedPane);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return ComponentsPanel;
    }

}
