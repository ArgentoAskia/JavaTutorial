package cn.argentoaskia.awt.widgets.apis.component.beans;

import javax.swing.*;
import java.awt.*;

public class ButtonAttributesBean {
    private String actionCommand;
    private Color backGround;
    private Cursor cursor;
    private boolean enabled;
    private Font font;
    private Color foreground;
    private String text;
    private String name;
    private boolean visible;
    private Dimension preferredSize;
    private String toolTipText;
    private Icon icon;

    @Override
    public String toString() {
        return "ButtonAttributesBean{" +
                "actionCommand='" + actionCommand + '\'' +
                ", backGround=" + backGround +
                ", cursor=" + cursor +
                ", enabled=" + enabled +
                ", font=" + font +
                ", foreground=" + foreground +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                ", visible=" + visible +
                ", preferredSize=" + preferredSize +
                ", toolTipText='" + toolTipText + '\'' +
                ", icon=" + icon +
                '}';
    }

    public String getActionCommand() {
        return actionCommand;
    }

    public ButtonAttributesBean setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
        return this;
    }

    public Color getBackGround() {
        return backGround;
    }

    public ButtonAttributesBean setBackGround(Color backGround) {
        this.backGround = backGround;
        return this;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public ButtonAttributesBean setCursor(Cursor cursor) {
        this.cursor = cursor;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ButtonAttributesBean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Font getFont() {
        return font;
    }

    public ButtonAttributesBean setFont(Font font) {
        this.font = font;
        return this;
    }

    public Color getForeground() {
        return foreground;
    }

    public ButtonAttributesBean setForeground(Color foreground) {
        this.foreground = foreground;
        return this;
    }

    public String getText() {
        return text;
    }

    public ButtonAttributesBean setText(String text) {
        this.text = text;
        return this;
    }

    public String getName() {
        return name;
    }

    public ButtonAttributesBean setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public ButtonAttributesBean setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public ButtonAttributesBean setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
        return this;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public ButtonAttributesBean setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
        return this;
    }

    public Icon getIcon() {
        return icon;
    }

    public ButtonAttributesBean setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }
}
