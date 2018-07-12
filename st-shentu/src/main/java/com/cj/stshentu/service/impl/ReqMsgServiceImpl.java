package com.cj.stshentu.service.impl;

import com.cj.stshentu.entity.ReqMsg;
import com.cj.stshentu.mapper.ReqMsgMapper;
import com.cj.stshentu.service.ReqMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReqMsgServiceImpl implements ReqMsgService {

    @Autowired
    private ReqMsgMapper reqMsgMapper;

    @Override
    public int addReqMsg(ReqMsg reqMsg) {
        return reqMsgMapper.insertSelective(reqMsg);
    }

    @Override
    public int updateReqMsg(ReqMsg reqMsg) {
        return reqMsgMapper.updateByPrimaryKeySelective(reqMsg);
    }
}
