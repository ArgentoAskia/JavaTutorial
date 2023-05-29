package cn.argentoaskia.awt.events;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.awt.im.InputMethodRequests;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import sun.awt.InputMethodSupport;
/**
 *
 * Copyright 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class TextCanvas extends Canvas implements KeyListener, FocusListener,
        InputMethodListener, InputMethodRequests {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // 空的字符信息迭代器
    private static final AttributedCharacterIterator EMPTY_TEXT = (new AttributedString(
            "")).getIterator();
    // 空的输入法信息
    private static final Attribute[] IM_ATTRIBUTES = { TextAttribute.INPUT_METHOD_HIGHLIGHT };
    // 判定当前组件是否具有焦点
    private transient boolean haveFocus;
    // 用户已输入数据缓存
    private StringBuffer messageText = new StringBuffer();
    // 文本布局器，用以限定输入后显示的具体位置
    private transient TextLayout textLayout = null;
    // 判定是否验证输入法布局器
    private transient boolean validTextLayout = false;
    // 文本显示位置的坐标修正值
    private static final int textOffset = 5;
    // 已输入文本定位器
    private Point textOrigin = new Point(0, 0);
    // 已输入信息的字符串构成数据
    private AttributedString composedTextString;
    // 已输入信息的字符构成数据
    private AttributedCharacterIterator composedText;
    // 插入符定位器，用以记载游标所在位置
    private TextHitInfo caret = null;
    /**
     * 构造一个TextCanvas，用以通过纯绘制的方式输入文本信息
     *
     */
    public TextCanvas() {
        super();
        this.setForeground(Color.black);
        this.setBackground(Color.white);
        this.setFontSize(12);
        this.setVisible(true);
        this.setEnabled(true);
        this.addInputMethodListener(this);
        this.addKeyListener(this);
        this.addFocusListener(this);
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            boolean shouldEnable = false;
            // 验证当前环境是否支持输入法调用
            if (toolkit instanceof InputMethodSupport) {
                shouldEnable = ((InputMethodSupport) toolkit)
                        .enableInputMethodsForTextComponent();
            }
            enableInputMethods(shouldEnable);
        } catch (Exception e) {
        }
    }
    /**
     * 重载Component的输入法调用接口，因为此类为InputMethodRequests的实现，所以返回this即可。
     */
    public InputMethodRequests getInputMethodRequests() {
        return this;
    }
    /**
     * 调正当前显示的字体大小
     *
     * @param size
     */
    public void setFontSize(int size) {
        setFont(new Font("Dialog", Font.PLAIN, size));
        textOrigin.x = 5;
        textOrigin.y = (textOffset + size);
        if (composedTextString != null) {
            composedTextString.addAttribute(TextAttribute.FONT, getFont());
        }
    }
    /**
     * 返回用于显示的输入信息集
     *
     * @return
     */
    public AttributedCharacterIterator getDisplayText() {
        if (composedText == null) {
            return getDisplayTextToAttributedCharacterIterator();
        } else {
            return EMPTY_TEXT;
        }
    }
    /**
     * 返回当前插入符所在位置
     *
     * @return
     */
    public TextHitInfo getCaret() {
        if (composedText == null) {
            return TextHitInfo.trailing(messageText.length() - 1);
        } else if (caret == null) {
            return null;
        } else {
            return caret.getOffsetHit(getCommittedTextLength());
        }
    }
    /**
     * 触发输入法变更事件
     */
    public void inputMethodTextChanged(InputMethodEvent e) {
        System.out.println("123");
        int committedCharacterCount = e.getCommittedCharacterCount();
        AttributedCharacterIterator text = e.getText();
        composedText = null;
        char c;
        if (text != null) {
            // 需要复制的字符长度
            int toCopy = committedCharacterCount;
            c = text.first();
            while (toCopy-- > 0) {
                insertCharacter(c);
                c = text.next();
            }
            if (text.getEndIndex()
                    - (text.getBeginIndex() + committedCharacterCount) > 0) {
                composedTextString = new AttributedString(text, text
                        .getBeginIndex()
                        + committedCharacterCount, text.getEndIndex(),
                        IM_ATTRIBUTES);
                composedTextString.addAttribute(TextAttribute.FONT, getFont());
                composedText = composedTextString.getIterator();
            }
        }
        e.consume();
        invalidateTextLayout();
        caret = e.getCaret();
        repaint();
    }
    /**
     * 修改插入符所在位置
     */
    public void caretPositionChanged(InputMethodEvent event) {
        caret = event.getCaret();
        event.consume();
        repaint();
    }
    /**
     * 获得指定定位符对应的文本显示位置
     */
    public Rectangle getTextLocation(TextHitInfo offset) {
        Rectangle rectangle;
        if (offset == null) {
            rectangle = getCaretRectangle();
        } else {
            TextHitInfo globalOffset = offset
                    .getOffsetHit(getCommittedTextLength());
            rectangle = getCaretRectangle(globalOffset);
        }
        Point location = getLocationOnScreen();
        rectangle.translate(location.x, location.y);
        return rectangle;
    }
    /**
     * 获得偏移指定坐标的插入符信息
     */
    public TextHitInfo getLocationOffset(int x, int y) {
        Point location = getLocationOnScreen();
        Point textOrigin = getTextOrigin();
        x -= location.x + textOrigin.x;
        y -= location.y + textOrigin.y;
        TextLayout textLayout = getTextLayout();
        if (textLayout != null && textLayout.getBounds().contains(x, y)) {
            return textLayout.hitTestChar(x, y).getOffsetHit(
                    -getCommittedTextLength());
        } else {
            return null;
        }
    }
    public int getInsertPositionOffset() {
        return getCommittedTextLength();
    }
    /**
     * 返回指定范围内的字符信息迭代器
     */
    public AttributedCharacterIterator getCommittedText(int beginIndex,
                                                        int endIndex, Attribute[] attributes) {
        return getMessageText(beginIndex, endIndex);
    }
    public AttributedCharacterIterator cancelLatestCommittedText(
            Attribute[] attributes) {

        return null;
    }
    public AttributedCharacterIterator getSelectedText(Attribute[] attributes) {
        return EMPTY_TEXT;
    }
    public synchronized void update(Graphics g) {
        paint(g);
    }
    /**
     * 绘制目标界面
     */
    public synchronized void paint(Graphics g) {
        g.setColor(getBackground());
        Dimension size = getSize();
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.black);
        g.drawRect(0, 0, size.width - 1, size.height - 1);
        if (haveFocus) {
            g.drawRect(1, 1, size.width - 3, size.height - 3);
        }
        g.setColor(getForeground());
        TextLayout textLayout = getTextLayout();
        if (textLayout != null) {
            textLayout.draw((Graphics2D) g, textOrigin.x, textOrigin.y);
        }
        Rectangle rectangle = getCaretRectangle();
        if (haveFocus && rectangle != null) {
            g.setXORMode(getBackground());
            g.fillRect(rectangle.x, rectangle.y, 1, rectangle.height);
            g.setPaintMode();
        }
    }
    /**
     * 将messageText转化为指定范围内的字符信息迭代器
     *
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public AttributedCharacterIterator getMessageText(int beginIndex,
                                                      int endIndex) {
        AttributedString string = new AttributedString(messageText.toString());
        return string.getIterator(null, beginIndex, endIndex);
    }
    /**
     * 返回已输入的字符串信息长度
     */
    public int getCommittedTextLength() {
        return messageText.length();
    }
    public AttributedCharacterIterator getDisplayTextToAttributedCharacterIterator() {
        AttributedString string = new AttributedString(messageText.toString());
        if (messageText.length() > 0) {
            string.addAttribute(TextAttribute.FONT, getFont());
        }
        return string.getIterator();
    }
    /**
     * 返回当前文本布局器
     *
     * @return
     */
    public synchronized TextLayout getTextLayout() {
        if (!validTextLayout) {
            textLayout = null;
            AttributedCharacterIterator text = getDisplayText();
            if (text.getEndIndex() > text.getBeginIndex()) {
                FontRenderContext context = ((Graphics2D) getGraphics())
                        .getFontRenderContext();
                textLayout = new TextLayout(text, context);
            }
        }
        validTextLayout = true;
        return textLayout;
    }
    /**
     * 强制文本布局器验证无效化
     *
     */
    public synchronized void invalidateTextLayout() {
        validTextLayout = false;
    }
    /**
     * 返回文本绘制点
     *
     * @return
     */
    public Point getTextOrigin() {
        return textOrigin;
    }
    /**
     * 返回对应插入点的矩形选框
     *
     * @return
     */
    public Rectangle getCaretRectangle() {
        TextHitInfo caret = getCaret();
        if (caret == null) {
            return null;
        }
        return getCaretRectangle(caret);
    }
    /**
     * 返回对应插入点的矩形选框
     *
     * @param caret
     * @return
     */
    public Rectangle getCaretRectangle(TextHitInfo caret) {
        TextLayout textLayout = getTextLayout();
        int caretLocation;
        if (textLayout != null) {
            caretLocation = Math.round(textLayout.getCaretInfo(caret)[0]);
        } else {
            caretLocation = 0;
        }
        FontMetrics metrics = getGraphics().getFontMetrics();
        return new Rectangle(textOrigin.x + caretLocation, textOrigin.y
                - metrics.getAscent(), 0, metrics.getAscent()
                + metrics.getDescent());
    }
    /**
     * 插入指定字符串
     *
     * @param c
     */
    public void insertCharacter(char c) {
        messageText.append(c);
        invalidateTextLayout();
    }
    /**
     * 用户输入
     */
    public void keyTyped(KeyEvent event) {
        char keyChar = event.getKeyChar();
        // 处理文字删除
        if (keyChar == '\b') {
            int len = messageText.length();
            if (len > 0) {
                messageText.setLength(len - 1);
                invalidateTextLayout();
            }
        } else {
            insertCharacter(keyChar);
        }
        event.consume();
        repaint();
    }
    public void keyPressed(KeyEvent event) {
    }
    public void keyReleased(KeyEvent event) {
    }
    public void focusGained(FocusEvent event) {
        haveFocus = true;
        repaint();
    }
    public void focusLost(FocusEvent event) {
        haveFocus = false;
        repaint();
    }
    public static void main(String[] args) {
        Frame frame = new Frame("绘制一个输入框");
        TextCanvas text = new TextCanvas();
        frame.add(text);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

