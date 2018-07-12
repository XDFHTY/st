package com.cj.stshentu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqMsg {
    /**
     * 用户请求信息记录表
     */
    private Long reqId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 单条请求数据总量
     */
    private Integer dataCount;

    /**
     * 单条请求错误数量
     */
    private Integer errorCount;

    /**
     * 请求时间
     */
    private Date createTime;


}