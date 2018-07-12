package com.cj.stcommon.utils.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建一个内存数据类，用于存放静态的数据，并初始化
 * 用于单设备登录
 */
public class MemoryData {

    private static ExpiryMap<String, String> tokenMap = new ExpiryMap<>(1000*60*60*2);



    public static ExpiryMap<String, String> getTokenMap() {
        return tokenMap;
    }

    public static void setTokenMap(ExpiryMap<String, String> tokenMap) {
        MemoryData.tokenMap = tokenMap;
    }
}