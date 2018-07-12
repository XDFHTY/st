package com.cj.stshentu.service.impl;

import com.cj.stshentu.entity.WrongMsg;
import com.cj.stshentu.mapper.WrongMsgMapper;
import com.cj.stshentu.service.WrongMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WrongMsgServiceImpl implements WrongMsgService {


    @Autowired
    private WrongMsgMapper wrongMsgMapper;
    @Override
    public int addWrongMsg(WrongMsg wrongMsg) {
        return wrongMsgMapper.insertSelective(wrongMsg);
    }
}
