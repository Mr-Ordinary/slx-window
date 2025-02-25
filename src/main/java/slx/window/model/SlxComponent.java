package slx.window.model;

import java.awt.*;

public interface SlxComponent {
    public Component getComponent();

    public void display(boolean display);

    public void setFont(Font font);

    public void setFontSize(int size);
}
