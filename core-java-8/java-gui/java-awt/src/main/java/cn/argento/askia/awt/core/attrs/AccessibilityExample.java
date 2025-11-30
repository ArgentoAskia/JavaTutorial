package cn.argento.askia.awt.core.attrs;

import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleText;
import javax.swing.*;
import java.awt.*;

public class AccessibilityExample {
    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("Accessibility Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // 创建按钮并设置可访问性属性
        JButton button = new JButton("Click Me");
        setupButtonAccessibility(button);

        // 创建文本框并设置可访问性属性
        JTextField textField = new JTextField(15);
        setupTextFieldAccessibility(textField);

        // 创建标签并设置可访问性属性
        JLabel label = new JLabel("Sample Label");
        setupLabelAccessibility(label);

        frame.add(button);
        frame.add(textField);
        frame.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    private static void setupButtonAccessibility(JButton button) {
        // 获取 AccessibleContext
        AccessibleContext context = button.getAccessibleContext();

        // 设置可访问的名称和描述
        context.setAccessibleName("Action Button");
        context.setAccessibleDescription("This button performs an action when clicked");

        // 添加属性变化监听器
        context.addPropertyChangeListener(evt -> {
            System.out.println("Property changed: " + evt.getPropertyName());
        });
    }

    private static void setupTextFieldAccessibility(JTextField textField) {
        AccessibleContext context = textField.getAccessibleContext();
        context.setAccessibleName("Input Field");
        context.setAccessibleDescription("Enter text in this field");

        // 检查是否支持 AccessibleText 接口
        if (context instanceof AccessibleText) {
            System.out.println("TextField supports AccessibleText interface");
        }
    }

    private static void setupLabelAccessibility(JLabel label) {
        AccessibleContext context = label.getAccessibleContext();
        context.setAccessibleName("Information Label");
        context.setAccessibleDescription("This label displays information");
    }
}