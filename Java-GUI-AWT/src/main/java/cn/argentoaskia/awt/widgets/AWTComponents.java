package cn.argentoaskia.awt.widgets;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * AWT全组件Demo演示.
 * This example show all client components、container components、 menu components、 Dialog Components for AWT
 * include:
 *
 * @author Askia
 * @version 1.1
 * @since 1.0
 */
public class AWTComponents extends Frame {
    // button panel
    private Panel buttonsPanel;
    // single checkbox panel
    private Panel checkBoxPanel;
    // Mutilation checkbox panel
    private Panel checkBoxPanel2;
    // choice panel
    private Panel choicesPanel;
    // labels panel
    private Panel labelsPanel;
    // list panel
    private Panel listPanel;
    // scrollbar panel
    private Panel scrollbarsPanel;

    // textArea panel
    private Panel textAreaPanel;

    // textField panel
    private Panel textFieldPanel;

    private Panel animationPanel;
    // 第一步，创建所有组件
    private void createPanels(){
        animationPanel = new Panel();
        buttonsPanel = new Panel();
        checkBoxPanel = new Panel();
        checkBoxPanel2 = new Panel();
        choicesPanel = new Panel();
        labelsPanel = new Panel();
        listPanel = new Panel();
        scrollbarsPanel = new Panel();
        textAreaPanel = new Panel();
        textFieldPanel = new Panel();
    }
    // 第二步，初始化所有面板
    private void initPanel(){
        createPanels();
        buttonsPanel.setLayout(new GridLayout(5, 5, 5, 5));
        checkBoxPanel.setLayout(new GridLayout(5, 5, 5, 5));
        checkBoxPanel2.setLayout(new GridLayout(5, 5, 5, 5));
        choicesPanel.setLayout(new GridLayout(5, 1,5 ,5));
        labelsPanel.setLayout(new GridLayout(5, 5, 5, 5));
        listPanel.setLayout(new BorderLayout(5,8));
        scrollbarsPanel.setLayout(new BoxLayout(scrollbarsPanel, BoxLayout.Y_AXIS));
        textAreaPanel.setLayout(new BorderLayout());
        textFieldPanel.setLayout(new GridLayout(6, 1, 5, 5));
        animationPanel.setLayout(new BorderLayout());
        animationPanel.setBackground(Color.cyan);
    }
    // 第三步，布局所有组件
    private void layoutPanel(){
        initPanel();
        buttonsPanel.setPreferredSize(new Dimension(300, 200));
        checkBoxPanel.setPreferredSize(new Dimension(300, 200));
        checkBoxPanel2.setPreferredSize(new Dimension(300, 200));
        choicesPanel.setPreferredSize(new Dimension(300, 200));
        labelsPanel.setPreferredSize(new Dimension(300, 200));
        listPanel.setPreferredSize(new Dimension(300, 200));
        scrollbarsPanel.setPreferredSize(new Dimension(300, 200));
        textAreaPanel.setPreferredSize(new Dimension(300, 200));
        textFieldPanel.setPreferredSize(new Dimension(300, 200));
    }

    // 9大组件库：button、canvas、checkbox、choice、label、list、scrollbar、textarea、textfield
    private Random random = new Random();
    // 按钮
    private Button[] buttons;
    // canvas绘图
    private Canvas canvas;
    private Timer timer;
    // 单选框
    private CheckboxGroup checkboxGroup;
    private Checkbox[] checkbox;
    // 复选框
    private Checkbox[] checkboxes;
    // 下拉列表框
    private Choice[] choices;
    // 标题
    private Label[] labels;
    // 列表框
    private List[] lists;
    // 滑块条
    private Scrollbar[] scrollbars;
    // 文本域
    private TextArea textArea;
    // 文本框
    private TextField[] textFields;

