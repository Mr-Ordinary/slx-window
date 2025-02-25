package slx.window.model;

import slx.window.win.SlxWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 按钮
 */
public class SlxButton implements SlxComponent{

    // 按钮
    private final JButton button;

    /**
     * 构造器
     * @param content 按钮正文
     */
    public SlxButton(String content) {
        this.button = new JButton(content);
    }

    @Override
    public JButton getComponent() {
        return this.button;
    }

    @Override
    public void display(boolean display) {
        this.button.setVisible(display);
    }

    /**
     * 点击时间
     * @param listener 执行监听器
     */
    public void onClick(ActionListener listener){
        button.addActionListener(listener);
    }

    /**
     * 获取文件选择按钮
     * @param content 按钮正文
     * @param window 窗口
     * @param field 文件选择input
     * @return 文件选择按钮
     */
    public static SlxButton fileChooserButton(String content, SlxWindow window,SlxTextField field){
        String currentDir = new File("").getAbsolutePath();
        JFileChooser  fileChooser = new JFileChooser(currentDir);
        SlxButton button = new SlxButton(content);
        button.onClick(e->{
            SwingUtilities.invokeLater(() -> {
                int option = fileChooser.showOpenDialog(window);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        field.setText(selectedFile.getAbsolutePath());
                    }
                }
            });
        });
        return button;
    }

    /**
     * 设置字体颜色
     * @param color 颜色
     */
    public void setColor(Color color){
        button.setBackground(color);
    }

    /**
     * 设置字体
     * @param font 字体
     */
    public void setFont(Font font){
        button.setFont(font);
    }

    /**
     * 设置字号
     * @param size 字号
     */
    public void setFontSize(int size){
        Font curFont = button.getFont();
        Font setFont = new Font(curFont.getName(), curFont.getStyle(), size);
        button.setFont(setFont);
    }
}
