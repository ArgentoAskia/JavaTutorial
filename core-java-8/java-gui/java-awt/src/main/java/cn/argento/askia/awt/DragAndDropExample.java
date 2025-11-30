package cn.argento.askia.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class DragAndDropExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drag and Drop Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // 创建一个可拖放的组件
            JButton draggableButton = new JButton("Drag Me");
            draggableButton.setTransferHandler(new TransferHandler("text"));

            // 创建一个可以接受拖放的组件
            JTextArea dropArea = new JTextArea();
            dropArea.setTransferHandler(new TransferHandler("text"));
            dropArea.setDragEnabled(true);

            // 添加组件到窗口
            frame.add(draggableButton, BorderLayout.NORTH);
            frame.add(new JScrollPane(dropArea), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}