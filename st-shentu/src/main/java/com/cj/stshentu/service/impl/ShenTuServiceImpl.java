package com.cj.stshentu.service.impl;

import com.cj.stshentu.entity.Parameter;
import com.cj.stshentu.entity.WrongMsg;
import com.cj.stshentu.service.ShenTuService;
import com.cj.stshentu.service.WrongMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

import static com.cj.stcommon.utils.util.MathExtend.add;
import static com.cj.stcommon.utils.util.MathExtend.divide;
import static com.cj.stcommon.utils.util.MathExtend.multiply;
import static com.cj.stcommon.utils.util.STAssistUtil.*;
import static java.lang.Math.PI;


@Service
@Slf4j
public class ShenTuServiceImpl implements ShenTuService {


    @Autowired
    private WrongMsgService wrongMsgService;

    /**
     * 未提取公式
     * @param parameter
     * @return
     */
    @Override
    public String ST1(Parameter parameter) {

        String msg = "";

        double min_steel_ratio = 0;
        double midspan_min_steel_ratio = 0;
        int min_dia = 0;  //获取最小箍筋直径
        int seismic_param = parameter.getSe();
        double concrete_tensile_param = 0;
        double concrete_resist_param = 0;
        int rebar_tensile_param = 0;
        int rebar_resist_param = 0;

        System.out.println("seismic_param=================>>"+seismic_param);


        if(parameter.getConcrete().equals("C30")){
            concrete_tensile_param = 1.43;
            concrete_resist_param = 14.3;
        }else if(parameter.getConcrete().equals("C35")){
            concrete_tensile_param = 1.57;
            concrete_resist_param = 16.7;
        }else if(parameter.getConcrete().equals("C40")){
            concrete_tensile_param = 1.71;
            concrete_resist_param = 19.1;
        }else if(parameter.getConcrete().equals("C45")){
            concrete_tensile_param = 1.8;
            concrete_resist_param = 21.1;
        }else if(parameter.getConcrete().equals("C50")){
            concrete_tensile_param = 1.89;
            concrete_resist_param = 23.1;
        }

        System.out.println("concrete_tensile_param================>>"+concrete_tensile_param);
        System.out.println("concrete_resist_param================>>"+concrete_resist_param);



        if(parameter.getRebar().equals("HPB300")){
            rebar_tensile_param = 270;
            rebar_resist_param = 270;
        } else if(parameter.getRebar().equals("HRB335")){
            rebar_tensile_param = 300;
            rebar_resist_param = 300;
        }else if(parameter.getRebar().equals("HRB400")){
            rebar_tensile_param = 360;
            rebar_resist_param = 360;
        }else if(parameter.getRebar().equals("HRB500")){
            rebar_tensile_param = 435;
            rebar_resist_param = 435;
        }

        System.out.println("rebar_tensile_param========>>"+rebar_tensile_param);
        System.out.println("rebar_resist_param========>>"+rebar_resist_param);



        if(seismic_param==1){




            min_steel_ratio = getMax(0.004, divide(divide(multiply(80,concrete_tensile_param),rebar_tensile_param),100));
            midspan_min_steel_ratio = getMax(0.003,divide(divide(multiply(65,concrete_tensile_param),rebar_tensile_param),100));
            min_dia = 10;

        }else if(seismic_param==2){
            min_steel_ratio = getMax(0.003,divide(divide(multiply(65,concrete_tensile_param),rebar_tensile_param),100));
            midspan_min_steel_ratio = getMax(0.0025,divide(divide(multiply(55,concrete_tensile_param),rebar_tensile_param),100));
            min_dia = 8;

        }else if(seismic_param==3){
            min_steel_ratio = getMax(0.0025,divide(divide(multiply(55,concrete_tensile_param),rebar_tensile_param),100));
            midspan_min_steel_ratio = getMax(0.002,divide(divide(multiply(45,concrete_tensile_param),rebar_tensile_param),100));
            min_dia = 8;
        }

        System.out.println("支座最小配筋率======>>"+min_steel_ratio);
        System.out.println("跨中最小配筋率======>>"+midspan_min_steel_ratio);
        System.out.println("箍筋直径======>>"+min_dia);


        int n1 = -1;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int d1 = -1;
        int d2 = -1;
        int d3 = -1;
        int d4 = -1;
        int n11 = -1;
        int n22 = -1;
        int n33 = -1;
        int n44 = -1;
        int d11 = -1;
        int d22 = -1;
        int d33 = -1;
        int d44 = -1;
        String reg = "%%[0-9]{3}";  //正则

        if(parameter.getDownMidLable()!=null && parameter.getDownMidLable().trim().length()>0 && parameter.getDownMidLable().indexOf("@")== -1 && parameter.getDownMidLable().indexOf("x")== -1){
            System.out.println("DownMidLable==========>>"+parameter.getDownMidLable());
            if(parameter.getDownMidLable().indexOf("+") > -1){
                parameter.setDownMidLable(repString(parameter.getDownMidLable()));
                String[] strings4 = parameter.getDownMidLable().split(reg);
                System.out.println("strings4=========>>"+Arrays.toString(strings4));
                n4 = Integer.parseInt(strings4[0].trim());
                d4 = Integer.parseInt(strings4[1].trim());
                n44 = Integer.parseInt(strings4[2].trim());
                d44 = Integer.parseInt(strings4[3].trim());

            }else {
                parameter.setDownMidLable(repString(parameter.getDownMidLable()));
                String[] strings4 = parameter.getDownMidLable().split(reg);
                System.out.println("strings4=========>>"+Arrays.toString(strings4));
                n4 = Integer.parseInt(strings4[0].trim());
                d4 = Integer.parseInt(strings4[1].trim());
            }

        }

        if(parameter.getUpLeftLable()!=null && parameter.getUpLeftLable().trim().length()>0 ){
            System.out.println("UpLeftLable==========>>"+parameter.getUpLeftLable());
            if(parameter.getUpLeftLable().indexOf("+") > -1){
                parameter.setUpLeftLable(repString(parameter.getUpLeftLable()));
                String[] strings1 = parameter.getUpLeftLable().split(reg);
                System.out.println("strings1=========>>"+Arrays.toString(strings1));
                n1 = Integer.parseInt(strings1[0].trim());
                d1 = Integer.parseInt(strings1[1].trim());
                n11 = Integer.parseInt(strings1[2].trim());
                d11 = Integer.parseInt(strings1[3].trim());

            }else {
                parameter.setUpLeftLable(repString(parameter.getUpLeftLable()));
                String[] strings1 = parameter.getUpLeftLable().split(reg);
                System.out.println("strings1=========>>"+Arrays.toString(strings1));
                n1 = Integer.parseInt(strings1[0].trim());
                d1 = Integer.parseInt(strings1[1].trim());

            }


        }

        if(parameter.getUpMidLable()!=null && parameter.getUpMidLable().trim().length()>0 ){
            System.out.println("UpMidLable==========>>"+parameter.getUpMidLable());
            if(parameter.getUpMidLable().indexOf("+") > -1){
                parameter.setUpMidLable(repString(parameter.getUpMidLable()));
                String[] strings3 = parameter.getUpMidLable().split(reg);
                System.out.println("strings3=========>>"+Arrays.toString(strings3));
                n3 = Integer.parseInt(strings3[0].trim());
                d3 = Integer.parseInt(strings3[1].trim());
                n33 = Integer.parseInt(strings3[2].trim());
                d33 = Integer.parseInt(strings3[3].trim());

            }else {

                parameter.setUpMidLable(repString(parameter.getUpMidLable()));
                String[] strings3 = parameter.getUpMidLable().split(reg);
                System.out.println("strings3=========>>"+Arrays.toString(strings3));
                n3 = Integer.parseInt(strings3[0].trim());
                d3 = Integer.parseInt(strings3[1].trim());
            }

        }

        if(parameter.getUpRightLable()!=null && parameter.getUpRightLable().trim().length()>0 ){
            System.out.println("UpRightLable==========>>"+parameter.getUpRightLable());
            if(parameter.getUpRightLable().indexOf("+") > -1){
                parameter.setUpRightLable(repString(parameter.getUpRightLable()));
                String[] strings2 = parameter.getUpRightLable().split(reg);
                System.out.println("strings2=========>>"+Arrays.toString(strings2));
                n2 = Integer.parseInt(strings2[0].trim());
                d2 = Integer.parseInt(strings2[1].trim());
                n22 = Integer.parseInt(strings2[2].trim());
                d22 = Integer.parseInt(strings2[3].trim());

            }else {
                parameter.setUpRightLable(repString(parameter.getUpRightLable()));
                String[] strings2 = parameter.getUpRightLable().split(reg);
                System.out.println("strings2=========>>"+Arrays.toString(strings2));
                n2 = Integer.parseInt(strings2[0].trim());
                d2 = Integer.parseInt(strings2[1].trim());

            }

        }



        System.out.println("n1=====>"+n1);
        System.out.println("d1=====>"+d1);
        System.out.println("n2=====>"+n2);
        System.out.println("d2=====>"+d2);
        System.out.println("n3=====>"+n3);
        System.out.println("d3=====>"+d3);
        System.out.println("n4=====>"+n4);
        System.out.println("d4=====>"+d4);
        System.out.println("n11=====>"+n11);
        System.out.println("d11=====>"+d11);
        System.out.println("n22=====>"+n22);
        System.out.println("d22=====>"+d22);
        System.out.println("n33=====>"+n33);
        System.out.println("d33=====>"+d33);
        System.out.println("n44=====>"+n44);
        System.out.println("d44=====>"+d44);





        //获取宽度、高度
        String str1 = (String) parameter.getIntensiveLable().get(0);
        int statr = str1.indexOf(")");
        int end = str1.indexOf("x");



        String str11 =  str1.substring(statr+1,end).trim();
        String str12 =  str1.substring(end+1).trim();

        int wide = Integer.parseInt(str11);
        int height =Integer.parseInt(str12);

        //获取直径
        String str2 = (String) parameter.getIntensiveLable().get(1);
        int three = str2.indexOf("@");

        int d = Integer.parseInt(str2.substring(5,three));

        System.out.println("wide===========>>"+wide);
        System.out.println("height===========>>"+height);
        System.out.println("d===========>>"+d);

        double ret1 = -1;  //左支座配筋面积
        double ret2 = -1;  //左支座配筋率
        double ret3 = -1;  //右支座配筋面积
        double ret4 = -1;  //右支座配筋率
        double ret5 = -1;  //跨中上支座配筋面积
        double ret6 = -1;  //跨中上支座配筋率
        double ret7 = -1;  //跨中下支座配筋面积
        double ret8 = -1;  //跨中下支座配筋率





        if(n1>0 && d1>0){

            if(n11>0 && d11>0){

                ret1 = multiply(multiply(multiply(n1,d1),d1),divide(PI,4)) + multiply(multiply(multiply(n11,d11),d11),divide(PI,4));                      //以上四个字段都要调这两个公式

            }else {
                ret1 = multiply(multiply(multiply(n1,d1),d1),divide(PI,4));                      //以上四个字段都要调这两个公式

            }
            ret2 = divide(divide(ret1,wide),height-60);                  //不应小于left_min_steel_ratio的值     ,左支座配筋率


        }
        if(n2>0 && d2>0){
            if(n22>0 && d22>0){
                ret3 = multiply(multiply(multiply(n2,d2),d2),divide(PI,4)) + multiply(multiply(multiply(n22,d22),d22),divide(PI,4));
            }else {
                ret3 = multiply(multiply(multiply(n2,d2),d2),divide(PI,4));
            }
            ret4 = divide(divide(ret3,wide),height-60);               //不应小于left_min_steel_ratio的值     ,右支座配筋率
        }

        if(n3>0 && d3>0){

            if(n33>0 && d33>0){
                ret5 = multiply(multiply(multiply(n3,d3),d3),divide(PI,4)) + multiply(multiply(multiply(n33,d33),d33),divide(PI,4));
            }else {
                ret5 = multiply(multiply(multiply(n3,d3),d3),divide(PI,4));
            }
            ret6 = divide(divide(ret5,wide),height-60);                                                        //跨中上配筋率
        }

        if(n4>0 && d4>0){
            if(n44>0 && d44>0){
                ret7 = multiply(multiply(multiply(n4,d4),d4),divide(PI,4)) + multiply(multiply(multiply(n44,d44),d44),divide(PI,4));
            }else {
                ret7 = multiply(multiply(multiply(n4,d4),d4),divide(PI,4));
            }
            ret8 = divide(divide(ret7,wide),height-60); 												       //跨中下配筋率
        }

        System.out.println("ret1==>"+ret1);
        System.out.println("ret2==>"+ret2);
        System.out.println("ret3==>"+ret3);
        System.out.println("ret4==>"+ret4);
        System.out.println("ret5==>"+ret5);
        System.out.println("ret6==>"+ret6);
        System.out.println("ret7==>"+ret7);
        System.out.println("ret8==>"+ret8);


        //受压区高度
        double x1 = -1;
        double x2 = -1;
        if(ret1>0 && ret7>0){
            x1 = divide((multiply(ret1,rebar_tensile_param)- multiply(ret7,rebar_resist_param)),multiply(multiply(wide,height - 60),concrete_resist_param));
        }

        if(ret3>0 && ret7>0){
            x2 = divide((multiply(ret3,rebar_tensile_param)- multiply(ret7,rebar_resist_param)),multiply(multiply(wide,height - 60),concrete_resist_param));
        }


        System.out.println("x1==>"+x1);
        System.out.println("x2==>"+x2);



//        double ret1 左支座配筋面积
//        double ret2 左支座配筋率
//        double ret3 右支座配筋面积
//        double ret4 右支座配筋率
//        double ret5 跨中上支座配筋面积
//        double ret6 跨中上支座配筋率
//        double ret7 跨中下支座配筋面积
//        double ret8 跨中下支座配筋率
//        d 当前箍筋直径



        //判断结果
        if(ret2 > 0){

            if(ret2 < min_steel_ratio){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】左支座配筋率("+doDecimal(ret2)+")小于最小配筋率("+doDecimal(min_steel_ratio)+");";
            }else if (ret2 > 0.025){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】左支座配筋率("+doDecimal(ret2)+")不应大于2.5%;";
            }else if(ret2 > 0.02 && ret2 < 0.025){

                if(d<(min_dia + 2)){
                    msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】箍筋直径应大于等于"+doDecimal((min_dia + 2))+",当前箍筋直径为("+doDecimal(d)+");";

                }
            }
        }

        if(ret4 > 0){
            if(ret4 < min_steel_ratio){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】右支座配筋率("+doDecimal(ret4)+")小于最小配筋率("+doDecimal(min_steel_ratio)+");";
            }else if (ret4 > 0.025 ){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】右支座配筋率("+doDecimal(ret4)+")不应大于2.5%;";
            }else if(ret4 > 0.02 && ret4 < 0.025){
                if(d < min_dia + 2){

                    msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】箍筋直径应大于等于"+doDecimal((min_dia + 2))+",当前箍筋直径为("+doDecimal(d)+");";
                }
            }
        }


        if(ret8 > 0){
            if(ret8 < midspan_min_steel_ratio){

                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】跨中下支座配筋率("+doDecimal(ret8)+")小于最小配筋率("+doDecimal(midspan_min_steel_ratio)+");";
            }
        }

        if(seismic_param == 1) {

            if(ret7>0 && (ret1>0 || ret3>0)){
                double dd1 = divide(ret7,getMax(ret1,ret3));

                if (dd1>0 && dd1 < 0.5) {

                    msg += "【抗震规范6.3.3-2条款判断】梁端截面的底面和顶面纵向钢筋配筋量的比值("+doDecimal(dd1)+")，一级不应小于0.5;";
                }
            }


            if(x1>0.25 | x2>0.25){
                msg +="【抗震规范11.3.1条款判断】混凝土受压区高度不符合要求。";
            }

        }


        if(seismic_param == 2 || seismic_param == 3) {

            if(ret7>0 && (ret1>0 || ret3>0)){

                double dd2 = divide(ret7,getMax(ret1,ret3));
                if (dd2>0 && dd2 < 0.3) {
                    msg += "【抗震规范6.3.3-2条款判断】梁端截面的底面和顶面纵向钢筋配尽量的比值("+dd2+"),二三级不应小于0.3;";
                }
            }

            if(x1>0.35 | x2>0.35){
                msg +="【抗震规范11.3.1条款判断】混凝土受压区高度不符合要求。";
            }
        }





        if(msg.length()>0){
            msg += "<=======此处检查已完============>";

        }
        System.out.println("结果===>>"+msg);
        return msg;
    }

