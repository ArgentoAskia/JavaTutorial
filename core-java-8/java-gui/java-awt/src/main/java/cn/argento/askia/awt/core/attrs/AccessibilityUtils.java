package cn.argento.askia.awt.core.attrs;

import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class AccessibilityUtils {

    /**
     * 为组件设置完整的可访问性信息
     */
    public static void makeComponentAccessible(JComponent component,
                                               String name,
                                               String description,
                                               String tooltip) {
        AccessibleContext context = component.getAccessibleContext();

        // 设置基本属性
        context.setAccessibleName(name);
        context.setAccessibleDescription(description);

        // 设置工具提示（也用于可访问性）
        component.setToolTipText(tooltip);

        // 设置键盘助记符（如果适用）
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            // 这里可以设置助记符，例如：button.setMnemonic('C');
        }
    }

    /**
     * 为容器中的所有组件设置可访问性
     */
    public static void makeContainerAccessible(Container container) {
        Component[] components = container.getComponents();
        for (Component comp : components) {
            if (comp instanceof JComponent) {
                JComponent jcomp = (JComponent) comp;
                String className = comp.getClass().getSimpleName();
                makeComponentAccessible(jcomp,
                        className + " Component",
                        "This is a " + className + " with accessibility support",
                        className + " tooltip");
            }

            // 递归处理子容器
            if (comp instanceof Container) {
                makeContainerAccessible((Container) comp);
            }
        }
    }

    /**
     * 打印组件的可访问性信息（用于调试）
     */
    public static void printAccessibilityInfo(JComponent component) {
        AccessibleContext context = component.getAccessibleContext();
        System.out.println("=== Accessibility Info for " + component.getClass().getSimpleName() + " ===");
        System.out.println("Name: " + context.getAccessibleName());
        System.out.println("Description: " + context.getAccessibleDescription());
        System.out.println("Role: " + context.getAccessibleRole());
        System.out.println("State: " + context.getAccessibleStateSet());
        System.out.println("Children Count: " + context.getAccessibleChildrenCount());
        System.out.println();
    }

    /**
     * 创建具有完整可访问性支持的示例界面
     */
    public static JPanel createAccessibleDemoPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // 创建各种组件并设置可访问性
        JButton button = new JButton("Submit");
        makeComponentAccessible(button, "Submit Button",
                "Click to submit the form", "Submit form data");

        JTextField textField = new JTextField(15);
        makeComponentAccessible(textField, "Name Input Field",
                "Enter your name here", "Type your name");

        JCheckBox checkBox = new JCheckBox("I agree to terms");
        makeComponentAccessible(checkBox, "Agreement Checkbox",
                "Check to agree to terms and conditions",
                "Accept terms and conditions");

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        makeComponentAccessible(comboBox, "Selection Dropdown",
                "Choose an option from the list", "Select an option");

        JSlider slider = new JSlider(0, 100, 50);
        makeComponentAccessible(slider, "Value Slider",
                "Adjust the value using the slider", "Slide to adjust value");

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(75);
        makeComponentAccessible(progressBar, "Progress Indicator",
                "Shows the current progress", "Progress display");

        panel.add(button);
        panel.add(textField);
        panel.add(checkBox);
        panel.add(comboBox);
        panel.add(slider);
        panel.add(progressBar);

        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Accessibility Utils Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel demoPanel = createAccessibleDemoPanel();
        frame.add(demoPanel);

        // 打印所有组件的可访问性信息
        makeContainerAccessible(demoPanel);
        printAccessibilityInfo(demoPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
