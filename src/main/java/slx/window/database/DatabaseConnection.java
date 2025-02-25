package slx.window.database;

import slx.window.annotation.db.DBConnect;
import slx.window.common.SlxConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接
 */
public class DatabaseConnection {
    // 数据库连接实例
    private static Connection connection;

    /**
     * 初始化数据库连接
     * @param connect 注解
     * @throws ClassNotFoundException ex
     * @throws SQLException ex
     */
    public static void initConnection(DBConnect connect) throws ClassNotFoundException, SQLException {
        Class.forName(SlxConstant.DB_DRIVER_POSTGRES);
        StringBuilder url = new StringBuilder("jdbc:");
        if (connect.driver().equals("org.postgresql.Driver")){
            url.append("postgresql://")
                    .append(connect.host())
                    .append(":")
                    .append(connect.port())
                    .append("/")
                    .append(connect.name());

        }
        connection =  DriverManager.getConnection(url.toString(), connect.user(), connect.password());
    }

    /**
     * 获取数据库连接
     * @return 数据库连接
     */
    public static Connection getConnection(){
        return connection;
    }
}
