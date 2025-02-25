package slx.window.main;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import slx.window.annotation.db.DBConnect;
import slx.window.annotation.field.Assemble;
import slx.window.annotation.method.SlxDrawEnd;
import slx.window.annotation.method.SlxInit;
import slx.window.annotation.type.*;
import slx.window.annotation.type.LookAndFeel;
import slx.window.database.DatabaseConnection;
import slx.window.ex.SlxException;
import slx.window.win.SlxWindow;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 开始:程序入口
 */
public class SlxWindowStarter {


    /**
     * 获取主类
     * @return 主类
     */
    private static Class<?> getMainClass(){
        //找到主类
        Reflections configReflections = new Reflections(new ConfigurationBuilder()
                .forPackage("") // 根包名
                .addScanners(Scanners.TypesAnnotated)
        );
        Set<Class<?>> mainClasses = configReflections.getTypesAnnotatedWith(SlxWindowMain.class);

        if (mainClasses.size()>1){
            throw new SlxException("There can only be one main class");
        }
        if (mainClasses.isEmpty()){
            throw new SlxException("Cannot find main class");
        }
        for (Class<?> mainClass : mainClasses) {
            return mainClass;
        }
        throw new SlxException("Cannot find main class");
    }

    /**
     * 获取 base package
     * @return base package
     */
    private static String getBasePackage(){
        Class<?> mainClass = getMainClass();
        SlxWindowMain annotation = mainClass.getAnnotation(SlxWindowMain.class);
        String basePackage = annotation.basePackage();
        if (basePackage==null||basePackage.isEmpty()){
            throw new SlxException("No base package");
        }
        return basePackage;
    }

    /**
     * 构建容器
     * @throws InstantiationException ex
     * @throws IllegalAccessException ex
     */
    private static void buildingContainers() throws InstantiationException, IllegalAccessException {
        String basePackage = getBasePackage();
        // 扫描指定包中的所有类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage(basePackage)
                .addScanners(Scanners.TypesAnnotated)
        );

        // 获取所有带有 SlxElement 注解的类
        Set<Class<?>> elementClasses = new HashSet<>();
        Set<Class<?>> configClasses = reflections.getTypesAnnotatedWith(SlxConfiguration.class);
        Set<Class<?>> rendererClasses = reflections.getTypesAnnotatedWith(SlxWindowRenderer.class);
        Set<Class<?>> getSlxElementClasses = reflections.getTypesAnnotatedWith(SlxElement.class);

        elementClasses.addAll(configClasses);
        elementClasses.addAll(rendererClasses);
        elementClasses.addAll(getSlxElementClasses);
        for (Class<?> elementClass : elementClasses) {
            // 创建对象至容器
            Object obj = elementClass.newInstance();
            SlxContext.addElement(elementClass,obj);
        }

        // 为容器中的对象装配依赖
        for (Map.Entry<Class<?>, Object> e : SlxContext.getElements().entrySet()) {
            Class<?> clazz = e.getKey();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Assemble.class)) {
                    Class<?> type = field.getType();
                    Object element = SlxContext.getElement(type);
                    if (element==null){
                        throw new SlxException("Class "+clazz.getName()+" cannot find the object that needs to be assembled ("+type.getName()+")");
                    }
                    field.setAccessible(true);
                    field.set(e.getValue(),element);
                }
            }
        }
    }


    /**
     * 构建数据库连接
     */
    private static void buildDatabaseConn(){
        String basePackage = getBasePackage();
        // 扫描指定包中的所有类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage(basePackage)
                .addScanners(Scanners.TypesAnnotated)
        );
        Set<Class<?>> dbClasses = reflections.getTypesAnnotatedWith(DBConnect.class);
        if (dbClasses.size()>1){
            throw new SlxException("There can only be one db connect");
        }
        for (Class<?> dbClass : dbClasses) {
            try {
                DatabaseConnection.initConnection(dbClass.getAnnotation(DBConnect.class));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * master  开始
     * @throws InstantiationException ex
     * @throws IllegalAccessException ex
     * @throws InvocationTargetException ex
     */
    public static void start() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        // build containers
        buildingContainers();
        // build dbConnect
        buildDatabaseConn();

        String basePackage = getBasePackage();
        // all class in base package
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage(basePackage)
                .addScanners(Scanners.TypesAnnotated)
        );

        //look and feel
        setUI(reflections);

        for (Map.Entry<Class<?>, Object> e : SlxContext.getElements().entrySet()) {
            Class<?> clazz = e.getKey();
            Object obj = e.getValue();
            Method[] methods = clazz.getDeclaredMethods();
            // 1 初始化器
            for (Method method : methods) {
                if (method.isAnnotationPresent(SlxInit.class)){
                    method.setAccessible(true);
                    method.invoke(obj);
                }
            }
            //
        }

        // end 找到窗口绘制端  绘制窗口
        openWindow(reflections);

    }

    /**
     * 设置窗口UI
     * @param reflections base package下的所有类
     */
    private static void setUI(Reflections reflections){
        Set<Class<?>> lookAndFeels = reflections.getTypesAnnotatedWith(LookAndFeel.class);
        if (lookAndFeels.size()>1){
            throw new SlxException("There can only be one look and feel class");
        }
        for (Class<?> lookAndFeelClass : lookAndFeels) {
            LookAndFeel annotation = lookAndFeelClass.getAnnotation(LookAndFeel.class);
            String s = annotation.lookAndFeel();
            try {
                UIManager.setLookAndFeel(s);
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 打开窗口
     * @param reflections base package下的所有类
     * @throws InvocationTargetException ex
     * @throws IllegalAccessException ex
     */
    private static void openWindow(Reflections reflections) throws InvocationTargetException, IllegalAccessException {
        SlxWindow window = null;
        Set<Class<?>> rendererClasses = reflections.getTypesAnnotatedWith(SlxWindowRenderer.class);
        if (rendererClasses.size()>1){
            throw new SlxException("There can only be one renderer");
        }
        if (rendererClasses.isEmpty()){
            throw new SlxException("Cannot find renderer");
        }

        for (Class<?> rendererClass : rendererClasses) {
            Method[] methods = rendererClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(SlxDrawEnd.class)){
                    Class<?> returnType = method.getReturnType();
                    if (returnType.equals(SlxWindow.class)){
                        method.setAccessible(true);
                        window =  (SlxWindow) method.invoke(SlxContext.getElement(rendererClass));
                    }else {
                        throw new SlxException("The return value of the drawing end should be of type SlxWindow.class");
                    }
                }
            }
        }
        if (window==null){
            throw new SlxException("Window drawing failed");
        }
        window.start();
    }
}
