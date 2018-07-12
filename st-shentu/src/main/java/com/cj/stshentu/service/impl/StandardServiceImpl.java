package com.cj.stshentu.service.impl;

import com.cj.stshentu.entity.Standard;
import com.cj.stshentu.mapper.StandardMapper;
import com.cj.stshentu.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardMapper standardMapper;

    @Override
    public int addStandard(Standard standard) {
        return standardMapper.insertSelective(standard);
    }
}
