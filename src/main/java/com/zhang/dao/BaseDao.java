package com.zhang.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Collection;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    //静态代码快，类加载的时候就初始化了
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }
    //获取数据库链接
    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(driver);
          connection=DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    //编写查询公共类
    public static ResultSet execute(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet,String sql,Object[] params) throws SQLException {
        //预编译sql，在后面直接执行
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i+1,params[i]);
        }
        resultSet= preparedStatement.executeQuery();
        return resultSet;
    }
    //编写增删改公共类
    public static int executes(Connection connection,PreparedStatement preparedStatement,String sql,Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i+1,params[i]);
        }
        int updateRows= preparedStatement.executeUpdate();
        return updateRows;
    }
    //释放资源
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag = true;
        if (resultSet!=null){
            try {
                resultSet.close();
                //垃圾回收
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
                //垃圾回收
                preparedStatement = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (connection!=null){
            try {
                connection.close();
                //垃圾回收
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
