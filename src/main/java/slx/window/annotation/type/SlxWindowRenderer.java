package slx.window.annotation.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 窗口渲染器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SlxElement
public @interface SlxWindowRenderer {

}