    /**
     * 将公式提取后
     * @param parameter
     * @return
     */
    @Override
    public String ST2(Parameter parameter) {

        String msg = "";

        double min_steel_ratio = 0;
        double midspan_min_steel_ratio = 0;
        int min_dia = 0;  //获取最小箍筋直径
        int seismic_param = parameter.getSe();
        double concrete_tensile_param = 0;
        double concrete_resist_param = 0;
        int rebar_tensile_param = 0;
        int rebar_resist_param = 0;

//        System.out.println("seismic_param=================>>"+seismic_param);


        if(parameter.getConcrete().equals("C30")){
            concrete_tensile_param = 1.43;
            concrete_resist_param = 14.3;
        }else if(parameter.getConcrete().equals("C35")){
            concrete_tensile_param = 1.57;
            concrete_resist_param = 16.7;
        }else if(parameter.getConcrete().equals("C40")){
            concrete_tensile_param = 1.71;
            concrete_resist_param = 19.1;
        }else if(parameter.getConcrete().equals("C45")){
            concrete_tensile_param = 1.8;
            concrete_resist_param = 21.1;
        }else if(parameter.getConcrete().equals("C50")){
            concrete_tensile_param = 1.89;
            concrete_resist_param = 23.1;
        }

//        System.out.println("concrete_tensile_param================>>"+concrete_tensile_param);
//        System.out.println("concrete_resist_param================>>"+concrete_resist_param);



        if(parameter.getRebar().equals("HPB300")){
            rebar_tensile_param = 270;
            rebar_resist_param = 270;
        } else if(parameter.getRebar().equals("HRB335")){
            rebar_tensile_param = 300;
            rebar_resist_param = 300;
        }else if(parameter.getRebar().equals("HRB400")){
            rebar_tensile_param = 360;
            rebar_resist_param = 360;
        }else if(parameter.getRebar().equals("HRB500")){
            rebar_tensile_param = 435;
            rebar_resist_param = 435;
        }

//        System.out.println("rebar_tensile_param========>>"+rebar_tensile_param);
//        System.out.println("rebar_resist_param========>>"+rebar_resist_param);



        if(seismic_param==1){




            min_steel_ratio = getMax(0.004, formula4(80,concrete_tensile_param,rebar_tensile_param));
            midspan_min_steel_ratio = getMax(0.003,formula4(65,concrete_tensile_param,rebar_tensile_param));
            min_dia = 10;

        }else if(seismic_param==2){
            min_steel_ratio = getMax(0.003,formula4(65,concrete_tensile_param,rebar_tensile_param));
            midspan_min_steel_ratio = getMax(0.0025,formula4(55,concrete_tensile_param,rebar_tensile_param));
            min_dia = 8;

        }else if(seismic_param==3){
            min_steel_ratio = getMax(0.0025,formula4(55,concrete_tensile_param,rebar_tensile_param));
            midspan_min_steel_ratio = getMax(0.002,formula4(45,concrete_tensile_param,rebar_tensile_param));
            min_dia = 8;
        }

//        System.out.println("支座最小配筋率======>>"+min_steel_ratio);
//        System.out.println("跨中最小配筋率======>>"+midspan_min_steel_ratio);
//        System.out.println("箍筋直径======>>"+min_dia);


        int n1 = -1;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int d1 = -1;
        int d2 = -1;
        int d3 = -1;
        int d4 = -1;
        int n11 = -1;
        int n22 = -1;
        int n33 = -1;
        int n44 = -1;
        int d11 = -1;
        int d22 = -1;
        int d33 = -1;
        int d44 = -1;
        String reg = "%%[0-9]{3}";  //正则

        if(parameter.getDownMidLable()!=null && parameter.getDownMidLable().trim().length()>0 && parameter.getDownMidLable().indexOf("@")== -1 && parameter.getDownMidLable().indexOf("x")== -1){
//            System.out.println("DownMidLable==========>>"+parameter.getDownMidLable());
            if(parameter.getDownMidLable().indexOf("+") > -1){
                parameter.setDownMidLable(repString(parameter.getDownMidLable()));
                String[] strings4 = parameter.getDownMidLable().split(reg);
//                System.out.println("strings4=========>>"+Arrays.toString(strings4));
                n4 = Integer.parseInt(strings4[0].trim());
                d4 = Integer.parseInt(strings4[1].trim());
                n44 = Integer.parseInt(strings4[2].trim());
                d44 = Integer.parseInt(strings4[3].trim());

            }else {
                parameter.setDownMidLable(repString(parameter.getDownMidLable()));
                String[] strings4 = parameter.getDownMidLable().split(reg);
//                System.out.println("strings4=========>>"+ Arrays.toString(strings4));
                n4 = Integer.parseInt(strings4[0].trim());
                d4 = Integer.parseInt(strings4[1].trim());
            }

        }

        if(parameter.getUpLeftLable()!=null && parameter.getUpLeftLable().trim().length()>0 ){
//            System.out.println("UpLeftLable==========>>"+parameter.getUpLeftLable());
            if(parameter.getUpLeftLable().indexOf("+") > -1){
                parameter.setUpLeftLable(repString(parameter.getUpLeftLable()));
                String[] strings1 = parameter.getUpLeftLable().split(reg);
//                System.out.println("strings1=========>>"+Arrays.toString(strings1));
                n1 = Integer.parseInt(strings1[0].trim());
                d1 = Integer.parseInt(strings1[1].trim());
                n11 = Integer.parseInt(strings1[2].trim());
                d11 = Integer.parseInt(strings1[3].trim());

            }else {
                parameter.setUpLeftLable(repString(parameter.getUpLeftLable()));
                String[] strings1 = parameter.getUpLeftLable().split(reg);
//                System.out.println("strings1=========>>"+Arrays.toString(strings1));
                n1 = Integer.parseInt(strings1[0].trim());
                d1 = Integer.parseInt(strings1[1].trim());

            }


        }

        if(parameter.getUpMidLable()!=null && parameter.getUpMidLable().trim().length()>0 ){
//            System.out.println("UpMidLable==========>>"+parameter.getUpMidLable());
            if(parameter.getUpMidLable().indexOf("+") > -1){
                parameter.setUpMidLable(repString(parameter.getUpMidLable()));
                String[] strings3 = parameter.getUpMidLable().split(reg);
//                System.out.println("strings3=========>>"+Arrays.toString(strings3));
                n3 = Integer.parseInt(strings3[0].trim());
                d3 = Integer.parseInt(strings3[1].trim());
                n33 = Integer.parseInt(strings3[2].trim());
                d33 = Integer.parseInt(strings3[3].trim());

            }else {

                parameter.setUpMidLable(repString(parameter.getUpMidLable()));
                String[] strings3 = parameter.getUpMidLable().split(reg);
//                System.out.println("strings3=========>>"+Arrays.toString(strings3));
                n3 = Integer.parseInt(strings3[0].trim());
                d3 = Integer.parseInt(strings3[1].trim());
            }

        }

        if(parameter.getUpRightLable()!=null && parameter.getUpRightLable().trim().length()>0 ){
//            System.out.println("UpRightLable==========>>"+parameter.getUpRightLable());
            if(parameter.getUpRightLable().indexOf("+") > -1){
                parameter.setUpRightLable(repString(parameter.getUpRightLable()));
                String[] strings2 = parameter.getUpRightLable().split(reg);
//                System.out.println("strings2=========>>"+Arrays.toString(strings2));
                n2 = Integer.parseInt(strings2[0].trim());
                d2 = Integer.parseInt(strings2[1].trim());
                n22 = Integer.parseInt(strings2[2].trim());
                d22 = Integer.parseInt(strings2[3].trim());

            }else {
                parameter.setUpRightLable(repString(parameter.getUpRightLable()));
                String[] strings2 = parameter.getUpRightLable().split(reg);
//                System.out.println("strings2=========>>"+Arrays.toString(strings2));
                n2 = Integer.parseInt(strings2[0].trim());
                d2 = Integer.parseInt(strings2[1].trim());

            }

        }



//        System.out.println("n1=====>"+n1);
//        System.out.println("d1=====>"+d1);
//        System.out.println("n2=====>"+n2);
//        System.out.println("d2=====>"+d2);
//        System.out.println("n3=====>"+n3);
//        System.out.println("d3=====>"+d3);
//        System.out.println("n4=====>"+n4);
//        System.out.println("d4=====>"+d4);
//        System.out.println("n11=====>"+n11);
//        System.out.println("d11=====>"+d11);
//        System.out.println("n22=====>"+n22);
//        System.out.println("d22=====>"+d22);
//        System.out.println("n33=====>"+n33);
//        System.out.println("d33=====>"+d33);
//        System.out.println("n44=====>"+n44);
//        System.out.println("d44=====>"+d44);





        //获取宽度、高度
        String str1 = (String) parameter.getIntensiveLable().get(0);
        int statr = str1.indexOf(")");
        int end = str1.indexOf("x");



        String str11 =  str1.substring(statr+1,end).trim();
        String str12 =  str1.substring(end+1).trim();

        int wide = Integer.parseInt(str11);
        int height =Integer.parseInt(str12);

        //获取直径
        String str2 = (String) parameter.getIntensiveLable().get(1);
        int three = str2.indexOf("@");

        int d = Integer.parseInt(str2.substring(5,three));

//        System.out.println("wide===========>>"+wide);
//        System.out.println("height===========>>"+height);
//        System.out.println("d===========>>"+d);

        double ret1 = -1;  //左支座配筋面积
        double ret2 = -1;  //左支座配筋率
        double ret3 = -1;  //右支座配筋面积
        double ret4 = -1;  //右支座配筋率
        double ret5 = -1;  //跨中上支座配筋面积
        double ret6 = -1;  //跨中上支座配筋率
        double ret7 = -1;  //跨中下支座配筋面积
        double ret8 = -1;  //跨中下支座配筋率





        if(n1>0 && d1>0){

            if(n11>0 && d11>0){


                ret1 = add(formula1(n1,d1),formula1(n11,d11));
            }else {

                ret1 = formula1(n1,d1);
            }

            ret2 = formula2(ret1,wide,height);

        }
        if(n2>0 && d2>0){
            if(n22>0 && d22>0){
                ret3 = add(formula1(n2,d2),formula1(n22,d22));
            }else {
                ret3 = formula1(n2,d2);
            }
            ret4 = formula2(ret3,wide,height);
        }

        if(n3>0 && d3>0){

            if(n33>0 && d33>0){
                ret5 = add(formula1(n3,d3),formula1(n33,d33));
            }else {
                ret5 = formula1(n3,d3);
            }
            ret6 = formula2(ret5,wide,height);
        }

        if(n4>0 && d4>0){
            if(n44>0 && d44>0){
                ret7 = add(formula1(n4,d4),formula1(n44,d44));
            }else {
                ret7 = formula1(n4,d4);
            }
            ret8 = formula2(ret7,wide,height);
        }

//        System.out.println("ret1==>"+ret1);
//        System.out.println("ret2==>"+ret2);
//        System.out.println("ret3==>"+ret3);
//        System.out.println("ret4==>"+ret4);
//        System.out.println("ret5==>"+ret5);
//        System.out.println("ret6==>"+ret6);
//        System.out.println("ret7==>"+ret7);
//        System.out.println("ret8==>"+ret8);


        //受压区高度
        double x1 = -1;
        double x2 = -1;
        if(ret1>0 && ret7>0){

            x1 = formula3(ret1,rebar_tensile_param,ret7,rebar_resist_param,wide,height,concrete_resist_param);
        }

        if(ret3>0 && ret7>0){
            x2 = formula3(ret3,rebar_tensile_param,ret7,rebar_resist_param,wide,height,concrete_resist_param);
        }


//        System.out.println("x1==>"+x1);
//        System.out.println("x2==>"+x2);



//        double ret1 左支座配筋面积
//        double ret2 左支座配筋率
//        double ret3 右支座配筋面积
//        double ret4 右支座配筋率
//        double ret5 跨中上支座配筋面积
//        double ret6 跨中上支座配筋率
//        double ret7 跨中下支座配筋面积
//        double ret8 跨中下支座配筋率
//        d 当前箍筋直径



        //判断结果
        if(ret2 > 0){

            if(ret2 < min_steel_ratio){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】左支座配筋率("+doDecimal(ret2)+")小于最小配筋率("+doDecimal(min_steel_ratio)+");";
            }else if (ret2 > 0.025){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】左支座配筋率("+doDecimal(ret2)+")不应大于2.5%;";
            }else if(ret2 > 0.02 && ret2 < 0.025){

                if(d<(min_dia + 2)){
                    msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】箍筋直径应大于等于"+doDecimal((min_dia + 2))+",当前箍筋直径为("+doDecimal(d)+");";

                }
            }
        }

        if(ret4 > 0){
            if(ret4 < min_steel_ratio){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】右支座配筋率("+doDecimal(ret4)+")小于最小配筋率("+doDecimal(min_steel_ratio)+");";
            }else if (ret4 > 0.025 ){
                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】右支座配筋率("+doDecimal(ret4)+")不应大于2.5%;";
            }else if(ret4 > 0.02 && ret4 < 0.025){
                if(d < min_dia + 2){

                    msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】箍筋直径应大于等于"+doDecimal((min_dia + 2))+",当前箍筋直径为("+doDecimal(d)+");";
                }
            }
        }


        if(ret8 > 0){
            if(ret8 < midspan_min_steel_ratio){

                msg += "【抗震规范6.3.3-1条款判断，6.3.3-3条款判断】跨中下支座配筋率("+doDecimal(ret8)+")小于最小配筋率("+doDecimal(midspan_min_steel_ratio)+");";
            }
        }

        if(seismic_param == 1) {

            if(ret7>0 && (ret1>0 || ret3>0)){
                double dd1 = divide(ret7,getMax(ret1,ret3));

                if (dd1>0 && dd1 < 0.5) {

                    msg += "【抗震规范6.3.3-2条款判断】梁端截面的底面和顶面纵向钢筋配筋量的比值("+doDecimal(dd1)+")，一级不应小于0.5;";
                }
            }


            if(x1>0.25 | x2>0.25){
                msg +="【抗震规范11.3.1条款判断】混凝土受压区高度不符合要求。";
            }

        }


        if(seismic_param == 2 || seismic_param == 3) {

            if(ret7>0 && (ret1>0 || ret3>0)){

                double dd2 = divide(ret7,getMax(ret1,ret3));
                if (dd2>0 && dd2 < 0.3) {
                    msg += "【抗震规范6.3.3-2条款判断】梁端截面的底面和顶面纵向钢筋配尽量的比值("+dd2+"),二三级不应小于0.3;";
                }
            }

            if(x1>0.35 | x2>0.35){
                msg +="【抗震规范11.3.1条款判断】混凝土受压区高度不符合要求。";
            }
        }





        if(msg.length()>0){
            msg += "<=======此处检查已完============>";

        }
        System.out.println("结果===>>"+msg);
        return msg;
    }


