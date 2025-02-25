package slx.window.model;

import javax.swing.*;
import java.awt.*;

public class SlxDivide implements SlxComponent{

    public static final int HORIZONTAL = 0;

    public static final int VERTICAL   = 1;

    JSeparator jSeparator = null;

    public SlxDivide(int type) {
        this.jSeparator  = new JSeparator(type);
    }


    @Override
    public Component getComponent() {
        return this.jSeparator;
    }

    @Override
    public void display(boolean display) {
        this.jSeparator.setVisible(display);
    }

    @Override
    public void setFont(Font font) {
    }

    @Override
    public void setFontSize(int size) {
    }
}
