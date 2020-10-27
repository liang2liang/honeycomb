package com.honeycomb.java.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class HiveClient {

    private static final String driver = "org.apache.hive.jdbc.HiveDriver";

    private static final String createTableSql = "create table picus.repeat_check_statistics_analysis\n" +
            "(\n" +
            "  user_id                      varchar(255) comment '域账号',\n" +
            "  guid                         varchar(255) comment 'guid',\n" +
            "  business_id                  varchar(255) comment '业务Id',\n" +
            "  content_id                   varchar(255) comment '文章ID',\n" +
            "  region                       varchar(255) comment '分区',\n" +
            "  title string comment '标题',\n" +
            "  bucket                       varchar(255) comment '桶名',\n" +
            "  storage_time                 TIMESTAMP comment 'dc入库时间',\n" +
            "  dc_type                      varchar(255) comment 'dcType（dc一致、dc不一致）',\n" +
            "  status                       int comment '状态',\n" +
            "  result                       int comment '结果（1：错误，0正确）',\n" +
            "  arbiter                      varchar(255) comment '仲裁人',\n" +
            "  finish_time                  TIMESTAMP comment 'dc流程完成时间',\n" +
            "  audit_status                 tinyint comment '审核状态',\n" +
            "  result_audit_status          tinyint comment '审核状态（最终结果）',\n" +
            "  audit_status_error           tinyint comment '审核状态是否错误',\n" +
            "  cat_name array< string > comment '分类',\n" +
            "  result_cat_name array< string > comment '分类（最终结果）',\n" +
            "  cat_name_error               tinyint comment '分类状态错误数',\n" +
            "  content_quality_tag array< string > comment '特殊标记',\n" +
            "  result_content_quality_tag array< string > comment '特殊标记（最终结果）',\n" +
            "  content_quality_tag_error    tinyint comment '特殊标记是否错误',\n" +
            "  favor_data array< string > comment '兴趣包',\n" +
            "  result_favor_data array< string > comment '兴趣包（最终结果）',\n" +
            "  favor_data_error             tinyint comment '兴趣包是否错误',\n" +
            "  garbage_words string comment '标注',\n" +
            "  result_garbage_words string comment '标注（最终结果）',\n" +
            "  garbage_words_error          tinyint comment '标注是否错误',\n" +
            "  long_time                    int comment '时效',\n" +
            "  result_long_time             int comment '时效（最终结果）',\n" +
            "  long_time_error              tinyint comment '时效是否错误',\n" +
            "  long_time_short_error        tinyint comment '时效短错误',\n" +
            "  long_time_normal_error       tinyint comment '时效正常错误',\n" +
            "  long_time_long_error         tinyint comment '时效长错误',\n" +
            "  long_time_expire_time        TIMESTAMP comment '时效期',\n" +
            "  result_long_time_expire_time TIMESTAMP comment '时效期（最终结果）',\n" +
            "  question_mark string comment '标注',\n" +
            "  result_question_mark string comment '标注（最终结果）',\n" +
            "  question_mark_error string comment '标注是否错误',\n" +
            "  recommend_area array< string > comment '推荐地区',\n" +
            "  result_recommend_area array< string > comment '推荐地区（最终结果）',\n" +
            "  recommend_area_error         tinyint comment '推荐地区是否错误',\n" +
            "  recommend_people array< string > comment '推荐人群',\n" +
            "  result_recommend_people array< string > comment '推荐人群（最终结果）',\n" +
            "  recommend_people_error       tinyint comment '推荐人群是否错误',\n" +
            "  tags array< string > comment '标签',\n" +
            "  result_tags array< string > comment '标签（最终结果）',\n" +
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

        Connection connection = DriverManager.getConnection("jdbc:hive2://10.61.240.46:39653/picus");
//        Statement statement = connection.createStatement();
        //删除表
//        boolean result = statement.execute("drop table repeat_check_statistics_analysis");
        //创建表
//        boolean result = statement.execute(createTableSql);
        try {
//            String sql = "insert into repeat_check_statistics_analysis('user_id','guid','business_id','content_id','region','title','bucket','storage_time','dc_type','status','result','arbiter','finish_time','audit_status','result_audit_status','audit_status_error','cat_name','result_cat_name','cat_name_error','content_quality_tag','result_content_quality_tag','content_quality_tag_error','favor_data','result_favor_data','favor_data_error','garbage_words','result_garbage_words','garbage_words_error','long_time','result_long_time','long_time_error','long_time_short_error','long_time_normal_error','long_time_long_error','long_time_expire_time','result_long_time_expire_time','question_mark','result_question_mark','question_mark_error','recommend_area','result_recommend_area','recommend_area_error','recommend_people','result_recommend_people','recommend_people_error','tags','result_tags','tags_error') values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) partition(year=2020,month=9,day=25)";
            String sql = "insert into picus.repeat_check_statistics_analysis partition(year=2020,month=9,day=25) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,array('军事','国际军情'),array('军事','国际军情'),?,array('正常'),array('正常'),?,null,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null,?,null,null,?,array('社会事件','新型冠状病毒','特朗普','军事新闻资讯'),array('社会事件','新型冠状病毒','特朗普','军事新闻资讯'),?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "honeycomb");
            preparedStatement.setString(2, "6710086812473237357");
            preparedStatement.setString(3, "zmt_vid");
            preparedStatement.setString(4, "5ea918ea-bf4f-4e95-bba1-a07f0bf9e1be");
            preparedStatement.setString(5, "zmt_vid_qua");
            preparedStatement.setString(6, "白宫已成混乱中心，大批民众包围要说法，特朗普却下令竖起铁丝网");
            preparedStatement.setString(7, "common");
            preparedStatement.setLong(8, 1601016552000L);
            preparedStatement.setString(9, "repeat_agreement");
            preparedStatement.setInt(10, 111);
            preparedStatement.setInt(11, 0);
            preparedStatement.setString(12, "nobody");
            preparedStatement.setLong(13, 1601016552000L);
            preparedStatement.setInt(14, 1);
            preparedStatement.setInt(15, 1);
            preparedStatement.setInt(16, 0);
//            preparedStatement.setString(17, "军事,国际军情");
//            preparedStatement.setString(18, "军事,国际军情");
            preparedStatement.setInt(17, 0);
//            preparedStatement.setString(20, "正常");
//            preparedStatement.setString(21, "正常");
            preparedStatement.setInt(18, 0);
//            preparedStatement.setString(23, "");
//            preparedStatement.setString(24, "");
            preparedStatement.setInt(19, 0);
            preparedStatement.setString(20, "");
            preparedStatement.setString(21, "");
            preparedStatement.setInt(22, 0);
            preparedStatement.setInt(23, 1);
            preparedStatement.setInt(24, 1);
            preparedStatement.setInt(25, 0);
            preparedStatement.setInt(26, 0);
            preparedStatement.setInt(27, 0);
            preparedStatement.setInt(28, 0);
            preparedStatement.setInt(29, 0);
            preparedStatement.setInt(30, 0);
            preparedStatement.setString(31, "");
            preparedStatement.setString(32, "");
            preparedStatement.setInt(32, 0);
//            preparedStatement.setString(40, "");
//            preparedStatement.setString(41, "");
            preparedStatement.setInt(34, 0);
//            preparedStatement.setString(43, "");
//            preparedStatement.setString(44, "");
            preparedStatement.setInt(35, 0);
//            preparedStatement.setString(46, "社会事件,新型冠状病毒,特朗普,军事新闻资讯");
//            preparedStatement.setString(47, "社会事件,新型冠状病毒,特朗普,军事新闻资讯");
            preparedStatement.setInt(36, 0);
            int result = preparedStatement.executeUpdate();
            System.out.println(result);
        } finally {
            connection.close();
        }
    }
}
