package slx.window.model;

import javax.swing.*;
import java.awt.*;

public class SlxHtmlEditor implements SlxComponent{

    JEditorPane editorPane;

    public SlxHtmlEditor() {
        this.editorPane = new JEditorPane();
        this.editorPane.setContentType("text/html");
        this.editorPane.setText("<html><body></body></html>");
    }

    public void setText(String text) {
        this.editorPane.setText(text);
    }

    @Override
    public Component getComponent() {
        return editorPane;
    }

    @Override
    public void display(boolean display) {
        editorPane.setVisible(display);
    }

    @Override
    public void setFont(Font font) {
        editorPane.setFont(font);
    }

    @Override
    public void setFontSize(int size) {
        Font curFont = editorPane.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        editorPane.setFont(setFont);
    }

    public void editable(boolean editable) {
        editorPane.setEditable(editable);
    }


}
