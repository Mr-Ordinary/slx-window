package slx.window.model;

import javax.swing.*;
import java.awt.*;

/**
 * 标签
 */
public class SlxLabel implements SlxComponent{

    public String content;

    private final JLabel label;

    public SlxLabel(String content) {
        this.content = content;
        label = new JLabel(content);
    }

    @Override
    public JLabel getComponent() {
        return label;
    }

    @Override
    public void display(boolean display) {
        this.label.setVisible(display);
    }

    public void setColor(Color color){
        label.setBackground(color);
    }

    public void setText(String text){
        label.setText(text);
    }

    public void setFont(Font font){
        label.setFont(font);
    }

    public void setFontSize(int size){
        Font curFont = label.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        label.setFont(setFont);
    }
}
