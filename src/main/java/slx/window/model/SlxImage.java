package slx.window.model;

import slx.window.ex.SlxException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SlxImage implements SlxComponent{

    private JLabel label;


    public SlxImage(String path) {
        if (!new File(path).exists()){
            throw new SlxException("file not exists:"+path);
        }
        ImageIcon imageIcon = new ImageIcon(path);

        label = new JLabel("", imageIcon, SwingConstants.LEFT);
    }

    public SlxImage(java.net.URL url){
        ImageIcon imageIcon = new ImageIcon(url);
        label = new JLabel("", imageIcon, SwingConstants.LEFT);
    }
    @Override
    public Component getComponent() {
        return label;
    }

    @Override
    public void display(boolean display) {
        label.setVisible(display);
    }

    @Override
    public void setFont(Font font) {
        label.setFont(font);
    }

    @Override
    public void setFontSize(int size) {
        Font curFont = label.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        label.setFont(setFont);
    }
}
