package cn.argentoaskia.swing.widgets;

import cn.argentoaskia.swing.widgets.demos.ColorChooserDemo;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.security.PrivateKey;

public class SwingComponents extends JFrame {
    private JTabbedPane tabbedPane;

    private JPanel buttonGroupPanel;
    private JPanel buttonsPanel;
    private JPanel checkBoxesPanel;
    private JPanel radioButtonsPanel;
    private JPanel jToggleButtonPanel;
    private JPanel groupCheckBoxesPanel;
    private JPanel groupRadioButtonsPanel;
    private JPanel groupJToggleButtonPanel;

    // buttons
    private JButton[] buttons;
    private JCheckBox[] checkBoxes;
    private JRadioButton[] radioButtons;
    private JToggleButton[] jToggleButtons;

    // group button
    private ButtonGroup radioButtonGroup;
    private JRadioButton[] groupRadioButtons;
    private ButtonGroup checkBoxesGroup;
    private JCheckBox[] groupCheckBoxes;
    private ButtonGroup jToggleButtonsGroup;
    private JToggleButton[] groupJToggleButtons;


    private JPanel barPanel;
    private JPanel progressBarPanel;
    private JPanel scrollBarPanel;
    private JProgressBar[] progressBar;
    private JScrollBar[] scrollBar;
    private JLabel scrollBarLabel;


    public JPanel initBars(){
        progressBarPanel = new JPanel();
        scrollBarPanel = new JPanel();
        barPanel = new JPanel();

        GridLayout gridLayout = new GridLayout(3, 1);
        progressBarPanel.setLayout(gridLayout);
        scrollBarPanel.setLayout(gridLayout);
        //
        scrollBarLabel = new JLabel("0%");
        progressBar = new JProgressBar[3];
        scrollBar = new JScrollBar[3];
        progressBar[0] = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        progressBar[0].setBorderPainted(true);
        progressBar[0].setStringPainted(true);
        progressBar[0].setValue(60);
        progressBar[1] = new JProgressBar(SwingConstants.HORIZONTAL, 0, 1000);
        progressBar[1].setIndeterminate(true);
        progressBar[1].setIndeterminate(true);
        progressBar[1].setBorderPainted(false);
        progressBar[1].setStringPainted(true);
        progressBar[2] = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        progressBar[2].setIndeterminate(true);
        for (int i = 0; i < progressBar.length; i++) {
            progressBarPanel.add(progressBar[i]);
        }
        //
        scrollBar[0] = new JScrollBar();
        scrollBar[0].setVisibleAmount(5);
        scrollBar[0].setUnitIncrement(2);
        scrollBar[0].setOrientation(JScrollBar.HORIZONTAL);
        scrollBar[0].setMaximum(100);
        scrollBar[0].setMinimum(0);
        scrollBar[0].setValue(50);
        scrollBar[1] = new JScrollBar(JScrollBar.HORIZONTAL, 140, 2, 100, 200);
        scrollBar[2] = new JScrollBar(JScrollBar.HORIZONTAL, 100, 20, 0, 1000);
        scrollBarPanel.add(scrollBar[0]);
        scrollBarPanel.add(scrollBar[1]);
        Box horizontalBox = Box.createHorizontalBox();
        scrollBar[2].setPreferredSize(new Dimension(0, 100));
        scrollBarLabel.setPreferredSize(new Dimension(50, 100));
        horizontalBox.add(scrollBar[2]);
        horizontalBox.add(scrollBarLabel);
        scrollBarPanel.add(horizontalBox);

        // 选择夹主面板
        barPanel = new JPanel();
        GridLayout gridLayout2 = new GridLayout(2, 1);
        barPanel.setLayout(gridLayout2);
        barPanel.add(progressBarPanel);
        barPanel.add(scrollBarPanel);
        return barPanel;
    }

    // 初始化所有按钮和Panel
    public JPanel initButtons(){
        // simple buttons
        buttons = new JButton[10];
        checkBoxes = new JCheckBox[10];
        radioButtons = new JRadioButton[10];
        jToggleButtons = new JToggleButton[10];

        // group button
        groupRadioButtons = new JRadioButton[10];
        groupCheckBoxes = new JCheckBox[10];
        groupJToggleButtons = new JToggleButton[10];

        // panel
        buttonsPanel = new JPanel();
        checkBoxesPanel = new JPanel();
        radioButtonsPanel = new JPanel();
        jToggleButtonPanel = new JPanel();

        // group panel
        groupCheckBoxesPanel = new JPanel();
        groupRadioButtonsPanel = new JPanel();
        groupJToggleButtonPanel = new JPanel();

        // group
        radioButtonGroup = new ButtonGroup();
        checkBoxesGroup = new ButtonGroup();
        jToggleButtonsGroup = new ButtonGroup();
        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton();
            checkBoxes[i] = new JCheckBox();
            radioButtons[i] = new JRadioButton();
            jToggleButtons[i] = new JToggleButton("toggleButton" + i);
            buttons[i].setText("button " + i);
            checkBoxes[i].setText("checkbox " + i);
            radioButtons[i].setText("radioButtons " + i);
            jToggleButtons[i].setSelected(true);
            checkBoxes[i].setSelected(true);
            radioButtons[i].setSelected(true);

            groupRadioButtons[i] = new JRadioButton("radioButtons " + i);
            groupCheckBoxes[i] = new JCheckBox("checkbox " + i);
            groupJToggleButtons[i] = new JToggleButton("toggleButton" + i);

            radioButtonGroup.add(groupRadioButtons[i]);
            checkBoxesGroup.add(groupCheckBoxes[i]);
            jToggleButtonsGroup.add(groupJToggleButtons[i]);

            buttonsPanel.add(buttons[i]);
            checkBoxesPanel.add(checkBoxes[i]);
            radioButtonsPanel.add(radioButtons[i]);
            jToggleButtonPanel.add(jToggleButtons[i]);

            groupCheckBoxesPanel.add(groupCheckBoxes[i]);
            groupRadioButtonsPanel.add(groupRadioButtons[i]);
            groupJToggleButtonPanel.add(groupJToggleButtons[i]);
        }
        buttonGroupPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(7, 1, 5, 5);
        buttonGroupPanel.setLayout(gridLayout);
        buttonGroupPanel.add(buttonsPanel);
        buttonGroupPanel.add(checkBoxesPanel);
        buttonGroupPanel.add(radioButtonsPanel);
        buttonGroupPanel.add(jToggleButtonPanel);

        buttonGroupPanel.add(groupCheckBoxesPanel);
        buttonGroupPanel.add(groupRadioButtonsPanel);
        buttonGroupPanel.add(groupJToggleButtonPanel);

        return buttonGroupPanel;
    }

    // 颜色选择组件演示
    public JPanel colorChooserPanel = new ColorChooserDemo();

    public void initTabbedPane(){
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("按钮&状态按钮&单选框&多选框", initButtons());
        tabbedPane.addTab("Bars", initBars());
        tabbedPane.addTab("ColorChooser", colorChooserPanel);
    }

    public SwingComponents(){
        initTabbedPane();
        add(tabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new SwingComponents();
    }

}
