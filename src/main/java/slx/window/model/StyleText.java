package slx.window.model;

import javax.swing.text.Style;
import java.awt.*;

public class StyleText {
    private Color color;
    private int offset;
    private int length;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static StyleBuilder styleBuilder() {
        return new StyleBuilder();
    }
}
