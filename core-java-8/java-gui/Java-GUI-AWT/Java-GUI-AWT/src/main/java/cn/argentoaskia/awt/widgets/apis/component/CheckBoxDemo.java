package cn.argentoaskia.awt.widgets.apis.component;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// checkBox演示！加上checkboxGroup就是单选框
// 不加就是复选框！
public class CheckBoxDemo extends Frame {
    private Panel checkboxGroup1Panel;
    private CheckboxGroup checkboxGroup1;
    private Checkbox checkbox1;
    private Checkbox checkbox2;
    private Checkbox checkbox3;
    private Checkbox checkbox4;

    private Panel checkboxGroup2Panel;
    private CheckboxGroup checkboxGroup2;
    private Checkbox checkbox5;
    private Checkbox checkbox6;
    private Checkbox checkbox7;

    private Panel checkboxGroup3Panel;
    private Checkbox checkbox8;
    private Checkbox checkbox9;
    private Checkbox checkbox10;
    private Checkbox checkbox0;


    private void init(){
        checkboxGroup1Panel = new Panel();
        checkboxGroup2Panel = new Panel();
        checkboxGroup3Panel = new Panel();
        checkboxGroup1 = new CheckboxGroup();
        checkbox1 = new Checkbox();
        // 设置checkbox属于哪个组
        checkbox1.setCheckboxGroup(checkboxGroup1);
        // 设置checkbox标签
        checkbox1.setLabel("选择框1");
        // 设置选中状态
        checkbox1.setState(true);
        checkbox2 = new Checkbox("选择框2", true, checkboxGroup1);
        checkbox3 = new Checkbox("选择框3", true, checkboxGroup1);
        checkbox4 = new Checkbox("选择框4", false, checkboxGroup1);

        checkboxGroup2 = new CheckboxGroup();
        checkbox5 = new Checkbox("男", true, checkboxGroup2);
        checkbox6 = new Checkbox("女", true, checkboxGroup2);
        checkbox7 = new Checkbox("不明", true, checkboxGroup2);

        checkbox8 = new Checkbox("一根烟", true);
        checkbox9 = new Checkbox("一杯水", true);
        checkbox10 = new Checkbox("一个Bug", true);
        checkbox0 = new Checkbox("改N天", true);

        checkboxGroup1Panel.add(checkbox1);
        checkboxGroup1Panel.add(checkbox2);
        checkboxGroup1Panel.add(checkbox3);
        checkboxGroup1Panel.add(checkbox4);

        checkboxGroup2Panel.add(checkbox5);
        checkboxGroup2Panel.add(checkbox6);
        checkboxGroup2Panel.add(checkbox7);

        checkboxGroup3Panel.add(checkbox8);
        checkboxGroup3Panel.add(checkbox9);
        checkboxGroup3Panel.add(checkbox10);
        checkboxGroup3Panel.add(checkbox0);

        add(checkboxGroup1Panel, BorderLayout.NORTH);
        add(checkboxGroup2Panel, BorderLayout.CENTER);
        add(checkboxGroup3Panel, BorderLayout.SOUTH);
    }

    public CheckBoxDemo(){
        init();
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new CheckBoxDemo();
    }
}
