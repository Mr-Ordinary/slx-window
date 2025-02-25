package slx.window.model;

import javax.swing.*;

public class SlxRadioGroup {
    private ButtonGroup buttonGroup;
    public static void addAll(SlxRadio... radios){
        ButtonGroup group = new ButtonGroup();
        for (SlxRadio radio : radios) {
            group.add((JRadioButton)radio.getComponent());
        }
    }
}
