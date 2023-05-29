package cn.argentoaskia.swing.widgets.demos;

import com.sun.java.swing.SwingUtilities3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Consumer;

public class FileChooserDemo2 extends JPanel implements ActionListener {
    private JPanel buttonPanel;
    private JButton openButton;
    private JButton saveButton;
    private JScrollPane jScrollPane;
    private JTextArea jTextArea;

    private JFileChooser fileChooser;
    private String lastDirectory = "C:\\";

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);
        if (e.getSource() == openButton){
            int i = fileChooser.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    List<String> list = Files.readAllLines(selectedFile.toPath());
                    list.forEach(s -> {
                        jTextArea.append(s);
                        jTextArea.append("\n");
                    });
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public FileChooserDemo2(){
        setLayout(new BorderLayout());
        //
        fileChooser = new JFileChooser(lastDirectory);

        buttonPanel = new JPanel();
        openButton = new JButton("open a file...");
        openButton.setPreferredSize(new Dimension(200, 30));

        saveButton = new JButton("save a file...");
        saveButton.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        openButton.addActionListener(this);
        saveButton.addActionListener(this);

        jTextArea = new JTextArea(10, 50);
        jScrollPane = new JScrollPane(jTextArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);

    }

    private static void createAndShowGUI(){
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new FileChooserDemo2());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
