package slx.window.common.util;

/**
 * 通用工具类
 */
public class CommonUtil {
    /**
     * 是否为空字符串 当字符串为null或empty时返回false
     * @param s 字符串
     * @return 结果 true:是 false:否
     */
    public static boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }

    
}
