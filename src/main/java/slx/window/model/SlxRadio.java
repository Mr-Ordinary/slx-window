package slx.window.model;

import javax.swing.*;
import java.awt.*;

public class SlxRadio implements SlxComponent{

    private JRadioButton radioButton;

    public SlxRadio (String contents){
        radioButton = new JRadioButton(contents);
    }

    @Override
    public Component getComponent() {
        return radioButton;
    }

    @Override
    public void display(boolean display) {
        radioButton.setVisible(display);
    }

    public boolean isSelected(){
        return radioButton.isSelected();
    }

    public void setSelected(boolean selected){
        radioButton.setSelected(selected);
    }

    public void setFont(Font font){
        radioButton.setFont(font);
    }

    public void setFontSize(int size){
        Font curFont = radioButton.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        radioButton.setFont(setFont);
    }
}