    /**
     * 修改部分逻辑
     * 将错误信息存储到数据库
     * @param parameter
     * @param reqId
     * @param num  单次请求循环次数编号
     * @return
     */
    @Override
    public Map ST3(Parameter parameter, Long reqId, int num) {
        WrongMsg wrongMsg = new WrongMsg();
        wrongMsg.setReqId(reqId);
        wrongMsg.setLoopNum(num);

        /**
         * b[0]:左支座最小配筋率检查结果
         * b[1]:左支座最大配筋率检查结果
         *
         * b[2]:右支座最小配筋率检查结果
         * b[3]:右支座最大配筋率检查结果
         *
         * b[4]:跨中下最小配筋率检查结果
         * b[5]:跨中下最大配筋率检查结果
         *
         * b[6]:跨中上最小配筋率检查结果
         * b[7]:跨中上最大配筋率检查结果
         *
         * b[8]:受压区高度检查结果
         * b[9]:箍筋直径检查结果
         * b[10]:受拉、受压超限
         *
         * b[11]:左支座梁端截面的底面和顶面纵向钢筋配筋量的比值
         * b[12]:右支座梁端截面的底面和顶面纵向钢筋配筋量的比值
         *
         * 默认为true(无错)
         * false(结果有错)
         */
        Boolean[] b = {true,true,true,true,true,true,true,true,true,true,true,true,true};

        //集中标注有问题时使用b1
        Boolean[] b1 = {false,false,false,false,false,false,false,false,false,false,false,false,false};







        String msg = "";

        double min_steel_ratio = 0;             //支座最小配筋率
        double midspan_min_steel_ratio = 0;     //跨中最小配筋率
        int min_dia = 0;                        //获取最小箍筋直径
        int seismic_param = parameter.getSe();  //抗震等级参数
        double concrete_tensile_param = 0;      //混凝土抗拉强度参数
        double concrete_resist_param = 0;       //混凝土抗压强度参数
        int rebar_tensile_param = 0;            //钢筋抗拉强度参数
        int rebar_resist_param = 0;             //钢筋抗压强度参数

//        System.out.println("seismic_param=================>>"+seismic_param);

        switch (parameter.getConcrete()){
            case "C30" :
                concrete_tensile_param = 1.43;
                concrete_resist_param = 14.3;
                break;
            case "C35" :
                concrete_tensile_param = 1.57;
                concrete_resist_param = 16.7;
                break;
            case "C40" :
                concrete_tensile_param = 1.71;
                concrete_resist_param = 19.1;
                break;
            case "C45" :
                concrete_tensile_param = 1.8;
                concrete_resist_param = 21.1;
                break;
            case "C50" :
                concrete_tensile_param = 1.89;
                concrete_resist_param = 23.1;
                break;
        }


//        System.out.println("concrete_tensile_param================>>"+concrete_tensile_param);
//        System.out.println("concrete_resist_param================>>"+concrete_resist_param);


        switch (parameter.getRebar()){
            case "HPB300" :
                rebar_tensile_param = 270;
                rebar_resist_param = 270;
                break;
            case "HRB335" :
                rebar_tensile_param = 300;
                rebar_resist_param = 300;
                break;
            case "HRB400" :
                rebar_tensile_param = 360;
                rebar_resist_param = 360;
                break;
            case "HRB500" :
                rebar_tensile_param = 435;
                rebar_resist_param = 435;
                break;
        }


        switch (seismic_param){
            case 1 :
                min_steel_ratio = getMax(0.004, formula4(80,concrete_tensile_param,rebar_tensile_param));
                midspan_min_steel_ratio = getMax(0.003,formula4(65,concrete_tensile_param,rebar_tensile_param));
                min_dia = 10;
                break;
            case 2 :
                min_steel_ratio = getMax(0.003,formula4(65,concrete_tensile_param,rebar_tensile_param));
                midspan_min_steel_ratio = getMax(0.0025,formula4(55,concrete_tensile_param,rebar_tensile_param));
                min_dia = 8;
                break;
            case 3 :
                min_steel_ratio = getMax(0.0025,formula4(55,concrete_tensile_param,rebar_tensile_param));
                midspan_min_steel_ratio = getMax(0.002,formula4(45,concrete_tensile_param,rebar_tensile_param));
                min_dia = 8;
                break;
            case 4 :
                min_steel_ratio = getMax(0.0025,formula4(55,concrete_tensile_param,rebar_tensile_param));
                midspan_min_steel_ratio = getMax(0.002,formula4(45,concrete_tensile_param,rebar_tensile_param));
                min_dia = 6;
                break;
        }

//        System.out.println("支座最小配筋率======>>"+min_steel_ratio);
//        System.out.println("跨中最小配筋率======>>"+midspan_min_steel_ratio);
//        System.out.println("箍筋直径======>>"+min_dia);


        /**
         * n表示钢筋根数，d表示钢筋直径
         */
        int n1 = -1;  //左上支座钢筋根数
        int n2 = -1;  //右上支座钢筋根数
        int n3 = -1;  //跨中上钢筋根数
        int n4 = -1;  //跨中下钢筋根数
        int d1 = -1;  //左上支座钢筋直径
        int d2 = -1;  //右上支座钢筋直径
        int d3 = -1;  //跨中上钢筋直径
        int d4 = -1;  //跨中下钢筋直径
        int n11 = -1;
        int n22 = -1;
        int n33 = -1;
        int n44 = -1;
        int d11 = -1;
        int d22 = -1;
        int d33 = -1;
        int d44 = -1;
        String reg = "%%[0-9]{3}";  //正则

        System.out.println("=====================parameter=======================>>"+parameter);
        /**
         * 对跨中下进行处理
         */
        if(parameter.getDownMidLable() != null && parameter.getDownMidLable().length() >0 && parameter.getDownMidLable().indexOf(",") != -1){
            //存在逗号
            String string = parameter.getDownMidLable();
            String[] strings = string.split(",");

            String reg2 = "[0-9]x[0-9]";
            List oldIntensiveLable = parameter.getIntensiveLable();  //集中标注
            List newIntensiveLable = new ArrayList();
            String lh = (String) oldIntensiveLable.get(0);  //梁号

            for (String s:strings){
                if(s.indexOf("K") != -1){
                    lh.replaceAll(reg2,s);
                    break;

                }
            }

            newIntensiveLable.add(lh);
            for (String s : strings){
                if(s.indexOf("@") != -1){
                    newIntensiveLable.add(s);
                    break;

                }
            }
            //替换集中标注
            parameter.setIntensiveLable(newIntensiveLable);
            //替换跨中下标注
            String reg3 = "[A-Z]";
            for (String s:strings){
                if(s.indexOf("%%") > 0 &&s.indexOf("(") == -1 && s.indexOf(")") ==-1 &&s.indexOf("@")==-1 &&s.indexOf("x")==-1&&s.indexOf(reg3)==-1){
                    parameter.setDownMidLable(s);

                    break;

                }
            }


        }

        System.out.println("=====================parameter=======================>>"+parameter);
        /**
         * 对跨中下标注进行判断
         */
        //判断 跨中下标注 不为空 且 长度>0 且 不包含@符号 且 不存在x符号
        if(parameter.getDownMidLable() != null && parameter.getDownMidLable().trim().length()>0
                && parameter.getDownMidLable().indexOf("@") == -1
                && parameter.getDownMidLable().indexOf("x") == -1 && parameter.getDownMidLable().indexOf("X") == -1) {
//            System.out.println("DownMidLable==========>>" + parameter.getDownMidLable());

            //替换不能是别的字符串
            parameter.setDownMidLable(rep(parameter.getDownMidLable()));
            //判断 跨中下标注中是否存在%符号 ，如果存在则返回无法判断
            if (inspectString(parameter.getDownMidLable()).length() > 0) {
                msg += "====>>梁跨中下标注无法判断";
                handleErrorMsg(wrongMsg,"24","梁跨中下标注无法判断");
            } else {

                if (parameter.getDownMidLable().indexOf("+") > -1) {
                    parameter.setDownMidLable(repString(parameter.getDownMidLable()));
                    String[] strings4 = parameter.getDownMidLable().split(reg);
//                    System.out.println("strings4=========>>" + Arrays.toString(strings4));
                    n4 = Integer.parseInt(strings4[0].trim());
                    d4 = Integer.parseInt(strings4[1].trim());
                    n44 = Integer.parseInt(strings4[2].trim());
                    d44 = Integer.parseInt(strings4[3].trim());

                } else {
                    parameter.setDownMidLable(repString(parameter.getDownMidLable()));
                    String[] strings4 = parameter.getDownMidLable().split(reg);
//                    System.out.println("strings4=========>>" + Arrays.toString(strings4));
                    n4 = Integer.parseInt(strings4[0].trim());
                    d4 = Integer.parseInt(strings4[1].trim());
                }
            }
        }

        //判断 梁左上标注 不为空 且 长度>0 且 不包含@符号 且 不存在x符号
        if(parameter.getUpLeftLable()!=null && parameter.getUpLeftLable().trim().length()>0
                && parameter.getUpLeftLable().indexOf("@") == -1
                && parameter.getUpLeftLable().indexOf("x") == -1 && parameter.getUpLeftLable().indexOf("X") == -1){

//            System.out.println("UpLeftLable==========>>"+parameter.getUpLeftLable());
            //替换不能是别的字符串
            parameter.setUpLeftLable(rep(parameter.getUpLeftLable()));

            if (inspectString(parameter.getUpLeftLable()).length() > 0) {
                msg += "====>>梁左上标注无法判断";
                handleErrorMsg(wrongMsg,"21","梁左上标注无法判断");
            } else {

                if (parameter.getUpLeftLable().indexOf("+") > -1) {
                    parameter.setUpLeftLable(repString(parameter.getUpLeftLable()));
                    String[] strings1 = parameter.getUpLeftLable().split(reg);
//                    System.out.println("strings1=========>>" + Arrays.toString(strings1));
                    n1 = Integer.parseInt(strings1[0].trim());
                    d1 = Integer.parseInt(strings1[1].trim());
                    n11 = Integer.parseInt(strings1[2].trim());
                    d11 = Integer.parseInt(strings1[3].trim());

                } else {
                    parameter.setUpLeftLable(repString(parameter.getUpLeftLable()));
                    String[] strings1 = parameter.getUpLeftLable().split(reg);
//                    System.out.println("strings1=========>>" + Arrays.toString(strings1));
                    n1 = Integer.parseInt(strings1[0].trim());
                    d1 = Integer.parseInt(strings1[1].trim());

                }
            }

        }

        //判断 跨中上标注 不为空 且 长度>0 且 不包含@符号 且 不存在x符号
        if(parameter.getUpMidLable()!=null && parameter.getUpMidLable().trim().length()>0
                && parameter.getUpMidLable().indexOf("@") == -1
                && parameter.getUpMidLable().indexOf("x") == -1 && parameter.getUpMidLable().indexOf("X") == -1){
//            System.out.println("UpMidLable==========>>"+parameter.getUpMidLable());
            //替换不能是别的字符串
            parameter.setUpMidLable(rep(parameter.getUpMidLable()));
            if (inspectString(parameter.getUpMidLable()).length() > 0) {
                msg += "====>>梁跨中上标注无法判断";
                handleErrorMsg(wrongMsg,"23","梁跨中上标注无法判断");
            } else {

                if (parameter.getUpMidLable().indexOf("+") > -1) {
                    parameter.setUpMidLable(repString(parameter.getUpMidLable()));
                    String[] strings3 = parameter.getUpMidLable().split(reg);
//                    System.out.println("strings3=========>>" + Arrays.toString(strings3));
                    n3 = Integer.parseInt(strings3[0].trim());
                    d3 = Integer.parseInt(strings3[1].trim());
                    n33 = Integer.parseInt(strings3[2].trim());
                    d33 = Integer.parseInt(strings3[3].trim());

                } else {

                    parameter.setUpMidLable(repString(parameter.getUpMidLable()));
                    String[] strings3 = parameter.getUpMidLable().split(reg);
//                    System.out.println("strings3=========>>" + Arrays.toString(strings3));
                    n3 = Integer.parseInt(strings3[0].trim());
                    d3 = Integer.parseInt(strings3[1].trim());
                }
            }
        }

        //判断 梁右上标注 不为空 且 长度>0 且 不包含@符号 且 不存在x符号
        if(parameter.getUpRightLable()!=null && parameter.getUpRightLable().trim().length()>0
                && parameter.getUpRightLable().indexOf("@") == -1
                && parameter.getUpRightLable().indexOf("x") == -1 && parameter.getUpRightLable().indexOf("X") == -1){
//            System.out.println("UpRightLable==========>>"+parameter.getUpRightLable());
            //替换不能是别的字符串
            parameter.setUpRightLable(rep(parameter.getUpRightLable()));

            if (inspectString(parameter.getUpRightLable()).length() > 0) {
                msg += "====>>梁右上标注无法判断";
                handleErrorMsg(wrongMsg,"22","梁右上标注无法判断");
            } else {

                if (parameter.getUpRightLable().indexOf("+") > -1) {
                    parameter.setUpRightLable(repString(parameter.getUpRightLable()));
                    String[] strings2 = parameter.getUpRightLable().split(reg);
//                    System.out.println("strings2=========>>" + Arrays.toString(strings2));
                    n2 = Integer.parseInt(strings2[0].trim());
                    d2 = Integer.parseInt(strings2[1].trim());
                    n22 = Integer.parseInt(strings2[2].trim());
                    d22 = Integer.parseInt(strings2[3].trim());

                } else {
                    parameter.setUpRightLable(repString(parameter.getUpRightLable()));
                    String[] strings2 = parameter.getUpRightLable().split(reg);
//                    System.out.println("strings2=========>>" + Arrays.toString(strings2));
                    n2 = Integer.parseInt(strings2[0].trim());
                    d2 = Integer.parseInt(strings2[1].trim());

                }
            }
        }



//        System.out.println("n1=====>"+n1);
//        System.out.println("d1=====>"+d1);
//        System.out.println("n2=====>"+n2);
//        System.out.println("d2=====>"+d2);
//        System.out.println("n3=====>"+n3);
//        System.out.println("d3=====>"+d3);
//        System.out.println("n4=====>"+n4);
//        System.out.println("d4=====>"+d4);
//        System.out.println("n11=====>"+n11);
//        System.out.println("d11=====>"+d11);
//        System.out.println("n22=====>"+n22);
//        System.out.println("d22=====>"+d22);
//        System.out.println("n33=====>"+n33);
//        System.out.println("d33=====>"+d33);
//        System.out.println("n44=====>"+n44);
//        System.out.println("d44=====>"+d44);





        //获取宽度、高度
        String str1 = (String) parameter.getIntensiveLable().get(0);
        int statr = str1.indexOf(")");
        int end = str1.indexOf("x");



        String str11 =  str1.substring(statr+1,end).trim();
        String str12 =  str1.substring(end+1).trim();

        int wide = Integer.parseInt(str11);   //梁宽度
        int height =Integer.parseInt(str12);  //梁高度

        if(parameter ==null || parameter.getIntensiveLable().size() <2){
            //集中标注有问题，无法判断
            Map map = new HashMap();
            map.put("msg","集中标注获取不完整，无法判断");
            map.put("result",Arrays.toString(b1));

            return map;
        }
        //获取直径
        String str2 = (String) parameter.getIntensiveLable().get(1);

        str2 = str2.trim();
        str2 = rep(str2);

        parameter.getIntensiveLable().set(1,str2);


        int stirrupDiameterStartIndex = str2.indexOf("%%");  //截取箍筋直径的开始下标
        int stirrupDiameterEndIndex = str2.indexOf("@");    //截取箍筋直径的结束下标


        int d = 0;  //箍筋直径

//            //如果%%不存在【接收到的数据有问题，检索%%会返回下标为0】，如：intensive_lable=[KL8(2) 400x800, 8@100/200(4), 325+(112);625]，则从下标0开始截取
//        if(stirrupDiameterStartIndex == -1 || (stirrupDiameterEndIndex < 6 && stirrupDiameterStartIndex == 0)){
//            stirrupDiameterStartIndex = 1;
//            d = Integer.parseInt(str2.substring(stirrupDiameterStartIndex,stirrupDiameterEndIndex));  //箍筋直径
//        }else {
//
//            d = Integer.parseInt(str2.substring(stirrupDiameterStartIndex+5,stirrupDiameterEndIndex));  //箍筋直径
//        }
        d = Integer.parseInt(str2.substring(stirrupDiameterStartIndex+5,stirrupDiameterEndIndex));  //箍筋直径


//        System.out.println("wide===========>>"+wide);
//        System.out.println("height===========>>"+height);
//        System.out.println("d===========>>"+d);

        double ret1 = -1;  //左支座配筋面积
        double ret2 = -1;  //左支座配筋率
        double ret3 = -1;  //右支座配筋面积
        double ret4 = -1;  //右支座配筋率
        double ret5 = -1;  //跨中上支座配筋面积
        double ret6 = -1;  //跨中上支座配筋率
        double ret7 = -1;  //跨中下支座配筋面积
        double ret8 = -1;  //跨中下支座配筋率





        if(n1>0 && d1>0){

            if(n11>0 && d11>0){


                ret1 = add(formula1(n1,d1),formula1(n11,d11));
            }else {

                ret1 = formula1(n1,d1);
            }

            ret2 = formula2(ret1,wide,height);

        }
        if(n2>0 && d2>0){
            if(n22>0 && d22>0){
                ret3 = add(formula1(n2,d2),formula1(n22,d22));
            }else {
                ret3 = formula1(n2,d2);
            }
            ret4 = formula2(ret3,wide,height);
        }

        if(n3>0 && d3>0){

            if(n33>0 && d33>0){
                ret5 = add(formula1(n3,d3),formula1(n33,d33));
            }else {
                ret5 = formula1(n3,d3);
            }
            ret6 = formula2(ret5,wide,height);
        }

        if(n4>0 && d4>0){
            if(n44>0 && d44>0){
                ret7 = add(formula1(n4,d4),formula1(n44,d44));
            }else {
                ret7 = formula1(n4,d4);
            }
            ret8 = formula2(ret7,wide,height);
        }

//        System.out.println("ret1==>"+ret1);
//        System.out.println("ret2==>"+ret2);
//        System.out.println("ret3==>"+ret3);
//        System.out.println("ret4==>"+ret4);
//        System.out.println("ret5==>"+ret5);
//        System.out.println("ret6==>"+ret6);
//        System.out.println("ret7==>"+ret7);
//        System.out.println("ret8==>"+ret8);


        //受压区高度
        double x1 = -1;  //左支座受压区高度
        double x2 = -1;  //右支座受压区高度
        if(ret1>0 && ret7>0){

            x1 = formula3(ret1,rebar_tensile_param,ret7,rebar_resist_param,wide,height,concrete_resist_param);
        }

        if(ret3>0 && ret7>0){
            x2 = formula3(ret3,rebar_tensile_param,ret7,rebar_resist_param,wide,height,concrete_resist_param);
        }


//        System.out.println("x1==>"+x1);
//        System.out.println("x2==>"+x2);



//        double ret1 左支座配筋面积
//        double ret2 左支座配筋率
//        double ret3 右支座配筋面积
//        double ret4 右支座配筋率
//        double ret5 跨中上支座配筋面积
//        double ret6 跨中上支座配筋率
//        double ret7 跨中下支座配筋面积
//        double ret8 跨中下支座配筋率
//        d 当前箍筋直径



        String str = "";
        DecimalFormat df = new DecimalFormat("0.00000%");
        //判断结果
        if( d < min_dia){
            str = "箍筋直径小于抗震等级对应最小值("+min_dia+");";
            msg += str;
            handleErrorMsg(wrongMsg,"413",str);

            b[9] = false;
        }

        if(ret2 > 0){

            if(ret2 < min_steel_ratio){

                str = "左支座配筋率("+df.format(ret2)+")小于最小配筋率("+df.format(min_steel_ratio)+");";
                msg += str;
                handleErrorMsg(wrongMsg,"211",str);
                b[0] = false;
            }else if (ret2 > 0.025){
                str = "左支座配筋率("+df.format(ret2)+")不应大于2.5%;";
                msg += str;
                handleErrorMsg(wrongMsg,"212",str);
                b[1] = false;
            }else if(ret2 > 0.02 && ret2 < 0.025){

                if(d<(min_dia + 2)){
                    str = "箍筋直径应大于等于"+df.format((min_dia + 2))+",当前箍筋直径为("+df.format(d)+");";
                    msg += str;
                    handleErrorMsg(wrongMsg,"213",str);
                    b[9] = false;
                }
            }
        }

        if(ret4 > 0){
            if(ret4 < min_steel_ratio){
                str = "右支座配筋率("+df.format(ret4)+")小于最小配筋率("+df.format(min_steel_ratio)+");";

                msg += str;
                handleErrorMsg(wrongMsg,"411",str);
                b[2] = false;
            }else if (ret4 > 0.025 ){
                str = "右支座配筋率("+df.format(ret4)+")不应大于2.5%;";
                msg += str;
                handleErrorMsg(wrongMsg,"412",str);
                b[3] = false;
            }else if(ret4 > 0.02 && ret4 < 0.025){
                if(d < min_dia + 2){

                    str = "箍筋直径应大于等于"+df.format((min_dia + 2))+",当前箍筋直径为("+df.format(d)+");";
                    msg += str;
                    handleErrorMsg(wrongMsg,"413",str);

                    b[9] = false;
                }
            }
        }


        if(ret8 > 0){
            if(ret8 < midspan_min_steel_ratio){

                str = "跨中下配筋率("+df.format(ret8)+")小于最小配筋率("+df.format(midspan_min_steel_ratio)+");";
                msg += str;
                handleErrorMsg(wrongMsg,"8",str);

                b[4] = false;
            }
        }

        if(seismic_param == 1) {

            if(ret7 > 0 && ret1 >0){
                if(divide(ret7,ret1) < 0.5){

                    str = "左支座 梁端截面的底面和顶面纵向钢筋配筋量的比值("+doDecimal(divide(ret7,ret1))+")，一级不应小于0.5;";
                    msg += str;
                    handleErrorMsg(wrongMsg,"11",str);
                    b[11] = false;

                }
            }

            if(ret7 > 0 && ret3 >0){
                if(divide(ret7,ret3) < 0.5){

                    str = "右支座 梁端截面的底面和顶面纵向钢筋配筋量的比值("+doDecimal(divide(ret7,ret3))+")，一级不应小于0.5;";
                    msg += str;
                    handleErrorMsg(wrongMsg,"11",str);
                    b[12] = false;

                }
            }
//
//            if(ret7>0 && (ret1>0 || ret3>0)){
//                double dd1 = divide(ret7,getMax(ret1,ret3));
//
//                if (dd1>0 && dd1 < 0.5) {
//
//                    str = "【抗震规范6.3.3-2条款判断】梁端截面的底面和顶面纵向钢筋配筋量的比值("+df.format(dd1)+")，一级不应小于0.5;";
//                    msg += str;
//                    handleErrorMsg(wrongMsg,"11",str);
//                }
//            }


            if(x1>0.25 | x2>0.25){
                str = "混凝土受压区高度不符合要求。";
                msg += str;
                handleErrorMsg(wrongMsg,"12",str);

                b[8] = false;
            }

        }


        if(seismic_param == 2 || seismic_param == 3) {


            if(ret7 > 0 && ret1 >0){
                if(divide(ret7,ret1) < 0.3){

                    str = "左支座 梁端截面的底面和顶面纵向钢筋配筋量的比值("+doDecimal(divide(ret7,ret1))+")，二三级不应小于0.3;";
                    msg += str;
                    handleErrorMsg(wrongMsg,"11",str);

                    b[11] = false;

                }
            }

            if(ret7 > 0 && ret3 >0){
                if(divide(ret7,ret3) < 0.3){

                    str = "右支座 梁端截面的底面和顶面纵向钢筋配筋量的比值("+doDecimal(divide(ret7,ret3))+")，二三级不应小于0.3;";
                    msg += str;
                    handleErrorMsg(wrongMsg,"11",str);

                    b[12] = false;

                }
            }

//            if(ret7>0 && (ret1>0 || ret3>0)){
//
//                double dd2 = divide(ret7,getMax(ret1,ret3));
//                if (dd2>0 && dd2 < 0.3) {
//                    str = "【抗震规范6.3.3-2条款判断】梁端截面的底面和顶面纵向钢筋配尽量的比值("+dd2+"),二三级不应小于0.3;";
//                    msg += str;
//                    handleErrorMsg(wrongMsg,"11",str);
//                }
//            }

            if(x1>0.35 | x2>0.35){
                str = "混凝土受压区高度不符合要求。";
                msg += str;
                handleErrorMsg(wrongMsg,"2",str);

                b[8] = false;
            }
        }





        if(msg.length()>0){
            msg += "";

        }
        System.out.println("结果===>>");
        Map<String,Object> rets = new LinkedHashMap<>();
        rets.put("leftP",ret2);   //左支座配筋率
        rets.put("rightP",ret4);  //右支座配筋率
        rets.put("upMidP",ret6);  //跨中上支座配筋率
        rets.put("downMidP",ret8);  //跨中下支座配筋率
        rets.put("d",d);  //箍筋直径

        Map map = new HashMap();
        map.put("msg",msg);
        map.put("rets",rets);
        map.put("result",Arrays.toString(b));

        return map;
    }


