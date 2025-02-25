package slx.window.model;

import javax.swing.*;
import java.awt.*;

public class SlxTextField implements SlxComponent{

    private final JTextField textField;

    public SlxTextField(int cols) {
        textField = new JTextField(cols);
    }

    @Override
    public Component getComponent() {
        return textField;
    }

    @Override
    public void display(boolean display) {
        this.textField.setVisible(display);
    }

    public String getText(){
        return textField.getText();
    }

    public void setText(String text){
        textField.setText(text);
    }

    public void setFont(Font font){
        textField.setFont(font);
    }

    public void setFontSize(int size){
        Font curFont = textField.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        textField.setFont(setFont);
    }
}
