package slx.window.model;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.util.UUID;

public class SlxTextPane implements SlxComponent {

    JTextPane textArea;

    JScrollPane scrollPane;


    StyledDocument doc;

    public SlxTextPane() {
        textArea = new JTextPane();
        scrollPane = new JScrollPane(textArea);
        init();
    }

    private void init() {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        doc = textArea.getStyledDocument();
    }

    @Override
    public Component getComponent() {
        return this.scrollPane;
    }

    @Override
    public void display(boolean display) {
        this.textArea.setVisible(display);
    }

    @Override
    public void setFont(Font font) {
        this.textArea.setFont(font);
    }

    @Override
    public void setFontSize(int size) {
        Font curFont = this.textArea.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        setFont(setFont);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public String getText() {
        return textArea.getText();
    }

    public void setFontColor(Color color) {
        textArea.setForeground(color);
    }

    public void setFontColor(int offset, int length, Color color) {
        Style style = textArea.addStyle(UUID.randomUUID().toString(), null);
        StyleConstants.setForeground(style, color);
        doc.setCharacterAttributes(offset, length, style, false);
    }

    public void addLine(String text, StyleText... styles) {
        try { // 追加新行文字
            int offset = doc.getLength();
            if (offset==0){
                doc.insertString(offset, text, null);
            }else{
                doc.insertString(offset, "\n" + text, null);
            }

            // 设置新行文字的样式
            for (StyleText style : styles) {
                Style s = textArea.addStyle(UUID.randomUUID().toString(), null);
                StyleConstants.setForeground(s,style.getColor());
                doc.setCharacterAttributes(offset+style.getOffset(), style.getLength(), s, false);
            }

            textArea.setCaretPosition(textArea.getDocument().getLength());
            textArea.scrollRectToVisible(new Rectangle(textArea.getVisibleRect().x, textArea.getVisibleRect().y, 1, textArea.getHeight()));
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void enable(boolean enable) {
        textArea.setEnabled(enable);
    }

    public void editable(boolean editable) {
        textArea.setEditable(editable);
    }



}




