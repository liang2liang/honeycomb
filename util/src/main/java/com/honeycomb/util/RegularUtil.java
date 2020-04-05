package com.honeycomb.util;

import java.util.regex.Pattern;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class RegularUtil {

    private static final Pattern M3U8 = Pattern.compile(".*m3u8");

    private static final Pattern TS = Pattern.compile(".*ts");

    private static final Pattern CHINESE_CODE = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 是否为M3u8格式文件
     *
     * @param url
     * @return
     */
    public static boolean isM3u8(String url) {
        if (StringUtil.isNotBlank(url))
            return M3U8.matcher(url).find();
        return false;
    }

    /**
     * 是否为ts文件
     *
     * @param url
     * @return
     */
    public static boolean isTs(String url) {
        if (StringUtil.isNotBlank(url))
            return TS.matcher(url).find();
        return false;
    }

    /**
     * 是否包含中文
     *
     * @param str 字符串
     * @return
     */
    public static boolean containsChinese(String str) {
        if (StringUtil.isNotBlank(str))
            return CHINESE_CODE.matcher(str).find();
        return false;
    }
}
