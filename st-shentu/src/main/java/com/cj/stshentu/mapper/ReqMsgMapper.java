package com.cj.stshentu.mapper;


import com.cj.stshentu.entity.ReqMsg;

public interface ReqMsgMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long reqId);

    /**
     *
     * @mbggenerated
     */
    int insert(ReqMsg record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(ReqMsg record);

    /**
     *
     * @mbggenerated
     */
    ReqMsg selectByPrimaryKey(Long reqId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ReqMsg record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ReqMsg record);
}