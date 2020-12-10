package com.honeycomb.java.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoopConnectionTest {

    private static Connection conn = getConn();

    public static void main(String[] args) {

        List<ResultSet> actualResultSets = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            actualResultSets.add(getAllCategory(conn));
        }


        boolean flag = true;
        int i = 0;
        while (true) {

            try {
                int index = i++;
                flag = displayResultSet(actualResultSets.get(index % 3), index % 3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!flag) {
                break;
            }
        }

    }

    private static ResultSet getAllCategory(Connection conn) {
        String sql = "select * from appRule";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
//            pstmt.setFetchSize(Integer.MIN_VALUE);
            resultSet = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {  
//            if (null!=pstmt) {
//                try {
//                    pstmt.close();//注释掉close方法是因为，一旦pstmt关闭，resultSet也会随之关闭
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return resultSet;
    }

    private static boolean displayResultSet(ResultSet rs, int index) throws SQLException {
        int col = rs.getMetaData().getColumnCount();
        System.out.println("index:" + index + "============================");
        boolean flag = rs.next();
        if (flag) {
            System.out.println(rs.getString("id"));
        }
        return flag;
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
