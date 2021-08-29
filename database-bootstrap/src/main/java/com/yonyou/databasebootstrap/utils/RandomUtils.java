package com.yonyou.databasebootstrap.utils;

import java.util.UUID;

/**
 * @Author: Ember
 * @Date: 2021/8/25 15:23
 * @Description: 生成随机code
 */
public class RandomUtils {

    /**
     * 生成32位UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
