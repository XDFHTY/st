package com.cj.stshentu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrongMsg {
    /**
     * 错误信息记录表
     */
    private Long wrongId;

    /**
     * 请求ID
     */
    private Long reqId;

    /**
     * 单次请求循环次数编号
     */
    private Integer loopNum;

    /**
     * 错误部位，2-左支座配筋率，4-右支座配筋率，8-跨中下支座配筋率，
     * 11-抗震规范，12-混凝土规范,13-箍筋直径
     * 21-梁左上标注无法判断,22-梁右上标注无法判断,23-梁跨中上标注无法判断,24-梁跨中下标注无法判断
     */
    private String retNum;

    /**
     * 错误信息
     */
    private String wrongMsg;


}