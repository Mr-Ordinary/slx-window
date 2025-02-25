package slx.window.win;

import slx.window.ex.SlxException;
import slx.window.model.MessageType;
import slx.window.model.SlxComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class SlxWindow extends JFrame {

    private final SlxPanel panel = new SlxPanel();

    private boolean main = false;

    private int panelWidth;
    private int panelHeight;

    private final List<SlxComponent> components = new ArrayList<SlxComponent>();

    public SlxWindow(String title, int width, int height, boolean main) {

        if (title == null) {
            throw new NullPointerException("title is null");
        }
        if (width < 0 || height < 0) {
            throw new SlxException("width or height is less than 0,width:" + width + ",height:" + height);
        }

        this.main = main;
        this.setTitle(title);
        this.setSize(width + 16, height + 39);
        if (!main) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        initializeScrollPane();
    }

    private void initializeScrollPane() {
        panel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        this.add(scrollPane);
    }

    public void centered() {
        setLocationRelativeTo(null);
    }

    public void location(int x, int y) {
        if (x < 0 || y < 0) {
            throw new SlxException("x or y is less than 0,x:" + x + ",y:" + y);
        }
        setLocation(x, y);
    }


    public int addComponent(SlxComponent component, int x, int y, int width, int height) {
        if (x < 0 || y < 0) {
            throw new SlxException("x or y is less than 0,x:" + x + ",y:" + y);
        }
        if (width < 0 || height < 0) {
            throw new SlxException("width or height is less than 0,width:" + width + ",height:" + height);
        }
        Component componentVal = component.getComponent();
        componentVal.setBounds(x, y, width, height);
        // 更新组件的首选大小以匹配边界大小
        componentVal.setPreferredSize(new Dimension(width, height));
        panel.add(componentVal);
        resetSize(x, y, width, height);
        components.add(component);
        return components.size() - 1;
    }

    public void removeComponent(SlxComponent component) {
        panel.remove(component.getComponent());
        panel.revalidate();
        panel.repaint();
    }

    public void resetSize(int x, int y, int width, int height) {
        if (x + width > panelWidth) {
            panelWidth = x + width;
        }
        if (y + height > panelHeight) {
            panelHeight = y + height;
        }
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

    }

    public void start() {
        this.setVisible(true);
    }

    public void centerStart() {
        this.centered();
        this.start();
    }

    public void messageDialog(String title, String message, MessageType type) {
        JOptionPane.showMessageDialog(SlxWindow.this, message, title, type.getType());
    }

    public void messageDialog(String title, String message, int type) {
        JOptionPane.showMessageDialog(SlxWindow.this, message, title, type);
    }

    public boolean confirm(String title, String message, MessageType type) {
        int result = JOptionPane.showConfirmDialog(SlxWindow.this, message, title, JOptionPane.YES_NO_OPTION,
                type.getType());
        return result == JOptionPane.YES_OPTION;
    }

    public void exitConfirm(String title, String message) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        SlxWindow.this,
                        message,
                        title,
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmed == JOptionPane.YES_OPTION) {
                    if (main) {
                        System.exit(0);
                    } else {
                        dispose();
                    }
                } else if (confirmed == JOptionPane.NO_OPTION || confirmed == JOptionPane.CANCEL_OPTION || confirmed == JOptionPane.CLOSED_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    public void close() {
        dispose();
    }

    public void resizable(boolean resizable) {
        this.setResizable(resizable);
    }

    public void resetSize(int width, int height) {
        this.setSize(width, height);
    }

    public SlxComponent getElement(int idx) {
        if (idx < components.size()) {
            return components.get(idx);
        } else {
            return null;
        }
    }

    public void setAllFont(Font font) {
        for (SlxComponent component : components) {
            component.setFont(font);
        }
    }

    public void setAllFontSize(int size) {
        for (SlxComponent component : components) {
            component.setFontSize(size);
        }
    }
}
