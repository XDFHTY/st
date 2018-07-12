package com.cj.stshentu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Standard {
    /**
     * 标准信息表
     */
    private Long standardId;

    /**
     * 标准编号
     */
    private String standardNum;

    /**
     * 标准名称
     */
    private String standardName;

    /**
     * 发行部门
     */
    private String standardDepartment;

    /**
     * 实施日期
     */
    private Date actualizeTime;

    /**
     * 01-作废，02-废止，11-现行
     */
    private String state;

    /**
     * 修改时间
     */
    private Date updateTime;


}