package slx.window.model;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SlxSelector<T> implements SlxComponent{

    JComboBox<T> jComboBox = null;

    public SlxSelector(T[] items) {
        this.jComboBox = new JComboBox<T>(items);
    }

    public T getSelected() {
        return (T) jComboBox.getSelectedItem();
    }

    @Override
    public Component getComponent() {
        return this.jComboBox;
    }

    @Override
    public void display(boolean display) {
        this.jComboBox.setVisible(display);
    }

    public void onChange(ActionListener listener){
        this.jComboBox.addActionListener(listener);
    }

    public void setFont(Font font){
        jComboBox.setFont(font);
    }

    public void setFontSize(int size){
        Font curFont = jComboBox.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        jComboBox.setFont(setFont);
    }

    public void setItems(T[] items){
        this.jComboBox.setModel(new DefaultComboBoxModel<>(items));
    }
}
