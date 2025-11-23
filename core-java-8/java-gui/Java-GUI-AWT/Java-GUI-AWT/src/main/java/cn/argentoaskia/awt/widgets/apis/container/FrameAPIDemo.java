package cn.argentoaskia.awt.widgets.apis.container;

import java.awt.*;

public class FrameAPIDemo extends Frame {
//    public FrameAPIDemo(){
//
//
//        // 设置总是显示在前面
//        // setAlwaysOnTop();
//        // 设置当窗口被激活的时候是否自动获取焦点
//        // setAutoRequestFocus();
//        // 设置背景色
//        // setBackground();
//        // 设置bounds
//        // setBounds();
//        // 未知
//        // setComponentOrientation();
//        // 设置窗口组件刷新顺序
//        // setComponentZOrder();
//        // 设置窗口内鼠标样式
//        // setCursor();
//        // 设置拖放目标，只有窗口设置了允许拖放的时候才有效
//        // setDropTarget();
//        // 窗口是否可用
//        // setEnabled();
//        // 设置窗口状态，如最大化、最小化等等
//        // setExtendedState();
//        // 设置窗口是否可以获取焦点，设置这个值，在焦点轮切的时候可以被切换上
//        // setFocusable();
//        // 设置窗口是否可以获取焦点，这个方法会根据窗口状态来影响判断
//        // setFocusableWindowState();
//        // setFocusCycleRoot();        // 这个方法没用
//        // 设置焦点切换按键
//        // setFocusTraversalKeys();
//        // 是否开启焦点切换功能
//        // setFocusTraversalKeysEnabled();
//
//        // 设置焦点切换的方式
////        setFocusTraversalPolicy();
//        // 是否开启设置焦点切换的方式
////        setFocusTraversalPolicyProvider();
//        // 设置字体
////        setFont();
//        // 设置前景颜色
////        setForeground();
////        // 设置图标图像（可以设置多个）
////        setIconImage();
////        setIconImages();
////        // 设置是否忽略窗口重绘
////        setIgnoreRepaint();
////        // 设置窗口布局
////        setLayout();
////        // 设置locale
////        setLocale();
////        // 设置窗口位置
////        setLocation();
////        // 设置是否由所在的平台来决定窗口位置
////        setLocationByPlatform();
////        // 设置该窗口的父窗口
////        setLocationRelativeTo();
////        // 设置窗口的最大边界
////        setMaximizedBounds();
////        // 设置窗口的最大大小
////        setMaximumSize();
////        // 设置菜单栏
////        setMenuBar();
////        // 设置窗口最小大小
////        setMinimumSize();
////        // 设置窗口模态排除类型，一般情况下当一个窗口弹出一些信息框，那么那个窗口将会被阻塞，动不了，设置了这个属性，则窗口可能将不会被阻塞。
////        setModalExclusionType();
////        // 设置组件名称，注意区别与setTitle()，setTitle是设置标题，而这个是在相当于为窗口设置一个名字，一般用于标记窗口|组件。
////        setName();
////        // 设置窗口透明度，值在0.0f-1.0f之间，需要注意，设置透明度需要先把窗口装饰区去掉，也就是setUndecorated(true)
////        setOpacity();
////        // 设置组件更加偏向的大小，一般在布局的设置中会采用这个，注意设置组件的大小不一定就会起作用，AWT会计算组件的大小以更好适应布局情况
////        setPreferredSize();
////        // 设置窗口是否可拉伸。
////        setResizable();
////        // 设置窗口的形状
////        setShape();
////        // 设置窗口大小
////        setSize();
////        // 设置窗口状态
////        setState();
////        // 设置窗口标题
////        setTitle();
////        // 设置窗口类型
////        setType(Type.POPUP);
////        // 设置是否去除装饰栏
////        setUndecorated();
////        // 设置窗口可视
////        setVisible();
//        setAutoRequestFocus(false);
//        // 设置窗口显示位置和大小
//        setBounds(200, 200, 500, 500);
//        // 设置标题
//        setTitle("第一个AWT窗口");
//        // 默认情况下AWT窗口关闭按钮没反应，需要自己定义关闭按钮的行为，使用下面的语句可以实现关闭功能，实际上是一个事件监听，后面会介绍
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//        // 显示窗口，窗口可视
//        setVisible(true);
//        // 释放该窗口上的所有资源，一旦调用该方法，该窗口上的所有子窗口，窗口本身都会被释放，内存归还给系统
////        this.dispose();
//        // ?
//        this.enableInputMethods(true);
//
//        // 是否包含屏幕上的某个点
//        this.contains(new Point());
//        this.contains(20, 30);
//        // 在窗口上寻找某个点是否包含坐标
//        this.findComponentAt(20, 30);
//        this.findComponentAt(new Point());
//        this.firePropertyChange("123", 1, 2);
//        // 返回组件的布局方式，大概率返回0.5
//        float alignmentX = this.getAlignmentX();
//        float alignmentY = this.getAlignmentY();
//        System.out.println(alignmentX);
//        System.out.println(alignmentY);
//        // get相关
//        // ?
//        this.getAccessibleContext();
//        // 获取背景颜色
//        this.getBackground();
//        // 获取基线 ×
//        this.getBaseline();
//        // 获取基线行为 ×
//        this.getBaselineResizeBehavior();
//        // 获取窗口矩形
//        this.getBounds();
//        this.getBounds();
//        // ?
//        this.getBufferStrategy();
//        // ?
//        this.getColorModel();
//        // 从窗口的ArrayList下标的形式获取组件
//        this.getComponent();
//        // 从窗口某个点获取组件
//        this.getComponentAt();
//        this.getComponentAt();
//        // 获取窗口所有组件数
//        this.getComponentCount();
//        // 获取事件监听
//        this.getComponentListeners();
//        this.getComponentOrientation();
//        this.getComponents();
//        this.getComponentZOrder();
//        this.getContainerListeners();
//        this.getCursor();
//        this.getDropTarget();
//        this.getExtendedState();
//        this.getFocusableWindowState();
//        this.getFocusCycleRootAncestor();
//        this.getFocusListeners();
//        this.getFocusOwner();
//        this.getFocusTraversalKeys();
//        this.getFocusTraversalKeysEnabled();
//        this.getFocusTraversalPolicy();
//        this.getFont();
//        this.getFontMetrics();
//        this.getForeground();
//        this.getGraphics();
//        this.getGraphicsConfiguration();
//        this.getHeight();
//        this.getHierarchyBoundsListeners();
//        this.getHierarchyListeners();
//        this.getIconImage();
//        this.getIconImages();
//        this.getIgnoreRepaint();
//        this.getInputContext();
//        this.getInputMethodListeners();
//        this.getInputMethodRequests();
//        this.getInsets();
//        this.getKeyListeners();
//        this.getLayout();
//        this.getListeners();
//        this.getLocale();
//        this.getLocation();
//        this.getLocationOnScreen();
//        this.getMaximizedBounds();
//        this.getMaximumSize();
//        this.getMenuBar();
//        this.getMinimumSize();
//        this.getModalExclusionType();
//        this.getMostRecentFocusOwner();
//        this.getMouseListeners();
//        this.getMouseMotionListeners();
//        this.getMousePosition();
//        this.getMouseWheelListeners();
//        this.getName();
//        this.getOpacity();
//        this.getOwnedWindows();
//        this.getOwner();
//        this.getParent();
//        this.getPreferredSize();
//        this.getPropertyChangeListeners();
//        this.getPropertyChangeListeners();
//        this.getShape();
//        this.getSize();
//        this.getState();
//        this.getTitle();
//        this.getToolkit();
//        this.getTreeLock();
//        this.getType();
//        this.getWarningString();
//        this.getWidth();
//        this.getWindowFocusListeners();
//        this.getWindowListeners();
//        this.getWindowStateListeners();
//        this.getX();
//        this.getY();
//        // 判别属性
//        this.areFocusTraversalKeysSet();
//        this.isActive();
//        this.isAlwaysOnTop();
//        this.isAlwaysOnTopSupported();
//        this.isAncestorOf();
//        this.isAutoRequestFocus();
//        this.isBackgroundSet();
//        this.isCursorSet();
//        this.isDisplayable();
//        this.isDoubleBuffered();
//        this.isEnabled();
//        this.isFocusable();
//        this.isFocusableWindow();
//        this.isFocusCycleRoot();
//        this.isFocused();
//        this.isFocusOwner();
//        this.isFocusTraversalPolicyProvider();
//        this.isFocusTraversalPolicySet();
//        this.isFontSet();
//        this.isForegroundSet();
//        this.isLightweight();
//        this.isLocationByPlatform();
//        this.isMaximumSizeSet();
//        this.isMinimumSizeSet();
//        this.isOpaque();
//        this.isPreferredSizeSet();
//        this.isResizable();
//        this.isShowing();
//        this.isUndecorated();
//        this.isValid();
//        this.isValidateRoot();
//        this.isVisible();
//
//        // remove component
//
//        // 列出一个窗口有多少个组件
//        this.list();
//        this.hasFocus();
//        this.pack();
//        this.paint();
//        this.paintAll();
//        this.paintComponents();
//        this.paramString();
//        this.print();
//        this.printAll();
//        this.printComponents();
//        this.repaint();
//        // this.processXXXXEvent();
//        this.requestFocus();
//        this.requestFocusInWindow();
//        this.revalidate();
//        this.toBack();
//        this.toFront();
//        this.toString();
//        this.transferFocus();
//        this.transferFocusBackward();
//        this.transferFocusDownCycle();
//        this.transferFocusUpCycle();
//        this.transferFocusUpCycle();
//        this.update();
//        this.validate();
//        this.validateTree();
//
//
//        // 用于调试
//        String s = this.paramString();
//        System.out.println(s);
//
//
//    }

    public static void main(String[] args) {
        new FrameAPIDemo();
    }
}
