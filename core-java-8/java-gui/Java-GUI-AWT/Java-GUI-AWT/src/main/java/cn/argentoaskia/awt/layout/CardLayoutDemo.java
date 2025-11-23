package cn.argentoaskia.awt.layout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * 卡式布局演示，包括如何定位卡片，如何动态增减卡片。
 *
 * 修正删除卡片bug
 * @author Askia
 */
public class CardLayoutDemo extends Frame{
    private static int currentCardIndex = 1;

    private Panel panel;
    CardLayout cardLayout;

    private Panel controlPanel;
    Button preBtn;
    Button nextBtn;
    Button firstBtn;
    Button lastBtn;
    Panel choicePanel;
    TextField field;
    Button b5;


    Panel panel1;
    Label label;
    Button button;
    Button button1;


    private Map<Integer, Label> labelMap;
    private Queue<Integer> queue;

    public CardLayoutDemo(){
        setTitle("CardLayout布局演示");
        setSize(1000, 600);
        labelMap = new HashMap<>();
        queue = new LinkedList<>();

        initPanelWidget();
        initDefaultLabel();
        initTopButtons();
        initBottomButtons();
        initEvents();

        add(panel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(panel1, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(800, 600);
        setVisible(true);
    }

    private void initDefaultLabel(){
        Label label1 = new Label("第1张卡片");
        label1.setAlignment(Label.CENTER);
        label1.setBackground(Color.green);
        Label label2 = new Label("第2张卡片");
        label2.setAlignment(Label.CENTER);
        label2.setBackground(Color.WHITE);
        Label label3 = new Label("第3张卡片");
        label3.setAlignment(Label.CENTER);
        label3.setBackground(Color.BLUE);
        labelMap.put(1, label1);
        labelMap.put(2, label2);
        labelMap.put(3, label3);
        panel.add( "1", label1);
        panel.add( "2", label2);
        panel.add( "3", label3);
    }

    private void initPanelWidget() {
        panel = new Panel();
        cardLayout = new CardLayout();
        panel.setLayout(cardLayout);
    }

    private void initTopButtons(){
        // 顶部Panel
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 20, 0);
        controlPanel = new Panel();
        controlPanel.setLayout(flowLayout);
        preBtn = new Button("上一张");
        nextBtn = new Button("下一张");
        firstBtn = new Button("第一张");
        lastBtn = new Button("最后一张");

        choicePanel = new Panel();
        field = new TextField(5);
        b5 = new Button("跳转到指定张");
        choicePanel.add(field);
        choicePanel.add(b5);

        controlPanel.add(preBtn);
        controlPanel.add(nextBtn);
        controlPanel.add(lastBtn);
        controlPanel.add(firstBtn);
        controlPanel.add(choicePanel);
    }


    private void initBottomButtons(){
        // 底部Panel
        panel1 = new Panel();
        FlowLayout flowLayout1 = new FlowLayout(FlowLayout.CENTER, 30, 0);
        label = new Label("当前有" + labelMap.size() + "张卡片");
        button = new Button("添加一张卡片");
        button.setActionCommand("add");
        button1 = new Button("删除当前卡片");
        button1.setActionCommand("delete");
        panel1.setLayout(flowLayout1);
        panel1.add(label);
        panel1.add(button);
        panel1.add(button1);
    }

    private class TopButtonsEvents implements ActionListener {

        private int parseToInt(String s){
            try{
                return Integer.parseInt(s);
            }catch (NumberFormatException e){
                System.out.println("无法将字符" + s + "转为数字！");
                throw e;
            }
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            final String actionCommand = e.getActionCommand();
            if (actionCommand.equalsIgnoreCase("上一张")){
                // 已经是第一张了，再次点击上一张，跳到最后一张
                if(currentCardIndex == 1){
                    currentCardIndex = labelMap.size();
                    // 切换卡片
                }else{
                    currentCardIndex--;
                }
                cardLayout.previous(panel);
            }
            else if(actionCommand.equalsIgnoreCase("下一张")){
                // 已经是最后一张了，再次点击下一张，跳到第一张
                if(currentCardIndex == labelMap.size()){
                    // 切换卡片
                    currentCardIndex = 1;
                }else{
                    currentCardIndex++;
                }
                cardLayout.next(panel);
            }
            else if (actionCommand.equalsIgnoreCase("第一张")){
                cardLayout.first(panel);
                currentCardIndex = 1;
            }
            else if(actionCommand.equalsIgnoreCase("最后一张")){
                cardLayout.last(panel);
                currentCardIndex = labelMap.size();
            }
            else{
                // 获取输入文本
                String text = field.getText();
                final int i = parseToInt(text);
                if (i > labelMap.size()){
                    System.out.println("当前只有" + labelMap.size() + "张卡片！");
                }
                else {
                    cardLayout.show(panel, i + "");
                    currentCardIndex = i;
                }
            }
        }
    }

    private class BottomButtonEvents implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("add")){
                int size = getNextSeq();
                Label label2 = new Label("第"+ size + "张卡片");
                labelMap.put(size, label2);
                label2.setAlignment(Label.CENTER);
                label2.setBackground(Color.getHSBColor((float) Math.random(), (float) Math.random(), (float) Math.random()));
                panel.add(size + "", label2);
                label.setText("当前有" + labelMap.size() + "张卡片");
            }
            else{
                panel.remove(labelMap.get(currentCardIndex));
                recycleSeq();
                label.setText("当前有" + (labelMap.size()) + "张卡片");
            }
            // 更新布局
            cardLayout.invalidateLayout(panel);
//                cardLayout.layoutContainer(panel);
        }
    }

    private void initEvents(){
        TopButtonsEvents topButtonsEvents = new TopButtonsEvents();
        preBtn.addActionListener(topButtonsEvents);
        nextBtn.addActionListener(topButtonsEvents);
        lastBtn.addActionListener(topButtonsEvents);
        firstBtn.addActionListener(topButtonsEvents);
        b5.addActionListener(topButtonsEvents);

        BottomButtonEvents bottomButtonEvents = new BottomButtonEvents();
        button.addActionListener(bottomButtonEvents);
        button1.addActionListener(bottomButtonEvents);
    }




    private Integer getNextSeq(){
        if (queue.size() != 0){
            return queue.poll();
        }
        else{
            return labelMap.size() + 1;
        }
    }

    private void recycleSeq(){
        labelMap.remove(currentCardIndex);
        queue.add(currentCardIndex);
    }


    public static void main(String[] args) {
        new CardLayoutDemo();
    }
}
