package slx.window.ex;


/**
 * SLX异常
 */
public class SlxException extends RuntimeException{
    /**
     * 构造器
     * @param message 异常消息
     */
    public SlxException(String message) {
        super(message);
    }

    /**
     * 构造器
     * @param cause 包含异常
     */
    public SlxException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造器
     * @param message 异常消息
     * @param cause 包含异常
     */
    public SlxException(String message, Throwable cause) {
        super(message, cause);
    }
}
