package cn.argento.askia.awt.core.components;

import cn.argentoaskia.awt.widgets.diy.PageSelector;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

public class AwtComponentsShow extends Frame {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        BitSet bitSet1 = new BitSet();
        bitSet.set(0);
        bitSet1.set(0);
        System.out.println(bitSet.equals(bitSet1));
        final Path resourcePath = Paths.get("Java-GUI-AWT/src/main/resources/radio_checkbox_images");
        final File resourcePathFile = resourcePath.toFile();
        final File[] listFiles = resourcePathFile.listFiles();
        System.out.println(Arrays.toString(listFiles));
        System.out.println(listFiles[0].getName());
        new AwtComponentsShow();
    }

    private PageSelector pageSelector;

    // components
    // 按钮
    private Button[] buttons;

    // 单选框
    private CheckboxGroup[] checkboxGroup;
    private Checkbox[] radioCheckboxes;
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

    // first init Selector
    private void initPageWidget() {
        pageSelector = new PageSelector();
        add(pageSelector, BorderLayout.CENTER);

        // use for debug!
        final Panel controller = PageSelector.useDefaultController(pageSelector);
        add(controller, BorderLayout.SOUTH);
    }

    private static final String BUTTONS_PAGE_NAME = "buttons";
    private static final int BUTTONS_H_GAP = 5;
    private static final int BUTTONS_V_GAP = 10;
    private static final int BUTTONS_COL_NUMBER = 5;
    private static final int BUTTONS_ROW_NUMBER = 5;
    private static final int BUTTONS_NUMBER = BUTTONS_COL_NUMBER * BUTTONS_ROW_NUMBER;
    private static final String BUTTONS_LABEL_PRE = "button";
    private static final String BUTTONS_NAME_PRE = "_button";
    private void initButtonPage() {
        final Panel buttonsPage = pageSelector.newPage(BUTTONS_PAGE_NAME);
        // 5 x 5排列
        buttonsPage.setLayout(new GridLayout(BUTTONS_ROW_NUMBER,
                BUTTONS_COL_NUMBER, BUTTONS_H_GAP, BUTTONS_V_GAP));

        buttons = new Button[BUTTONS_NUMBER];
        for (int i = 0; i < BUTTONS_NUMBER; i++) {
            buttons[i] = new Button();
            buttons[i].setName(BUTTONS_NAME_PRE + i);
            buttons[i].setLabel(BUTTONS_LABEL_PRE + i);
            buttonsPage.add(buttons[i]);
        }
    }

    private static final String RADIO_CHECKBOXES_PAGE_NAME = "radioCheckboxes";
    private static final int GROUPS_NUMBER = 2;
    private static int GROUPS_1_RADIO_NUMBER = 6;
    private static final int GROUPS_2_RADIO_NUMBER = 10;
    private static final String RADIO_CHECKBOXES_LABEL_PRE = "radioBox";
    private static final String RADIO_CHECKBOXES_NAME_PRE = "radioCheckbox";
    private static final String RADIO_CHECKBOXES_IMAGE_RESOURCES_PATH = "Java-GUI-AWT/src/main/resources/radio_checkbox_images";
    private PictureCanvas picture;
    private File[] initPictureRadioCheckboxesResources() {
        final Path resourcePath = Paths.get(RADIO_CHECKBOXES_IMAGE_RESOURCES_PATH);
        final File resourcePathFile = resourcePath.toFile();
        final File[] listFiles = resourcePathFile.listFiles();
        assert listFiles != null;
        GROUPS_1_RADIO_NUMBER = listFiles.length + 1;
        return listFiles;
    }
    private void initRadioCheckboxPage() {
        final Panel radioCheckboxesPage = pageSelector.newPage(RADIO_CHECKBOXES_PAGE_NAME);
        radioCheckboxesPage.setLayout(new BorderLayout(20, 10));

        final File[] resources = initPictureRadioCheckboxesResources();

        Panel pictureControlPanel = new Panel(new GridLayout(GROUPS_1_RADIO_NUMBER, 1));
        pictureControlPanel.setPreferredSize(new Dimension(100,50));
        checkboxGroup = new CheckboxGroup[GROUPS_NUMBER];
        checkboxGroup[0] = new CheckboxGroup();
        checkboxGroup[1] = new CheckboxGroup();
        radioCheckboxes = new Checkbox[GROUPS_1_RADIO_NUMBER + GROUPS_2_RADIO_NUMBER];
        for (int i = 0; i < GROUPS_1_RADIO_NUMBER; i++) {
            String fileNameOnly = "none";
            boolean selected = true;
            if (i < (GROUPS_1_RADIO_NUMBER - 1)) {
                fileNameOnly = resources[i].getName().split("\\.")[0];
                selected = false;
            }
            radioCheckboxes[i] = new Checkbox(fileNameOnly, selected, checkboxGroup[0]);
            radioCheckboxes[i].setName(RADIO_CHECKBOXES_NAME_PRE + i);
            pictureControlPanel.add(radioCheckboxes[i]);
        }

        @SuppressWarnings("all")
        int rows = GROUPS_2_RADIO_NUMBER % 2 == 1 ? GROUPS_2_RADIO_NUMBER / 2 + 1 : GROUPS_2_RADIO_NUMBER / 2;
        Panel radioPanel = new Panel(new GridLayout(rows, 2));
        for (int i = GROUPS_1_RADIO_NUMBER; i < (GROUPS_1_RADIO_NUMBER + GROUPS_2_RADIO_NUMBER); i++) {
            radioCheckboxes[i] = new Checkbox(RADIO_CHECKBOXES_LABEL_PRE + (i - GROUPS_1_RADIO_NUMBER), false, checkboxGroup[1]);
            radioCheckboxes[i].setName(RADIO_CHECKBOXES_NAME_PRE + i);
            radioPanel.add(radioCheckboxes[i]);
        }


        Panel picturePanel = new Panel(new BorderLayout());
        picture = new PictureCanvas();
        picture.init(resources);
        picture.setPreferredSize(new Dimension(125, 125));
        picturePanel.add(picture);


        radioCheckboxesPage.add(picturePanel);
        radioCheckboxesPage.add(radioPanel, BorderLayout.WEST);
        radioCheckboxesPage.add(pictureControlPanel, BorderLayout.EAST);
    }


    private static final String CHECKBOXES_PAGE_NAME = "checkboxesPage";
    private static final int CHECKBOXES_TOTAL_NUMBERS = 14;
    private static final int[] CHECKBOXES_GROUPS_NUMBER_ARRAY = {4, 14};
    private static final String CHECKBOXES_LABEL_PRE = "checkBox";
    private static final String CHECKBOXES_NAME_PRE = "checkBoxes";
    private static final String CHECKBOXES_IMAGE_RESOURCES_PATH = "Java-GUI-AWT/src/main/resources/checkbox_images";
    private static final File DEFAULT_IMAGE = new File("Java-GUI-AWT/src/main/resources/checkbox_images/geek-----.gif");
    private static final BitSet c = createBitSet(0);
    private static final BitSet g = createBitSet(1);
    private static final BitSet h = createBitSet(2);
    private static final BitSet t = createBitSet(3);
    private static BitSet selectedBit = new BitSet();
    private static final Map<BitSet, File> bitSetFileMapper = createMapper();
    // 创建BitSet
    private static BitSet createBitSet(int... indexForBitSet){
        BitSet bitSet = new BitSet();
        for (int i = 0; i < indexForBitSet.length; i++) {
            bitSet.set(indexForBitSet[i]);
        }
        return bitSet;
    }
    // 读取资源
    private static File[] initSelectedPictureCheckboxesResources(){
        final Path resourcePath = Paths.get(CHECKBOXES_IMAGE_RESOURCES_PATH);
        final File resourcePathFile = resourcePath.toFile();
        final File[] listFiles = resourcePathFile.listFiles();
        assert listFiles != null;
        return listFiles;
    }
    // 判断图像名称是否包含个flag
    private static char[] containFlags(String resourceFile){
        final String flag = resourceFile.substring(5, 9);
        return flag.toCharArray();
    }
    // 创建图像和标志的Mapper
    private static Map<BitSet, File> createMapper(){
        Map<BitSet, File> mapper = new HashMap<>();
        File[] files = initSelectedPictureCheckboxesResources();
        for (int i = 0; i < files.length; i++) {
            BitSet fileBitSet = new BitSet();
            final String name = files[i].getName();
            final char[] flags = containFlags(name);
            for (char flag :
                    flags) {
                switch (flag){
                    case 'c': {
                        fileBitSet.or(c);
                        break;
                    }
                    case 'g': {
                        fileBitSet.or(g);
                        break;
                    }
                    case 'h': {
                        fileBitSet.or(h);
                        break;
                    }
                    case 't': {
                        fileBitSet.or(t);
                        break;
                    }
                }
            }
            mapper.put(fileBitSet, files[i]);
        }
        return mapper;
    }
    private SelectedCanvas selectedPicture;
    // checkboxes page
    private void initCheckedBoxesPage(){
        final Panel panel = pageSelector.newPage(CHECKBOXES_PAGE_NAME);
        panel.setLayout(new BorderLayout(20, 10));

        checkboxes = new Checkbox[CHECKBOXES_TOTAL_NUMBERS];
        checkboxes[0] = new Checkbox("chin");
        checkboxes[1] = new Checkbox("glasses");
        checkboxes[2] = new Checkbox("hat");
        checkboxes[3] = new Checkbox("tooth");
        for (int i = 0; i < CHECKBOXES_GROUPS_NUMBER_ARRAY[0]; i++) {
            checkboxes[i].setName(CHECKBOXES_NAME_PRE + i);
        }
        for (int i = CHECKBOXES_GROUPS_NUMBER_ARRAY[0]; i < CHECKBOXES_GROUPS_NUMBER_ARRAY[1]; i++) {
            checkboxes[i] = new Checkbox(CHECKBOXES_LABEL_PRE + i);
            checkboxes[i].setName(CHECKBOXES_NAME_PRE + i);
        }
        Panel controller = new Panel(new GridLayout(4, 1));
        controller.add(checkboxes[0]);
        controller.add(checkboxes[1]);
        controller.add(checkboxes[2]);
        controller.add(checkboxes[3]);

        selectedPicture = new SelectedCanvas(DEFAULT_IMAGE);

        Panel showPanel = new Panel(new GridLayout(5, 2));
        for (int i = CHECKBOXES_GROUPS_NUMBER_ARRAY[0]; i < CHECKBOXES_GROUPS_NUMBER_ARRAY[1]; i++) {
            showPanel.add(checkboxes[i]);
        }

        panel.add(showPanel, BorderLayout.WEST);
        panel.add(controller, BorderLayout.EAST);
        panel.add(selectedPicture);
    }



    private static final String CHOOSE_PAGE_NAME = "choose";
    private void initChoicesPage(){
        final Panel panel = pageSelector.newPage(CHOOSE_PAGE_NAME);

        choices = new Choice[25];
    }


    private static final String LABEL_PAGE_NAME = "label";
    private void initLabelsPage(){
        pageSelector.newPage(LABEL_PAGE_NAME);
    }


    private static final String LIST_PAGE_NAME = "list";
    private static final String[] strings = {
            "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "January",
            "February", "March", "April", "May", "June",
            "July", "August", "September", "October",
            "November", "December"
    };
    private void initListPage(){
        final Panel panel = pageSelector.newPage(LIST_PAGE_NAME);
        panel.setLayout(new GridBagLayout());

        Button[] buttons = new Button[2];
        buttons[0] = new Button("-->");
        buttons[1] = new Button("<--");


        lists = new List[2];
        lists[0] = new List();
        lists[1] = new List();
        // init List
        for (String string : strings) {
            if (Math.random() > 0.5) {
                lists[0].add(string);
            } else {
                lists[1].add(string);
            }
        }

        GridBagConstraints list0Bag = new GridBagConstraints();
        list0Bag.weightx = 100;
        list0Bag.weighty = 100;
        list0Bag.gridx = 0;
        list0Bag.gridy = 0;
        list0Bag.gridheight = 10;
        list0Bag.gridwidth = 4;
        list0Bag.fill = GridBagConstraints.BOTH;
        list0Bag.insets = new Insets(0,0, 0, 20);
        Label label = new Label("1231");


        GridBagConstraints controllerBag0 = new GridBagConstraints();
        controllerBag0.weightx = 0;
        controllerBag0.weighty = 100;

        // 不能浮空设置x、y，也就是说当我们设置了gridy=3的时候
        // gridy=0-2的三个位置必须要有组件
        // 否则设置gridy=3会无效，按钮还是会被顶到最上面！
        // 实际上是存在gridy=0-2这三个位置的，只不过他们的高度都是0，所以组件才会顶到上面去
        // 这时候一种方式是设置
        controllerBag0.gridx = 4;
        controllerBag0.gridy = 0;
        controllerBag0.gridheight = 5;
        controllerBag0.gridwidth = 2;
        controllerBag0.ipadx = 5;
        controllerBag0.ipady = 5;
        controllerBag0.anchor = GridBagConstraints.SOUTH;
        controllerBag0.insets = new Insets(0, 0, 15, 0);

        // 改进的GridBagConstraints布局？
        GridBagConstraints controllerBag1 = new GridBagConstraints();
        controllerBag1.weightx = 0;
        controllerBag1.weighty = 100;
        controllerBag1.gridx = 4;
        controllerBag1.gridy = 5;
        controllerBag1.gridheight = 5;
        controllerBag1.gridwidth = 2;
        // 内边距
        controllerBag1.ipadx = 5;
        controllerBag1.ipady = 5;
        // 当组件大小小于区域大小的时候，如何设置组件的布局
        controllerBag1.anchor = GridBagConstraints.NORTH;
        // 外边距
        controllerBag1.insets = new Insets(15, 0, 0, 0);

        GridBagConstraints list1Bag = new GridBagConstraints();
        // 决定了横竖轴是否能够生长
        list1Bag.weightx = 100;
        list1Bag.weighty = 100;
        list1Bag.gridx = 6;
        list1Bag.gridy = 0;
        list1Bag.gridheight = 10;
        list1Bag.gridwidth = 4;
        list1Bag.fill = GridBagConstraints.BOTH;
        list1Bag.insets = new Insets(0,20, 0, 0);


        panel.add(lists[0], list0Bag);
        panel.add(buttons[0], controllerBag0);
        panel.add(buttons[1], controllerBag1);
        panel.add(lists[1], list1Bag);
    }

    private void initScrollbarsPage(){
        scrollbars = new Scrollbar[4];
        scrollbars[0] = new Scrollbar();
    }

    private void initTextAreaPage(){

    }

    private void initTextFieldsPage(){

    }


    public AwtComponentsShow() {
        initPageWidget();
        // init all controls
        initButtonPage();
        initRadioCheckboxPage();
        initCheckedBoxesPage();
        initListPage();


        setLocation(500, 500);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setTitle("AWT组件一览, 共有9种组件, 一共9种组件：按钮、画布、选择框、下拉列表框、标签、列表框、文本编辑框、文本编辑域、滑块条");
        setVisible(true);

    }

    private class PictureCanvas extends Canvas {
        private Map<String, BufferedImage> picNameResourceMapping;
        private String selected = "none";

        public void init(File[] picFiles) {
            picNameResourceMapping = new HashMap<>();
            for (File pic :
                    picFiles) {
                final String name = pic.getName().split("\\.")[0];
                try (FileInputStream resource = new FileInputStream(pic)) {
                    final BufferedImage image = ImageIO.read(resource);
                    picNameResourceMapping.put(name, image);
                } catch (FileNotFoundException e) {
                    System.err.println("Couldn't find file: " + pic);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setSelectedAndShow(String selectPicName) {
            selected = selectPicName;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth() - 2, getHeight() - 2);
            if ("none".equals(selected)) {
                g.drawLine(0, 0, getWidth() - 2, getHeight() - 2);
                g.drawLine(0, getHeight() - 2, getWidth() - 2, 0);
                return;
            }
            final BufferedImage image = picNameResourceMapping.get(selected);
            g.drawImage(image, 2, 2,getWidth() - 2, getHeight() - 2 , null);
        }
    }

    private class SelectedCanvas extends Canvas{

        private File imageFile = null;

        public SelectedCanvas(File initImage){
            this.imageFile = initImage;
            repaint();
        }

        public void setImageFile(File file){
            imageFile = file;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth() - 2, getHeight() - 2);
            BufferedImage image = null;
            try {
                image = ImageIO.read(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(image, 2, 2,getWidth() - 4, getHeight() - 4 , null);
        }
    }


}
