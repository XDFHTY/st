package com.cj.stshentu.mapper;


import com.cj.stshentu.entity.Standard;

public interface StandardMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long standardId);

    /**
     *
     * @mbggenerated
     */
    int insert(Standard record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Standard record);

    /**
     *
     * @mbggenerated
     */
    Standard selectByPrimaryKey(Long standardId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Standard record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Standard record);
}