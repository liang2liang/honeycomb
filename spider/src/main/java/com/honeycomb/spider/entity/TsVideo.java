package com.honeycomb.spider.entity;

import lombok.Data;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Data
public class TsVideo {

    /**
     * 视频地址
     */
    private String url;

    /**
     * 视频名
     */
    private String title;

    /**
     * 视频类型
     */
    private String type;
}
