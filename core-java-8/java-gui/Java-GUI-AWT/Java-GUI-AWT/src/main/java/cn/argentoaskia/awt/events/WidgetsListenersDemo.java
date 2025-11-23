package cn.argentoaskia.awt.events;

import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WidgetsListenersDemo extends Frame{
    // ActionListener对应的组件：按钮、菜单按钮、List、TextField按回车
    private Panel actionListenerButtonPanel;
    private Button actionListenerButton;
    private Label actionListenerButtonLabel;

    private Panel actionListenerListPanel;
    private List actionListenerList;
    private Label actionListenerListLabel;

    private Panel actionListenerTextFieldPanel;
    private TextField actionListenerTextField;
    private Label actionListenerTextFieldLabel;

    private Panel adjustmentListenerScrollbarPanel;
    private Scrollbar adjustmentListenerScroll;
    private Label adjustmentListenerScrollLabel;


    private void initLayout(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
    }

    private void initActionListenerWidgets(){
        actionListenerButtonPanel = new Panel();
        actionListenerButtonPanel.setLayout(new BorderLayout());
        actionListenerButton = new Button("actionListenerButton");
        actionListenerButtonLabel = new Label("ActionListener in Button");
        actionListenerButtonPanel.add(actionListenerButton, BorderLayout.CENTER);
        actionListenerButtonPanel.add(actionListenerButtonLabel, BorderLayout.SOUTH);

        actionListenerListPanel = new Panel();
        actionListenerListPanel.setLayout(new BorderLayout());
        actionListenerList = new List();
        actionListenerList.add("actionListener in List");
        actionListenerList.add("actionListener in List");
        actionListenerList.add("actionListener in List");
        actionListenerList.add("actionListener in List");
        actionListenerListLabel = new Label("ActionListener in Button");
    }

    public WidgetsListenersDemo(){
        initLayout();
        initActionListenerWidgets();

    }




    // Menu的事件！
    private Menu menu;
    private MenuItem menuItem;



}

class imageCanvas extends Canvas{
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
    }
}
