package com.honeycomb.springboot.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author maoliang
 * @version 1.0.0
 */
//@Configuration
public class XxlJobConfig {

    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses("http://test0-picus-xxl-job.ifeng.com");
        xxlJobSpringExecutor.setAppname("picus-nacos-test");
        xxlJobSpringExecutor.setAddress(null);
//        xxlJobSpringExecutor.setIp("172.31.156.175");
        xxlJobSpringExecutor.setPort(9999);
        xxlJobSpringExecutor.setAccessToken("BA385E37B6F28D81AD12BDC900E4E765");
        xxlJobSpringExecutor.setLogPath(System.getProperty("user.dir") + "/xxlJob/log");
        xxlJobSpringExecutor.setLogRetentionDays(1);

        return xxlJobSpringExecutor;
    }
}
