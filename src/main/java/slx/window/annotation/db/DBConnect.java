package slx.window.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库连接
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DBConnect {
    public String driver() default "";
    public String host() default "";
    public String port() default "";
    public String name() default "";
    public String user() default "";
    public String password() default "";
}
