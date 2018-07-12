package com.cj.stshentu.service;


import com.cj.stshentu.entity.ReqMsg;

public interface ReqMsgService {

    //保存请求信息
    public int addReqMsg(ReqMsg reqMsg);

    //修改请求信息
    public int updateReqMsg(ReqMsg reqMsg);
}
