package cn.argento.askia.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleUIDesigner extends JFrame {
    private JPanel designPanel;
    private JPanel componentPanel;
    private JTextArea codeArea;

    public SimpleUIDesigner() {
        setTitle("Simple UI Designer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 组件面板
        componentPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Button");
        addButton.addActionListener(e -> addButtonToDesignPanel());
        componentPanel.add(addButton);

        // 设计区域
        designPanel = new JPanel(new BorderLayout());
        designPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        designPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addButtonToDesignPanel();
                }
            }
        });

        // 代码生成区域
        codeArea = new JTextArea();
        codeArea.setEditable(false);

        // 添加到主面板
        mainPanel.add(componentPanel, BorderLayout.NORTH);
        mainPanel.add(designPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(codeArea), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addButtonToDesignPanel() {
        JButton newButton = new JButton("New Button");
        newButton.setName("button" + designPanel.getComponentCount()); // 为按钮命名
        designPanel.add(newButton);
        designPanel.revalidate();
        designPanel.repaint();

        updateCodeArea();
    }

    private void updateCodeArea() {
        StringBuilder code = new StringBuilder();
        code.append("import javax.swing.*;\n");
        code.append("public class GeneratedUI extends JFrame {\n");
        code.append("    public GeneratedUI() {\n");
        code.append("        setTitle(\"Generated UI\");\n");
        code.append("        setSize(400, 300);\n");
        code.append("        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n");
        code.append("        JPanel panel = new JPanel(new BorderLayout());\n");

        for (Component comp : designPanel.getComponents()) {
            if (comp instanceof JButton) {
                code.append("        JButton ").append(comp.getName()).append(" = new JButton(\"").append(((JButton) comp).getText()).append("\");\n");
                code.append("        panel.add(").append(comp.getName()).append(", BorderLayout.CENTER);\n");
            }
        }

        code.append("        setContentPane(panel);\n");
        code.append("    }\n");
        code.append("    public static void main(String[] args) {\n");
        code.append("        new GeneratedUI().setVisible(true);\n");
        code.append("    }\n");
        code.append("}\n");

        codeArea.setText(code.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleUIDesigner().setVisible(true);
            }
        });
    }
}