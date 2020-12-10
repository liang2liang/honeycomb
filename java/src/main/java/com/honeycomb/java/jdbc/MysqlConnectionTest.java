package com.honeycomb.java.jdbc;

import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author maoliang
 */
public class MysqlConnectionTest {

    public static void main(String[] args) throws Exception {
        testOneConn();
    }

    public static void testOneConn() throws Exception {

        System.out.print("begn");
        String cmdSql = "select * from appRule";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {

            conn = getConn();

            stmt = conn.prepareStatement(cmdSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(Integer.MIN_VALUE);
            //第一次查询（1）
            rs = stmt.executeQuery();
            final ResultSet tempRs = rs;
            Future<Integer> feture = executor.submit(new Callable<Integer>() {
                public Integer call() throws InterruptedException {
                    try {
                        while (tempRs.next()) {
                            try {
                                System.out.println(tempRs.getString("id"));
                                Thread.sleep(1000);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });

            Thread.sleep(2000);

            try {
                rs.close();
                //第二次查询（2)
                stmt.executeQuery();

            } catch (Exception e) {
                System.out.println("second search:" + e.getLocalizedMessage());

            }

            // 等待子线程执行完毕
            feture.get();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("first search:" + e.getLocalizedMessage());
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
    }

    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://10.21.7.20:23333/sisp_test";
        String username = "sisp_test";
        String password = "YYTjHn6M346fAVjb";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
