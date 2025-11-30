package cn.argento.askia.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIDesigner extends JFrame {
    private JPanel mainPanel;
    private JPanel componentPanel;
    private JPanel designPanel;
    private JTextArea codeArea;

    public UIDesigner() {
        setTitle("Swing UI Designer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());

        componentPanel = new JPanel(new FlowLayout());
        JButton button = new JButton("Add Button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButtonToDesignPanel();
            }
        });
        componentPanel.add(button);

        designPanel = new JPanel(new BorderLayout());
        designPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        codeArea = new JTextArea();
        codeArea.setEditable(false);

        mainPanel.add(componentPanel, BorderLayout.NORTH);
        mainPanel.add(designPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(codeArea), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addButtonToDesignPanel() {
        JButton newButton = new JButton("New Button");
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
                new UIDesigner().setVisible(true);
            }
        });
    }
}
