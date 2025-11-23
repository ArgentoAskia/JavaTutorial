package cn.argentoaskia.awt.layout;

import javax.jws.Oneway;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridLayoutDemo extends Frame{

    private GridLayout layout;
    private List<Component> componentList;
    private volatile int totalCounts;
    private Panel componentPanel;
    private Random random = new Random();
    // samples for components
    private void createNewButton(){
        System.out.println(totalCounts);
        System.out.println(componentList.size());
        if (componentList.size() < totalCounts){
            Button button = new Button("Button");
            componentPanel.add(button);
            componentList.add(button);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewLabel(){
        if (componentList.size() < totalCounts){
            Label label = new Label("Label");
            componentPanel.add(label);
            componentList.add(label);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewList(){
        if (componentList.size() < totalCounts){
            final int itemCount = random.nextInt(30);
            java.awt.List list = new java.awt.List();
            for (int i = 0; i < itemCount; i++) {
                list.add("item " + i);
            }
            componentPanel.add(list);
            componentList.add(list);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewChoice(){
        if (componentList.size() < totalCounts){
            final int itemCount = random.nextInt(30);
            Choice choice = new Choice();
            for (int i = 0; i < itemCount; i++) {
                choice.add("item " + i);
            }
            componentPanel.add(choice);
            componentList.add(choice);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewScrollbar(){
        if (componentList.size() < totalCounts){
            Scrollbar scrollbar = new Scrollbar();
            if (random.nextBoolean()){
                scrollbar.setOrientation(Scrollbar.HORIZONTAL);
            }
            else{
                scrollbar.setOrientation(Scrollbar.VERTICAL);
            }
            scrollbar.setValues(50, 50, 0 ,100);
            scrollbar.setUnitIncrement(1);
            componentPanel.add(scrollbar);
            componentList.add(scrollbar);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewCheckBox(){
        if (componentList.size() < totalCounts){
            Checkbox checkbox = new Checkbox("checkbox");
            checkbox.setState(random.nextBoolean());
            componentPanel.add(checkbox);
            componentList.add(checkbox);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewRadioCheckBox(){
        if (componentList.size() < totalCounts){
            Checkbox checkbox = new Checkbox("checkbox");
            checkbox.setCheckboxGroup(new CheckboxGroup());
            checkbox.setState(random.nextBoolean());
            componentPanel.add(checkbox);
            componentList.add(checkbox);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewTextField(){
        if (componentList.size() < totalCounts){
           TextField textField = new TextField(10);
           componentPanel.add(textField);
           componentList.add(textField);
            return;
        }
        throw new IllegalComponentStateException();
    }
    private void createNewTextArea(){
        if (componentList.size() < totalCounts){
            TextArea textArea = new TextArea(5, 10);
            componentPanel.add(textArea);
            componentList.add(textArea);
            return;
        }
        throw new IllegalComponentStateException();
    }

    private void setComponentPanelNewLayout(int rows, int columns, int hgaps, int vgaps){
        // remove all components for re-add layout
        componentPanel.removeAll();
        componentList.clear();

        // set counts for components add
        totalCounts = rows * columns;
        layout = new GridLayout();
        layout.setColumns(columns);
        layout.setHgap(hgaps);
        layout.setRows(rows);
        layout.setVgap(vgaps);
        componentPanel.setLayout(layout);
    }


    private Panel controlPanel;
    private Label rowsLabel;
    private TextField rowTextField;
    private Label columnsLabel;
    private TextField columnsTextField;
    private Label hgapLabel;
    private TextField hgapTextField;
    private Label vgapLabel;
    private TextField vgapTextField;
    private Button updateLayoutButton;

    private Label componentTypesLabel;
    private Choice componentTypesChoice;
    private Button addComponentButton;
    private Button clearComponentButton;
    private Label addWarning;

    private void init(){
        componentList = new ArrayList<>();
        componentPanel = new Panel();
        setComponentPanelNewLayout(5, 5, 5, 5);
        totalCounts = 25;
        add(componentPanel);

        controlPanel = new Panel(new GridLayout(2, 1));
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        controlPanel.add(p1);
        controlPanel.add(p2);
        add(controlPanel, BorderLayout.SOUTH);


        // CONTROLLER 1
        rowsLabel = new Label("rows");
        p1.add(rowsLabel);
        rowTextField = new TextField(10);
        rowTextField.setText("5");
        p1.add(rowTextField);

        columnsLabel = new Label("cols");
        p1.add(columnsLabel);
        columnsTextField = new TextField(10);
        columnsTextField.setText("5");
        p1.add(columnsTextField);

        hgapLabel = new Label("hgap");
        p1.add(hgapLabel);
        hgapTextField = new TextField(10);
        hgapTextField.setText("5");
        p1.add(hgapTextField);

        vgapLabel = new Label("hgap");
        p1.add(vgapLabel);
        vgapTextField = new TextField(10);
        vgapTextField.setText("5");
        p1.add(vgapTextField);

        updateLayoutButton = new Button("update layout");
        p1.add(updateLayoutButton);


        // controller 2
        componentTypesLabel = new Label("component types");
        componentTypesChoice = new Choice();
        addComponentButton = new Button("add to panel");
        clearComponentButton = new Button("clear all");
        clearComponentButton.setEnabled(false);
        addWarning = new Label("Panel is full!! Can't not add Compoment any more! please clear the panel!");
        addWarning.setForeground(Color.RED);
        addWarning.setVisible(false);
        p2.add(componentTypesLabel);
        p2.add(componentTypesChoice);
        p2.add(addComponentButton);
        p2.add(clearComponentButton);
        p2.add(addWarning);
        componentTypesChoice.add("Button");
        componentTypesChoice.add("Label");
        componentTypesChoice.add("List");
        componentTypesChoice.add("Choice");
        componentTypesChoice.add("TextField");
        componentTypesChoice.add("TextArea");
        componentTypesChoice.add("CheckBox");
        componentTypesChoice.add("RadioCheckBox");
        componentTypesChoice.add("ScrollBar");
    }

    private void initEvents(){
        final ActionListener clearOrNewLayoutActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String row = rowTextField.getText();
                int rows = Integer.parseInt(row);
                final String column = columnsTextField.getText();
                int columns = Integer.parseInt(column);
                final String hgap = hgapTextField.getText();
                final String vgap = vgapTextField.getText();
                setComponentPanelNewLayout(rows, columns, Integer.parseInt(hgap), Integer.parseInt(vgap));
                addComponentButton.setEnabled(true);
                addWarning.setVisible(false);
                clearComponentButton.setEnabled(false);
                componentPanel.repaint();
                componentPanel.validate();
                controlPanel.repaint();
                controlPanel.validate();
            }
        };

        final ActionListener resetActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addComponentButton.setEnabled(true);
                addWarning.setVisible(false);
                clearComponentButton.setEnabled(false);
                componentPanel.removeAll();
                componentList.clear();
                componentPanel.repaint();
                componentPanel.validate();
                controlPanel.repaint();
                controlPanel.validate();
            }
        };

        updateLayoutButton.addActionListener(clearOrNewLayoutActionListener);
        addComponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    final String selectedItem = componentTypesChoice.getSelectedItem();
                    switch (selectedItem){
                        case "Button": {
                            createNewButton();
                            break;
                        }
                        case "Label": {
                            createNewLabel();
                            break;
                        }
                        case "List": {
                            createNewList();
                            break;
                        }
                        case "Choice": {
                            createNewChoice();
                            break;
                        }
                        case "TextField": {
                            createNewTextField();
                            break;
                        }
                        case "TextArea": {
                            createNewTextArea();
                            break;
                        }
                        case "CheckBox": {
                            createNewCheckBox();
                            break;
                        }
                        case "RadioCheckBox": {
                            createNewRadioCheckBox();
                            break;
                        }
                        case "ScrollBar": {
                            createNewScrollbar();
                            break;
                        }
                    }
                    clearComponentButton.setEnabled(true);
                    addComponentButton.setEnabled(true);
                } catch (IllegalComponentStateException e1){
                    addWarning.setVisible(true);
                    addComponentButton.setEnabled(false);
                    clearComponentButton.setEnabled(true);
                    controlPanel.repaint();
                    controlPanel.validate();
                }
                componentPanel.repaint();
                componentPanel.validate();
            }
        });
        clearComponentButton.addActionListener(resetActionListener);

    }


    public GridLayoutDemo(){
        init();
        initEvents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new GridLayoutDemo();
    }
}
