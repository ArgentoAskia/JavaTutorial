package cn.argentoaskia.swing.widgets;

import javax.swing.*;
import java.awt.*;

public class SwingJComponents extends JFrame{
    // 1.JButton about panels
    private static final String[] buttonDemosClassNames = {
            "cn.argentoaskia.swing.widgets.demos.ButtonDemo",
            "cn.argentoaskia.swing.widgets.demos.ButtonHtmlDemo"
    };
    // 所有Button Panel总数
    private static final int buttonAboutPanelCount = buttonDemosClassNames.length >= 5? buttonDemosClassNames.length + 1 : 5;
    // 剩余展示按钮的Panel数量
    private static final int buttonsOnlyPanelCount = buttonDemosClassNames.length >= 5? 1 : 5 - buttonDemosClassNames.length;
    // 剩余Panel展示的按钮个数
    private static final int perPanelButtonCount = 5;
    private JPanel[] buttonAboutPanels;
    private JButton[] jButtons;
    private JPanel initJButtonsPanel() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JPanel buttonTabPanel = new JPanel();
        buttonTabPanel.setLayout(new BorderLayout());

        final Box verticalBox = Box.createVerticalBox();

        buttonAboutPanels = new JPanel[buttonAboutPanelCount];
        jButtons = new JButton[buttonsOnlyPanelCount * perPanelButtonCount];
        for (int j = 0; j < jButtons.length; j++) {
            jButtons[j] = new JButton("Button" + j);
        }

        for (int j = 0; j < buttonDemosClassNames.length; j++) {
            final Class<?> buttonDemosClass = Class.forName(buttonDemosClassNames[j]);
            final JPanel buttonDemo = (JPanel) buttonDemosClass.newInstance();
            buttonAboutPanels[j] = buttonDemo;
            verticalBox.add(buttonAboutPanels[j]);
            // TODO: 2024/2/10 添加间距
        }

        for (int i = 0; i < buttonsOnlyPanelCount; i++) {
            buttonAboutPanels[i + buttonDemosClassNames.length] = new JPanel();
            buttonAboutPanels[i + buttonDemosClassNames.length].setOpaque(true);
            buttonAboutPanels[i + buttonDemosClassNames.length].setBackground(Color.DARK_GRAY);
            for (int j = 0; j < perPanelButtonCount; j++) {
                // perPanelButtonCount = 5
                // i = 0,1,2 ...
                // j = 0,1,2,3,4,
                buttonAboutPanels[i + buttonDemosClassNames.length].add(jButtons[perPanelButtonCount * i + j]);
                // TODO: 2024/2/10 修改间距

            }
            verticalBox.add(buttonAboutPanels[i + buttonDemosClassNames.length]);
        }


        buttonTabPanel.add(verticalBox);

        return buttonTabPanel;
    }



    // 2.JCheckBox Panel
    private static final String[] checkBoxDemosClassName = {

    };
    private static final int checkBoxAboutPanelCount = checkBoxDemosClassName.length >= 5?checkBoxDemosClassName.length + 1: 5;




    // select Panel
    private JTabbedPane selectTabbedPanel;


    // main init Frame
    private void init(){

    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        final SwingJComponents swingJComponents = new SwingJComponents();
        swingJComponents.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final JPanel init = swingJComponents.initJButtonsPanel();
        init.setOpaque(true);
        swingJComponents.setContentPane(init);
        swingJComponents.pack();
        swingJComponents.setVisible(true);
    }


}
