package cn.argento.askia.awt.awtsets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class AwtSets extends Frame {

    private Panel componentPanel;
    private CardLayout cardLayoutForComponentPanel;
    private Panel controlPanel;
    private FlowLayout flowLayoutForControlPanel;
    private BorderLayout borderLayoutForWindow;

    // 控制端组件
    private Button previous;
    private Button next;
    private Button first;
    private Button last;
    private Choice componentsPanelChoice;
    private Button jump;


    // 组件端组件
    private ButtonGroup buttonGroup;
    private LabelGroup labelGroup;
    private RadioGroup radioGroup;
    private CheckBoxGroup checkboxGroup;


    // resource
    private List<String> componentNames;

    public AwtSets(){
        initConfig();
        initComponents();
        initComponentsListeners();
        initWindow();
    }
    // 初始化窗口配置
    private void initConfig() {
        componentNames = new ArrayList<>();
        componentNames.add("按钮组件");
        componentNames.add("标签组件");
        componentNames.add("单选框组件");
        componentNames.add("复选框组件");
        componentNames.add("下拉列表组件");
        componentNames.add("列表组件");
        componentNames.add("文本框组件");
        componentNames.add("文本域组件");
        componentNames.add("滑块条组件");
        componentNames.add("画板组件");
    }
    // 初始化组件
    private void initComponents(){
        // 初始化Panel
        cardLayoutForComponentPanel = new CardLayout();
        componentPanel = new Panel(cardLayoutForComponentPanel);
        flowLayoutForControlPanel = new FlowLayout(FlowLayout.CENTER, 10, 10);
        controlPanel = new Panel(flowLayoutForControlPanel);
        previous = new Button("前一个组件组");
        next = new Button("后一个组件组");
        first = new Button("第一个组件组");
        last = new Button("最后一个组件组");
        jump = new Button("跳转到...");
        componentsPanelChoice = new Choice();
        for (String componentName: componentNames) {
            componentsPanelChoice.add(componentName);
        }
        controlPanel.add(previous);
        controlPanel.add(next);
        controlPanel.add(first);
        controlPanel.add(last);
        controlPanel.add(componentsPanelChoice);
        controlPanel.add(jump);

        // 组件段初始化
        buttonGroup = new ButtonGroup();
        buttonGroup.setup(componentPanel, "按钮组件");
        labelGroup = new LabelGroup();
        labelGroup.setup(componentPanel, "标签组件");
        radioGroup = new RadioGroup();
        radioGroup.setup(componentPanel, "单选框组件");
        checkboxGroup = new CheckBoxGroup();
        checkboxGroup.setup(componentPanel, "复选框组件");
    }
    // 初始化组件事件
    private void initComponentsListeners(){
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutForComponentPanel.previous(componentPanel);
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutForComponentPanel.next(componentPanel);
            }
        });
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutForComponentPanel.first(componentPanel);
            }
        });
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutForComponentPanel.last(componentPanel);
            }
        });
        jump.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutForComponentPanel.show(componentPanel, componentsPanelChoice.getSelectedItem());
            }
        });
    }
    // 初始化窗口
    private void initWindow(){
        initWindowRect();
        initWindowListeners();
        initWindowLayout();
        // 设置可视
        setVisible(true);
    }
    // ======  第二部分拆分 ======
    // 初始化窗口矩形
    private void initWindowRect(){
        // 设置标题
        setTitle("AwtSets");
        // 设置窗口大小和屏幕中出现的位置
        setBounds(0, 0, 800, 400);
    }
    // 初始化窗口监听器
    private void initWindowListeners(){
        // 添加关闭按钮事件监听
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
    // 初始化窗口Layout和组件布局
    private void initWindowLayout(){
       borderLayoutForWindow = new BorderLayout();
       setLayout(borderLayoutForWindow);
       add(componentPanel, BorderLayout.CENTER);
       add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new AwtSets();
    }
}


class ButtonGroup {
    private Panel rootPanel;
    private LayoutManager layoutManager;
    private Button[] buttons;
    private int buttonCount;
    public ButtonGroup(){
        buttonCount = 20;
        layoutManager = new FlowLayout(FlowLayout.LEFT, 10, 10);
        rootPanel = new Panel(layoutManager);
        buttons = new Button[buttonCount];
        for (int i = 0; i < buttonCount; i++) {
            buttons[i] = new Button("按钮" + i);
            rootPanel.add(buttons[i]);
        }
        initButtonsAttr();
        initButtonsEvent();
    }

    private void initButtonsEvent() {
        buttons[0].addActionListener(e -> JOptionPane.showMessageDialog(null, "您触发了按钮事件..., 按钮的触发就这么简单"));
    }

