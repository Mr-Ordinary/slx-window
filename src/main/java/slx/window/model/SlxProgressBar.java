package slx.window.model;

import javax.swing.*;
import java.awt.*;

public class SlxProgressBar implements SlxComponent{

    private int min;
    private int max;
    private JProgressBar progressBar;

    public SlxProgressBar(int min, int max){
        this.min = min;
        this.max = max;
    }


    @Override
    public Component getComponent() {
        progressBar = new JProgressBar(min, max);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        return progressBar;
    }

    @Override
    public void display(boolean display) {
        this.progressBar.setVisible(display);
    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public void setFontSize(int size) {

    }

    public void setValue(int value){
        progressBar.setValue(value);
    }
}
