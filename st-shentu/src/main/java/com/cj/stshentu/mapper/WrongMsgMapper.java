package com.cj.stshentu.mapper;


import com.cj.stshentu.entity.WrongMsg;

public interface WrongMsgMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long wrongId);

    /**
     *
     * @mbggenerated
     */
    int insert(WrongMsg record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(WrongMsg record);

    /**
     *
     * @mbggenerated
     */
    WrongMsg selectByPrimaryKey(Long wrongId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WrongMsg record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WrongMsg record);
}