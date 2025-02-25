package slx.window.model;

import javax.swing.*;
import java.awt.*;

public class SlxTextArea implements SlxComponent{

    JTextArea textArea;

    JScrollPane scrollPane;

    public SlxTextArea(int rowCount, int columnCount){
        textArea = new JTextArea(rowCount, columnCount);
        scrollPane = new JScrollPane(textArea);
        init();
    }
    public SlxTextArea(){
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        init();
    }

    private void init(){
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);
        textArea.setEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    @Override
    public Component getComponent() {
        return this.scrollPane;
    }

    @Override
    public void display(boolean display) {
        this.textArea.setVisible(display);
    }

    @Override
    public void setFont(Font font) {
        this.textArea.setFont(font);
    }

    @Override
    public void setFontSize(int size) {
        Font curFont =  this.textArea.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        setFont(setFont);
    }

    public void setColor(Color color){
        textArea.setBackground(color);
    }

    public void setFontColor(Color color){
        textArea.setForeground(color);
    }

    public void setText(String text){
        textArea.setText(text);
    }

    public String getText(){
        return textArea.getText();
    }
}
