package slx.window.annotation.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SlxElement
public @interface SlxWindowMain {
    String basePackage() default "";
}
