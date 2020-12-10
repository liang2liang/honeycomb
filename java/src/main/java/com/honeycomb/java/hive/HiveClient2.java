package com.honeycomb.java.hive;

import java.sql.*;
import java.util.Optional;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class HiveClient2 {

    private static final String driver = "org.apache.hive.jdbc.HiveDriver";

    private static final String createTableSql = "create table picus.test\n" +
            "(\n" +
            "  user_id                      varchar(255) comment '域账号',\n" +
            "  guid                         varchar(255) comment 'guid',\n" +
            "  cat_name array< string > comment '分类',\n" +
            "  long_time_expire_time        TIMESTAMP comment '时效期',\n" +
            "  tags_error                   tinyint comment '标签是否错误'\n" +
            ")\n" +
            "comment 'DC统计分析表' PARTITIONED BY (year int, month int, day int)\n" +
            "row format delimited fields terminated by '\\t'\n" +
            "COLLECTION ITEMS TERMINATED BY ','\n" +
            "STORED AS TEXTFILE";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:hive2://10.61.224.37:7001/picus;user=picus"); Statement statement = connection.createStatement()) {
            String sql = "select * from picus.test";
            System.out.println("ok");
        }
    }

    public static void main1(String[] args) throws SQLException, ClassNotFoundException {

        Connection connection = DriverManager.getConnection("jdbc:hive2://10.90.7.185:10000/picus");
        Statement statement = connection.createStatement();
//        删除表
//        boolean result = statement.execute("drop table test");
//        创建表
//        boolean result = statement.execute(createTableSql);
        try {
//            String sql = "insert into picus.test partition(year=2020,month=9,day=25) select 'honeycomb','123456', array('社会事件','新型冠状病毒','特朗普','军事新闻资讯'), 1601016552000, 0 from (select 'a') x";
//            String sql = "insert into picus.test partition(year=2020,month=9,day=25) select 'honeycomb','123456' from (select 'a') x";
//            int i = statement.executeUpdate(sql);
//            System.out.println(i);
            String sql = "select * from picus.test";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                for (int i = 1; i < 6; i++) {
                    Object object = resultSet.getObject(i);
                    System.out.println(i + ":" + object + " type is : " + Optional.ofNullable(object).map(Object::getClass).orElse(null));
                }
                System.out.println("");
            }
            System.out.println("ok");
        } finally {
            connection.close();
        }
    }

    public static void main3(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:hive2://10.61.240.46:39653/picus");
        try {
            String sql = "insert into picus.test partition(year=2020,month=9,day=25) select ?,?,array(?),?,? from (select 'a') x";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "honeycomb2");
            preparedStatement.setString(2, "456567");
            preparedStatement.setString(3, "5677,12312");
            preparedStatement.setLong(4, 1601016752000L);
            preparedStatement.setInt(5, 1);
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        } finally {

            connection.close();
        }
    }
}
