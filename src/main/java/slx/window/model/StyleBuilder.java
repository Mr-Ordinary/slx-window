package slx.window.model;

import javax.swing.text.Style;
import java.awt.*;

public class StyleBuilder{
    private int offset;
    private int length;
    private Color color;

    public StyleBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public StyleBuilder length(int length) {
        this.length = length;
        return this;
    }

    public StyleBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public StyleText build() {
        StyleText styleText = new StyleText();
        styleText.setOffset(offset);
        styleText.setLength(length);
        styleText.setColor(color);
        return styleText;
    }
}
