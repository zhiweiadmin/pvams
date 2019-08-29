package com.goodpower.pvams.common;

import com.google.common.collect.Maps;

import java.util.Map;

public class ResultCode {

    public static final Map<String,String> CODEMAP = Maps.newHashMap();

    static {
        CODEMAP.put("200","请求成功");
        CODEMAP.put("302","token已失效");
        CODEMAP.put("400","请求失败");
        CODEMAP.put("401","token验证错误");
        CODEMAP.put("500","服务器异常");
    }

}