    /**
     * ST3中公用的错误信息记录方法
     * @param wrongMsg
     * @param retNum
     * @param msg
     * @return
     */
    public int handleErrorMsg(WrongMsg wrongMsg, String retNum, String msg){
        wrongMsg.setRetNum(retNum);
        wrongMsg.setWrongMsg(msg);
        if(wrongMsg.getRetNum() == null || wrongMsg.getRetNum().trim().length() ==0){
            return 0;
        }else {
            return wrongMsgService.addWrongMsg(wrongMsg);

        }
    }


    public static void main(String[] args) {
//        String str1 = "2%%23532;3%%12124";
//        String str2 = "2%%23532+3%%12124";
//        String str3 = "[2%%23532+3%%12124]";
//        String str4 = "[N2%%23532+3%%12124]";
//        String str5 = "[N2%%23532+(3%%12124)]";
//
//        System.out.println("str1===========>>"+repString(str1));
//        System.out.println("str2===========>>"+repString(str2));
//        System.out.println("str3===========>>"+repString(str3));
//        System.out.println("str4===========>>"+repString(str4));
//        System.out.println("str5===========>>"+repString(str5));

//        double ret1 = formula1(4,20);
//        double ret2 = formula2(ret1,300,700);
//        double ret3 = formula1(4,20);
//        double ret4 = formula2(ret3,300,700);
//        double ret5 = -1;
//        double ret6 = -1;
//        double ret7 = formula1(5,16);
//        double ret8 = formula2(ret7,300,700);
//        double x1 = formula3(ret1,270,ret7,270,300,700,14.3);
//        double x2 = formula3(ret3,270,ret7,270,300,700,14.3);
//        System.out.println("ret1====>>"+ret1);
//        System.out.println("ret2====>>"+ret2);
//        System.out.println("x1====>>"+x1);
//        System.out.println("x2====>>"+x2);


        int i = 58674;
        char c = (char)i;
        log.info("c={}",c);
        int j = (c+"").indexOf("\uE532");
        log.info("j==>{}",j);


    }
}
