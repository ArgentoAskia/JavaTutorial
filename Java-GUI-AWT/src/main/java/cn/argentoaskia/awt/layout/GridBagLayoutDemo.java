package cn.argentoaskia.awt.layout;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GridBagLayoutDemo extends Frame{
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 20;

    private Choice face;
    private Choice size;

    private Checkbox bold;
    private Checkbox italic;

    private TextArea sample;

    public GridBagLayoutDemo(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        ItemListener listener = event -> updateSample();

        Label faceLabel = new Label("Face:");

        face = new Choice();
        face.add("Serif");
        face.add("SansSerif");
        face.add("Monospaced");
        face.add("Dialog");
        face.add("DialogInput");
        // 事件！
        face.addItemListener(listener);

        Label sizeLabel = new Label("Size:");
        size = new Choice();
        size.add("8");
        size.add("10");
        size.add("12");
        size.add("15");
        size.add("18");
        size.add("24");
        size.add("36");
        size.add("48");
        size.addItemListener(listener);

        bold = new Checkbox("bold");
        bold.setBackground(Color.BLACK);
        bold.addItemListener(listener);
        italic = new Checkbox("italic");
        bold.addItemListener(listener);

        sample = new TextArea("",TEXT_ROWS, TEXT_COLUMNS, TextArea.SCROLLBARS_VERTICAL_ONLY);
        sample.setText("The quick brown fox jumps over the lazy dog!");
        sample.setEditable(false);

        // add components
        add(faceLabel, new GBC(0,0).setAnchor(GBC.EAST));
        add(face, new GBC(1,0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
        add(sizeLabel, new GBC(0,1).setAnchor(GBC.EAST));
        add(size, new GBC(1,1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
        add(bold, new GBC(0, 2, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        add(italic, new GBC(0, 3, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        add(sample, new GBC(2, 1, 2, 3).setFill(GBC.BOTH).setWeight(100, 100));
        Label label = new Label("        ");
        label.setBackground(Color.BLACK);
        Label label2 = new Label("        ");
        label2.setBackground(Color.BLACK);
        add(label2, new GBC(2, 0).setFill(GBC.BOTH));
        add(label, new GBC(3, 5, 1, 4).setFill(GBC.BOTH).setWeight(100, 100));
        // 填充是以单位为基础的
        Label label3 = new Label("        ");
        label3.setBackground(Color.BLUE);
        add(label3, new GBC(2, 5, 1, 2).setFill(GBC.BOTH).setWeight(100, 100));
        // 如上面的这个,明明label3和label2有不同的gridheight,但是他们会占满同一个高度！
        // 当gridx、y设置不连续的时候，不连续的部分默认宽高都是0
        Label label4 = new Label("        ");
        label4.setBackground(Color.GREEN);
        add(label4, new GBC(2, 7, 1, 1).setFill(GBC.BOTH).setWeight(100, 100));
        Label label5 = new Label("        ");
        label5.setBackground(Color.RED);
        add(label5, new GBC(2, 8, 1, 1).setFill(GBC.BOTH).setWeight(100, 100));
        Label label6 = new Label("        ");
        label6.setBackground(Color.RED);
        add(label6, new GBC(4, 5, 1, 1).setFill(GBC.BOTH).setWeight(100, 100));
        pack();
        Label label7 = new Label("12312131");
        label7.setBackground(Color.MAGENTA);
        add(label7, new GBC(4, 6, 1, 1).setFill(GBC.BOTH).setWeight(100, 100).setIpad(300, 30));
        pack();
        updateSample();
    }

    private void updateSample() {
        String fontFace = face.getSelectedItem();
        int fontStyle = (bold.getState()? Font.BOLD : 0) +
                (italic.getState()? Font.ITALIC : 0);
        int fontSize = Integer.parseInt(size.getSelectedItem());
        Font font = new Font(fontFace, fontStyle, fontSize);
        sample.setFont(font);
        sample.repaint();
    }

    public static void main(String[] args) {
        GridBagLayoutDemo gridBagLayoutDemo = new GridBagLayoutDemo();
        gridBagLayoutDemo.setTitle("GridBagLayoutDemo");
        gridBagLayoutDemo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        gridBagLayoutDemo.setVisible(true);
    }
}
