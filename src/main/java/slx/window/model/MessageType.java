package slx.window.model;

/**
 * 消息类型:用于弹出窗口Dialog
 */
public enum MessageType {
    // 错误
    ERROR(0),
    // 信息
    INFO(1),
    // 警告
    WARNING(2),
    // 问题
    QUESTION(3);
    // 消息类型
    private final int type;

    /**
     * 构造器
     * @param type 消息类型
     */
    MessageType(int type){
        this.type = type;
    }

    /**
     * 获取消息类型
     * @return 消息类型
     */
    public int getType() {
        return type;
    }
}
