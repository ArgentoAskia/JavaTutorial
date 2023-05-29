package cn.argentoaskia.awt.layout;

import java.awt.*;

public class GridBagLayoutDemo2 {

}


class GripBagPanel extends Panel{
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 20;

    private Choice face;
    private Choice size;

    private Checkbox bold;
    private Checkbox italic;

    private TextArea sample;
    private Label faceLabel;

    private Label sizeLabel;

    private Frame windows;

    private void initLayout(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
    }
    private void init(){
        faceLabel = new Label("Face:");
        face = new Choice();
        face.add("Serif");
        face.add("SansSerif");
        face.add("Monospaced");
        face.add("Dialog");
        face.add("DialogInput");

        sizeLabel = new Label("Size:");
        size = new Choice();
        size.add("8");
        size.add("10");
        size.add("12");
        size.add("15");
        size.add("18");
        size.add("24");
        size.add("36");
        size.add("48");

        bold = new Checkbox("bold");
        italic = new Checkbox("italic");
        sample = new TextArea("",TEXT_ROWS, TEXT_COLUMNS, TextArea.SCROLLBARS_VERTICAL_ONLY);
        sample.setText("The quick brown fox jumps over the lazy dog!");
        sample.setEditable(false);
    }
    private void addLayout(){
        add(faceLabel, new GBC(0,0).setAnchor(GBC.EAST));
        add(face, new GBC(1,0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
        add(sizeLabel, new GBC(0,1).setAnchor(GBC.EAST));
        add(size, new GBC(1,1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
        add(bold, new GBC(0, 2, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        add(italic, new GBC(0, 3, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        add(sample, new GBC(2, 1, 2, 3).setFill(GBC.BOTH).setWeight(100, 100));
    }

    GripBagPanel(Frame frame){
        this.windows = frame;
        initLayout();
        init();
        addLayout();
    }

    private void resizeToRepaint(){
        int width = windows.getWidth();
        int height = windows.getHeight();
        windows.setSize(width + 1, height);
    }

    public void resizeFaceChoice(GBC gbc){
        LayoutManager2 layout = (LayoutManager2) getLayout();

    }

}