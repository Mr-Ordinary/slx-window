package slx.window.ex;

/**
 * SLX读取异常
 */
public class SlxReadException extends SlxException{

    /**
     * 构造器
     * @param message 异常消息
     */
    public SlxReadException(String message) {
        super(message);
    }

    /**
     * 构造器
     * @param cause 包含异常
     */
    public SlxReadException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造器
     * @param message 异常消息
     * @param cause 包含异常
     */
    public SlxReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