    // 第四步 创建用户组件库
    private void initButtons(){
        buttons = new Button[25];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button("按钮" + i);
            buttons[i].setPreferredSize(new Dimension(50, 30));
        }
    }
    private void initSingleCheckBoxs(){
        checkboxGroup = new CheckboxGroup();
        checkbox = new Checkbox[25];
        for (int i = 0; i < checkbox.length; i++) {
            checkbox[i] = new Checkbox("单选框" + i);
            checkbox[i].setCheckboxGroup(checkboxGroup);
        }
        checkboxGroup.setSelectedCheckbox(checkbox[random.nextInt(25)]);

    }
    private void initMultiCheckBoxs(){
        checkboxes = new Checkbox[25];
        for (int i = 0; i < checkbox.length; i++) {
            checkboxes[i] = new Checkbox("复选框" + i, random.nextBoolean());
        }
    }

    private void initChoices(){
        choices = new Choice[3 + 1 + 2 + 1 + 1];
        for (int i = 0; i < choices.length; i++) {
            choices[i] =  new Choice();
        }
        for (int i = 1900; i <= 2023; i++) {
            choices[0].add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            choices[1].add(String.valueOf(i));
        }
        for (int i = 0; i < 31; i++) {
            choices[2].add(String.valueOf(i));
        }
        String[] questions = {
                "您目前的姓名是？","您的学号（或工号）是？","您母亲的生日是？","您高中班主任的名字是？",
                "您配偶的生日是？","您父亲的姓名是？","您小学班主任的名字是？","您父亲的生日是？",
                "您配偶的姓名是？","您初中班主任的名字是？","您最熟悉的童年好友名字是？","您最熟悉的学校宿舍舍友名字是？",
                "对您影响最大的人名字是？"
        };
        for (String question :
                questions) {
            choices[3].add(question);
        }
        for (int i = 0; i < 100; i++) {
            choices[4].add(String.valueOf(random.nextDouble()));
        }
        for (int i = 0; i < 50; i++) {
            choices[5].add(String.valueOf(random.nextInt()));
        }
        for (int i = 0; i < 200; i++) {
            choices[6].add(UUID.randomUUID().toString());
        }
        for (int i = 0; i < 50; i++) {
            choices[7].add(String.valueOf(random.nextBoolean()));
        }
    }
    private void initLabels(){
        labels = new Label[25];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label();
            labels[i].setText("标签" + i);
            labels[i].setPreferredSize(new Dimension(90, 30));
        }
    }
    private void initList(){
        lists = new List[5];
        lists[0] = new List();
        lists[0].add("单选0");
        lists[0].add("单选1");
        lists[0].add("单选2");
        lists[0].add("单选3");
        lists[0].add("单选4");
        lists[0].add("单选5");
        lists[0].add("单选6");
        lists[0].add("单选7");
        lists[0].add("单选8");
        lists[0].add("单选9");
        lists[0].add("单选10");
        lists[0].add("单选11");
        lists[0].add("单选12");
        lists[0].add("单选13");
        lists[0].add("单选14");
        lists[0].setMultipleMode(false);
        lists[4] = new List();
        lists[4].add("多选0");
        lists[4].add("多选1");
        lists[4].add("多选2");
        lists[4].add("多选3");
        lists[4].add("多选4");
        lists[4].add("多选5");
        lists[4].add("多选6");
        lists[4].add("多选7");
        lists[4].add("多选8");
        lists[4].add("多选9");
        lists[4].add("多选10");
        lists[4].add("多选11");
        lists[4].add("多选12");
        lists[4].add("多选13");
        lists[4].add("多选14");
        lists[4].setMultipleMode(true);
        for (int i = 1; i < 4; i++) {
            lists[i] = new List();
            lists[i].add("选项0");
            lists[i].add("选项1");
            lists[i].add("选项2");
            lists[i].add("选项3");
            lists[i].add("选项4");
            lists[i].add("选项5");
            lists[i].add("选项6");
            lists[i].add("选项7");
            lists[i].add("选项8");
            lists[i].add("选项9");
            lists[i].add("选项10");
            lists[i].add("选项11");
            lists[i].add("选项12");
            lists[i].add("选项13");
            lists[i].add("选项14");
            lists[i].add("选项15");
            lists[i].setMultipleMode(random.nextBoolean());
        }
    }
    private void initScrollbars(){
        scrollbars = new Scrollbar[9];
        for (int i = 0; i < scrollbars.length; i++) {
            scrollbars[i] = new Scrollbar();
            scrollbars[i].setMaximum(100);
            scrollbars[i].setMinimum(0);
            scrollbars[i].setUnitIncrement(1);
            // 设置滑块大小数值
            scrollbars[i].setVisibleAmount(1);
            scrollbars[i].addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    System.out.println(e.getValue());
                }
            });
            if (i < 4){
                scrollbars[i].setOrientation(Scrollbar.HORIZONTAL);
                scrollbars[i].setPreferredSize(new Dimension(300, 20));
            }else{
                scrollbars[i].setOrientation(Scrollbar.VERTICAL);
                scrollbars[i].setPreferredSize(new Dimension(20, 120));
            }
        }
    }
    private void initTextArea(){
        textArea = new TextArea();
        textArea.setColumns(20);
        textArea.setPreferredSize(new Dimension(300, 300));
        textArea.setText("我是文本！我是文本！我是文本！我是文本！\n我是文本！我是文本！我是文本！我是文本！\n我是文本！我是文本！我是文本！我是文本！");
    }
    private void initTextField(){
        textFields = new TextField[6];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new TextField();
            textFields[i].setColumns(30);
            textFields[i].setText(UUID.randomUUID().toString());
        }
        // 设置显示字符
        textFields[2].setEchoChar('*');
        textFields[3].setEditable(false);
        Font font = new Font(null, Font.BOLD, 24);
        textFields[4].setFont(font);

    }

    private Image[] birdImage;
    private Image currentImage;
    private int index;
    private int x = 0;
    private Class<?> AWTAnimationPanel2Class;
    private void initCanvas(){
        try {
            AWTAnimationPanel2Class = Class.forName("com.sun.deploy.uitoolkit.impl.awt.AWTAnimationPanel2");
            canvas = (Canvas) AWTAnimationPanel2Class.newInstance();
            Method startAnimationMethod = AWTAnimationPanel2Class.getMethod("startAnimation");
            startAnimationMethod.invoke(canvas);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        birdImage = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            URL resource = AWTComponents.class.getResource("/images/" + i + ".png");
            try {
                birdImage[i] = ImageIO.read(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentImage = birdImage[0];
        index = 0;
        //  bird flying
        canvas = new Canvas(){
            @Override
            public void paint(Graphics g) {
                if (x > getWidth()){
                    x = 0;
                }
                g.drawImage(currentImage,x , 50, null);
                x = x +10;
            }
        };
    }

    // 第五步 初始化组件
    private void initComponents(){
        initCanvas();
        initButtons();
        initChoices();
        initSingleCheckBoxs();
        initMultiCheckBoxs();
        initLabels();
        initList();
        initScrollbars();
        initTextArea();
        initTextField();
    }

    // 第六步 布局组件
    private void layoutComponents(){
        initComponents();
        for (Button button : buttons) {
            buttonsPanel.add(button);
        }
        for (Checkbox value : checkbox) {
            checkBoxPanel.add(value);
        }
        for (Checkbox value: checkboxes) {
            checkBoxPanel2.add(value);
        }
        // add choices
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(choices[0]);
        horizontalBox.add(Box.createHorizontalStrut(10));
        horizontalBox.add(choices[1]);
        horizontalBox.add(Box.createHorizontalStrut(10));
        horizontalBox.add(choices[2]);

        Box horizontalBox1 = Box.createHorizontalBox();
        horizontalBox1.add(choices[4]);
        horizontalBox1.add(Box.createHorizontalStrut(10));
        horizontalBox1.add(choices[5]);
        choicesPanel.add(horizontalBox);
        choicesPanel.add(choices[3]);
        choicesPanel.add(horizontalBox1);
        choicesPanel.add(choices[6]);
        choicesPanel.add(choices[7]);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(scrollbars[0]);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(scrollbars[1]);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(scrollbars[2]);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(scrollbars[3]);
        scrollbarsPanel.add(verticalBox);

        Box horizontalBox2 = Box.createHorizontalBox();
        horizontalBox2.add(scrollbars[4]);
        horizontalBox2.add(Box.createHorizontalStrut(10));
        horizontalBox2.add(scrollbars[5]);
        horizontalBox2.add(Box.createHorizontalStrut(10));
        horizontalBox2.add(scrollbars[6]);
        horizontalBox2.add(Box.createHorizontalStrut(10));
        horizontalBox2.add(scrollbars[7]);
        horizontalBox2.add(Box.createHorizontalStrut(10));
        horizontalBox2.add(scrollbars[8]);
        scrollbarsPanel.add(horizontalBox2, BorderLayout.SOUTH);

        for (TextField textField : textFields) {
            textFieldPanel.add(textField);
        }
        listPanel.add(lists[0], BorderLayout.NORTH);
        listPanel.add(lists[1]);
        listPanel.add(lists[2],BorderLayout.EAST);
        listPanel.add(lists[3],BorderLayout.WEST);
        listPanel.add(lists[4],BorderLayout.SOUTH);
        for (Label label : labels) {
            labelsPanel.add(label);
        }
        textAreaPanel.add(textArea);

        animationPanel.add(canvas);
    }

    // 第七步 窗口设置
    private void initWindows(){
        setMinimumSize(new Dimension(950, 900));
        setMaximumSize(new Dimension(1500, 1400));
        setBounds(100, 100 ,1100, 900);
        // 网格布局
        GridLayout gridLayout = new GridLayout();
        // 4行3列
        gridLayout.setColumns(1);
        gridLayout.setRows(4);
        gridLayout.setVgap(20);
        gridLayout.setHgap(20);
        setLayout(gridLayout);
        Box horizontalBox1 = Box.createHorizontalBox();
        Box horizontalBox2 = Box.createHorizontalBox();
        Box horizontalBox3 = Box.createHorizontalBox();

        // 添加按钮组
        Panel buttonMainPanel = new Panel();
        buttonMainPanel.setLayout(new BorderLayout());
        buttonMainPanel.add(buttonsPanel, BorderLayout.CENTER);
        Label buttonGroupMsg = new Label("按钮组件", Label.CENTER);
        Font font = new Font(null, Font.BOLD, 20);
        buttonGroupMsg.setFont(font);
        buttonMainPanel.add(buttonGroupMsg, BorderLayout.SOUTH);

        Panel checkBoxMainPanel = new Panel();
        checkBoxMainPanel.setLayout(new BorderLayout());
        checkBoxMainPanel.add(checkBoxPanel, BorderLayout.CENTER);
        Label checkBoxGroupMsg = new Label("单选框组件", Label.CENTER);
        checkBoxGroupMsg.setFont(font);
        checkBoxMainPanel.add(checkBoxGroupMsg, BorderLayout.SOUTH);

        Panel multiCheckBoxMainPanel = new Panel();
        multiCheckBoxMainPanel.setLayout(new BorderLayout());
        multiCheckBoxMainPanel.add(checkBoxPanel2, BorderLayout.CENTER);
        Label multiCheckBoxGroupMsg = new Label("复选框组件", Label.CENTER);
        multiCheckBoxGroupMsg.setFont(font);
        multiCheckBoxMainPanel.add(multiCheckBoxGroupMsg, BorderLayout.SOUTH);


        // group
        horizontalBox1.add(buttonMainPanel);
        horizontalBox1.add(Box.createHorizontalStrut(20));
        horizontalBox1.add(checkBoxMainPanel);
        horizontalBox1.add(Box.createHorizontalStrut(20));
        horizontalBox1.add(multiCheckBoxMainPanel);
        add(horizontalBox1);

        Panel choiceMainPanel = new Panel();
        choiceMainPanel.setLayout(new BorderLayout());
        choiceMainPanel.add(choicesPanel, BorderLayout.CENTER);
        Label choiceGroupMsg = new Label("下拉列表组件", Label.CENTER);
        choiceGroupMsg.setFont(font);
        choiceMainPanel.add(choiceGroupMsg, BorderLayout.SOUTH);

        Panel labelMainPanel = new Panel();
        labelMainPanel.setLayout(new BorderLayout());
        labelMainPanel.add(labelsPanel, BorderLayout.CENTER);
        Label labelGroupMsg = new Label("标签组件", Label.CENTER);
        labelGroupMsg.setFont(font);
        labelMainPanel.add(labelGroupMsg, BorderLayout.SOUTH);

        Panel listMainPanel = new Panel();
        listMainPanel.setLayout(new BorderLayout());
        listMainPanel.add(listPanel, BorderLayout.CENTER);
        Label listGroupMsg = new Label("列表组件", Label.CENTER);
        listGroupMsg.setFont(font);
        listMainPanel.add(listGroupMsg, BorderLayout.SOUTH);

        horizontalBox2.add(choiceMainPanel);
        horizontalBox2.add(Box.createHorizontalStrut(20));
        horizontalBox2.add(labelMainPanel);
        horizontalBox2.add(Box.createHorizontalStrut(20));
        horizontalBox2.add(listMainPanel);
        add(horizontalBox2);

        Panel scrollbarsMainPanel = new Panel();
        scrollbarsMainPanel.setLayout(new BorderLayout());
        scrollbarsMainPanel.add(scrollbarsPanel, BorderLayout.CENTER);
        Label scrollbarsGroupMsg = new Label("滑块条组件", Label.CENTER);
        scrollbarsGroupMsg.setFont(font);
        scrollbarsMainPanel.add(scrollbarsGroupMsg, BorderLayout.SOUTH);

        Panel textAreaMainPanel = new Panel();
        textAreaMainPanel.setLayout(new BorderLayout());
        textAreaMainPanel.add(textAreaPanel, BorderLayout.CENTER);
        Label textAreaGroupMsg = new Label("文本域组件", Label.CENTER);
        textAreaGroupMsg.setFont(font);
        textAreaMainPanel.add(textAreaGroupMsg, BorderLayout.SOUTH);

        Panel textFieldMainPanel = new Panel();
        textFieldMainPanel.setLayout(new BorderLayout());
        textFieldMainPanel.add(textFieldPanel, BorderLayout.CENTER);
        Label textFieldGroupMsg = new Label("列表组件", Label.CENTER);
        textFieldGroupMsg.setFont(font);
        textFieldMainPanel.add(textFieldGroupMsg, BorderLayout.SOUTH);

        horizontalBox3.add(scrollbarsPanel);
        horizontalBox3.add(Box.createHorizontalStrut(20));
        horizontalBox3.add(textAreaPanel);
        horizontalBox3.add(Box.createHorizontalStrut(20));
        horizontalBox3.add(textFieldPanel);
        add(horizontalBox3);

        add(animationPanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopAnimation();
                System.exit(0);
            }
        });
        setTitle("AWT组件一览, 共有9种组件, 一共9种组件：按钮、画布、选择框、下拉列表框、标签、列表框、文本编辑框、文本编辑域、滑块条");
        setVisible(true);
    }

    private MenuBar menuBar;
    // 主菜单
    // 组件设置菜单
    private Menu componentsMenu;
    // 显示对话框的Menu
    private Menu dialogsMenu;
    // 操作菜单的Menu
    private Menu menuOpsMenu;

    // 组件添加事件
    private Menu buttonListenersMenu;
    private Menu singleCheckboxListenersMenu;
    private Menu multiCheckboxListenersMenu;
    private Menu choiceListenersMenu;
    private Menu labelListenersMenu;
    private Menu listListenerMenu;
    private Menu scrollbarListenersMenu;
    private Menu textAreaListenersMenu;
    private Menu textFieldListenersMenu;

    // buttons listener effects
    private Menu[] buttonsEffects;
    private CheckboxMenuItem[][] buttonCheckboxMenuItems;

    // singleCheckbox listener effects
    private MenuItem currentSelected;
    private Menu[] singleCheckboxEffect;
    private CheckboxMenuItem[][] singleCheckboxCheckboxMenuItems;

    // multiCheckbox Listener elffects
    private MenuItem currentSelecteds;
    private Menu[] MultiCheckboxEffect;
    private CheckboxMenuItem[][] multiCheckboxCheckboxMenuItems;

    // more


    // dialog
    private Menu simpleDialog;
    private MenuItem  showMessageBox;
    private MenuItem  showInputBox;
    private Dialog messageDialog;
    private Dialog inputDialog;

    private Menu fileDialog;
    private MenuItem showOpenFileMenuItem;
    private MenuItem showSaveFileMenuItem;

    public void initMenu(){
        //  TODO: 2023/6/17  windows 10 21H系统测试：setShortcut()设置快捷键无法生效！
        menuBar = new MenuBar();
        componentsMenu = new Menu("组件设置菜单(L)", true);
        componentsMenu.setShortcut(new MenuShortcut(KeyEvent.VK_L, true));

        buttonListenersMenu = new Menu("按钮设置");
        buttonListenersMenu.setShortcut(new MenuShortcut(KeyEvent.VK_B));
        singleCheckboxListenersMenu = new Menu("单选框设置");
        multiCheckboxListenersMenu = new Menu("复选框设置");
        choiceListenersMenu = new Menu("下拉列表设置");
        labelListenersMenu = new Menu("标签设置");
        listListenerMenu = new Menu("下拉列表设置");
        scrollbarListenersMenu = new Menu("滑块条设置");
        textAreaListenersMenu = new Menu("文本域设置");
        textFieldListenersMenu = new Menu("文本框设置");
        componentsMenu.add(buttonListenersMenu);
        componentsMenu.add(singleCheckboxListenersMenu);
        componentsMenu.add(multiCheckboxListenersMenu);
        componentsMenu.add(choiceListenersMenu);
        componentsMenu.add(labelListenersMenu);
        componentsMenu.add(listListenerMenu);
        componentsMenu.add(scrollbarListenersMenu);
        componentsMenu.add(textAreaListenersMenu);
        componentsMenu.add(textFieldListenersMenu);
        setButtonListeners();



        dialogsMenu = new Menu();
        dialogsMenu.setLabel("对话框菜单(D)");
        dialogsMenu.setShortcut(new MenuShortcut(KeyEvent.VK_D + KeyEvent.VK_ALT));

        simpleDialog = new Menu("普通对话框");
        showMessageBox = new MenuItem("信息框...");
        showInputBox = new MenuItem("输入框...");
        simpleDialog.add(showMessageBox);
        simpleDialog.add(showInputBox);



        fileDialog = new Menu("文件对话框");
        showOpenFileMenuItem = new MenuItem("打开文件...",new MenuShortcut(KeyEvent.VK_O,true));
        showSaveFileMenuItem = new MenuItem("保存文件...",new MenuShortcut(KeyEvent.VK_S,true));
        fileDialog.add(showOpenFileMenuItem);
        fileDialog.add(showSaveFileMenuItem);


        dialogsMenu.add(simpleDialog);
        dialogsMenu.add(fileDialog);


        menuBar.add(componentsMenu);
        menuBar.add(dialogsMenu);

        setMenuBar(menuBar);
    }

    private void setButtonListeners() {
        buttonsEffects = new Menu[3];
        buttonCheckboxMenuItems = new CheckboxMenuItem[3][25];
        for (int i = 0; i < buttonsEffects.length; i++) {
             buttonsEffects[i] = new Menu("效果" + i);
             CheckboxMenuItem[] checkboxMenuItems = new CheckboxMenuItem[25];
             buttonCheckboxMenuItems[i] = checkboxMenuItems;
             for (int j = 0; j < checkboxMenuItems.length; j++) {
                 checkboxMenuItems[j] = new CheckboxMenuItem("按钮" + j, random.nextBoolean());
                 buttonsEffects[i].add(checkboxMenuItems[j]);
             }

             buttonListenersMenu.add(buttonsEffects[i]);
        }
    }


    public AWTComponents(){
        layoutPanel();
        layoutComponents();
        initMenu();
        initWindows();
        runAnimation();

    }

    // 运行动画
    private void runAnimation(){
        // 定时器，让Canvas动画动起来
        timer = new Timer();
        if (AWTAnimationPanel2Class != null && AWTAnimationPanel2Class.isInstance(canvas)){
            MyCanvasController myCanvasController = new MyCanvasController(canvas);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    myCanvasController.run();
                }
            };
            int run = 300 + random.nextInt(700);
            System.out.println(run);
            timer.schedule(timerTask, 100, run);
        }else{
            BirdCanvasController birdCanvasController = new BirdCanvasController(canvas);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    birdCanvasController.run();
                }
            };
            timer.schedule(timerTask, 100, 100);
        }
    }

    private void stopAnimation(){
        if (AWTAnimationPanel2Class != null && AWTAnimationPanel2Class.isInstance(canvas)){
            try {
                Method stopAnimation = AWTAnimationPanel2Class.getMethod("stopAnimation");
                stopAnimation.invoke(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        timer.cancel();

    }
    // canvas动画控制器
    class MyCanvasController implements Runnable{
        private Canvas awtAnimationPanel2;

        public MyCanvasController(Canvas awtAnimationPanel2){
            this.awtAnimationPanel2 = awtAnimationPanel2;
        }
        @Override
        public void run() {
            try {
                Method getProgressValue = AWTAnimationPanel2Class.getMethod("getProgressValue");
                Method setProgressValue = AWTAnimationPanel2Class.getMethod("setProgressValue", float.class);
                float progressValue = (float) getProgressValue.invoke(awtAnimationPanel2);
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String progressValueStr = decimalFormat.format(progressValue);
                if (Float.valueOf(progressValueStr).equals(1.0f)){
                    setProgressValue.invoke(awtAnimationPanel2, 0.0f);
                } else {
                    progressValue = progressValue + 0.1f;
                    String format = decimalFormat.format(progressValue);
                    Float aFloat = Float.valueOf(format);
                    System.out.println(aFloat);
                    setProgressValue.invoke(awtAnimationPanel2, aFloat);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class BirdCanvasController implements Runnable{
        private Canvas canvas;
        private BirdCanvasController(Canvas canvas){
            this.canvas = canvas;
        }
        @Override
        public void run() {
            index++;
//            int a = index / 10;
            currentImage = birdImage[index % 8];
            canvas.repaint();
        }
    }


    public static void main(String[] args) {
        new AWTComponents();
    }
}
