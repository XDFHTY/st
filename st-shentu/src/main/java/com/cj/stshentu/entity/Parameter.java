package com.cj.stshentu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {

    private double beam1Point1X;
    private double beam1Point1Y;
    private double beam1Point2X;
    private double beam1Point2Y;
    private double beam2Point1X;
    private double beam2Point1Y;
    private double beam2Point2X;
    private double beam2Point2Y;

    private double intenIntersecPointX;
    private double intenIntersecPointY;

    private String downMidLable;
    private String downLeftLable;
    private String downRightLable;
    private String upLeftLable;
    private String upMidLable;
    private String upRightLable;

    private List intensiveLable;

    private int se;
    private String rebar;
    private String concrete;
    private int stride;

    private double tLineSX;
    private double tLineSY;
    private double tLineEX;
    private double tLineEY;

    private int errorType;  //错误类型


    private String msg;  //校验结果信息 string
    private Map rets;  //校验结果配筋率

    private Object result;  //校验结果 boolean

}
