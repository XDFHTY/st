package com.cj.stshentu.service;


import com.cj.stshentu.entity.Parameter;

import java.util.Map;


/**
 * 审图计算接口
 */


public interface ShenTuService {


    public String ST1(Parameter parameter);

    public String ST2(Parameter parameter);

    public Map ST3(Parameter parameter, Long reqId, int num);
}