    private void initButtonsAttr() {
        buttons[0].setLabel("点击此按钮将会触发按钮事件");
    }

    public void setup(Panel parent, String registerName){
        parent.add(registerName, rootPanel);
    }
}

class LabelGroup {
    private Panel rootPanel;
    private LayoutManager layoutManager;
    private Label[] labels;
    private int labelCount;
    public LabelGroup(){
        labelCount = 20;
        layoutManager = new FlowLayout(FlowLayout.LEFT, 10, 10);
        rootPanel = new Panel(layoutManager);
        labels = new Label[labelCount];
        for (int i = 0; i < labelCount; i++) {
            labels[i] = new Label("标签" + i);
            rootPanel.add(labels[i]);
        }
    }
    public LabelGroup(int labelCount, String labelLabelPrefix){
        this.labelCount = labelCount;
        layoutManager = new FlowLayout(FlowLayout.LEFT, 10, 10);
        rootPanel = new Panel(layoutManager);
        labels = new Label[labelCount];
        for (int i = 0; i < labelCount; i++) {
            labels[i] = new Label(labelLabelPrefix + i);
            rootPanel.add(labels[i]);
        }
    }
    public LabelGroup(int labelCount, String labelLabelPrefix, LayoutManager layoutManager){
        this.labelCount = labelCount;
        this.layoutManager = layoutManager;
        rootPanel = new Panel(layoutManager);
        labels = new Label[labelCount];
        for (int i = 0; i < labelCount; i++) {
            labels[i] = new Label(labelLabelPrefix + i);
            rootPanel.add(labels[i]);
        }
    }

    public void setup(Panel parent, String registerName){
        parent.add(registerName, rootPanel);
    }
}


class RadioGroup {
    private Panel rootPanel;
    private LayoutManager layoutManager;
    private CheckboxGroup checkboxGroup;
    private Checkbox[] radioButtons;
    private int radioCount;

    public RadioGroup(){
        this(20, "单选框");
    }

    public RadioGroup(int radioCount, String radioLabelPrefix){
        this(radioCount, radioLabelPrefix, new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    public RadioGroup(int radioCount, String radioLabelPrefix, LayoutManager layoutManager){
        this.radioCount = radioCount;
        this.layoutManager = layoutManager;
        rootPanel = new Panel(layoutManager);
        radioButtons = new Checkbox[radioCount];
        checkboxGroup = new CheckboxGroup();
        boolean status = true;
        for (int i = 0; i < radioCount; i++) {
            radioButtons[i] = new Checkbox(radioLabelPrefix + i, checkboxGroup, status);
            status = false;
            rootPanel.add(radioButtons[i]);
        }
    }

    public void  setup(Panel parent, String registerName){
        parent.add(registerName, rootPanel);
    }
}

class CheckBoxGroup {
    private Panel rootPanel;
    private LayoutManager layoutManager;
    private Checkbox[] checkboxes;
    private int checkboxesCount;

    public CheckBoxGroup(){
        this(20, "复选框");
    }

    public CheckBoxGroup(int checkboxesCount, String radioLabelPrefix){
        this(checkboxesCount, radioLabelPrefix, new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    public CheckBoxGroup(int checkboxesCount, String radioLabelPrefix, LayoutManager layoutManager){
        this.checkboxesCount = checkboxesCount;
        this.layoutManager = layoutManager;
        rootPanel = new Panel(layoutManager);
        checkboxes = new Checkbox[checkboxesCount];
        for (int i = 0; i < checkboxesCount; i++) {
            checkboxes[i] = new Checkbox(radioLabelPrefix + i, Math.random() > 0.5);
            rootPanel.add(checkboxes[i]);
        }
    }

    public void  setup(Panel parent, String registerName){
        parent.add(registerName, rootPanel);
    }
}


class ChoiceGroup {
    private Panel rootPanel;
    private LayoutManager layoutManager;
    private Choice[] choices;
    private int choiceCount;

    public ChoiceGroup(){
        this(20, "单选框");
    }

    public ChoiceGroup(int choiceCount, String radioLabelPrefix){
        this(choiceCount, radioLabelPrefix, new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    public ChoiceGroup(int choiceCount, String radioLabelPrefix, LayoutManager layoutManager){
        this.choiceCount = choiceCount;
        this.layoutManager = layoutManager;
        rootPanel = new Panel(layoutManager);
        choices = new Choice[choiceCount];
        for (int i = 0; i < choiceCount; i++) {
            choices[i] = new Choice();
        }
    }


    public void  setup(Panel parent, String registerName){
        parent.add(registerName, rootPanel);
    }
}
