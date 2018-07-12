package com.cj.stshentu.controller;


import com.cj.core.aop.Log;
import com.cj.stcommon.utils.entity.ApiResult;
import com.cj.stshentu.entity.DLLCLass;
import com.cj.stshentu.entity.P2;
import com.cj.stshentu.service.HandleService;
import com.cj.stshentu.service.ReqMsgService;
import com.cj.stshentu.service.ShenTuService;
import com.cj.stshentu.service.StandardService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/st")
@Api(tags = "审图业务")
public class ShenTuController {

    @Autowired
    private ShenTuService shenTuService;

    @Autowired
    private ReqMsgService reqMsgService;



    @Autowired
    private StandardService standardService;

    @Autowired
    private HandleService handleService;

    Gson gson = new Gson();
    /**
     * 测试服务器是否正常工作
     * @return
     */
    @GetMapping("/test")
    @ApiOperation("测试服务器是否正常启动")
    public Object testST(){
        System.out.println("=========================================================");

        List list = new ArrayList();
        list.add(1);
        list.add(101);
        list.add(13);
        Map map1 = new HashMap();
        map1.put("msg","请求成功");
        map1.put("list",list);



        return map1;
    }

//
//    /**
//     * 测试单个对象
//     * @param parameter
//     * @return
//     */
//    @RequestMapping("/test2")
//    public Object test2ST(@RequestBody Parameter parameter) {
//        System.out.println("=========================================================");
//        System.out.println(parameter);
//        System.out.println("=========================================================");
//
//        Map map = new LinkedHashMap();
//        map.put("state",1);
//        map.put("msg",shenTuService.ST1(parameter));
//        map.put("parameter",parameter);
//
//
//        return map;
//    }
//
//
//    /**
//     * 测试对象集合
//     * 未提取公式
//     * @param parameterList
//     * @return
//     */
//    @RequestMapping("/test3")
//    public Object test3ST(@RequestBody List<Parameter> parameterList){
//        System.out.println("=========================================================");
//        System.out.println(parameterList);
//        System.out.println("=========================================================");
//        String message = "";
//
//        Map map = new LinkedHashMap();
//        List<Parameter> parameterList1 = new ArrayList<Parameter>();
//        int i = 0;
//
//        if(parameterList.size()>0){
//            System.out.println("集合长度===================>"+parameterList.size());
//
//            for (Parameter parameter:parameterList){
//                if(parameter.getIntensiveLable().get(0).toString().indexOf("x") > -1){
//
//                    message =  shenTuService.ST1(parameter);
//
//                    if(message.length()>0){
//                        i++;
//                        parameter.setMsg(message);
//                        parameterList1.add(parameter);
//                    }
//                }
//            }
//
//
//
//            message = "处理成功";
//
//            map.put("state",1);
//            map.put("message",message);
//            map.put("count1",i);
//            map.put("parameterList",parameterList1);
//        }else {
//            message = "没有接收到数据";
//
//            map.put("state",0);
//            map.put("message",message);
//            map.put("count",i);
//            map.put("parameterList","");
//        }
//
//        System.out.println("返回的集合长度===================>"+parameterList1.size());
//
//
//        return map;
//
//    }
//
//
//    /**
//     * 测试对象集合
//     * 已提取公式
//     * @param parameterList
//     * @return
//     */
//    @RequestMapping("/test4")
//    public Object test4ST(@RequestBody List<Parameter> parameterList){
//        long start = System.currentTimeMillis();
//
//        System.out.println("=========================================================");
//        System.out.println(parameterList);
//        System.out.println("=========================================================");
//        String message = "";
//
//        Map map = new LinkedHashMap();
//        List<Parameter> parameterList1 = new ArrayList<Parameter>();
//        int i = 0;
//
//        if(parameterList.size()>0){
//            System.out.println("集合长度===================>"+parameterList.size());
//
//            for (Parameter parameter:parameterList){
//                if(parameter.getIntensiveLable().get(0).toString().indexOf("x") > -1){
//
//                    message =  shenTuService.ST2(parameter);
//
//                    if(message.length()>0){
//                        i++;
//                        parameter.setMsg(message);
//                        parameterList1.add(parameter);
//                    }
//                }
//            }
//
//
//
//            message = "处理成功";
//
//            map.put("state",1);
//            map.put("message",message);
//            map.put("count",i);
//            map.put("parameterList",parameterList1);
//        }else {
//            message = "没有接收到数据";
//
//            map.put("state",0);
//            map.put("message",message);
//            map.put("count",i);
//            map.put("parameterList","");
//        }
//
//        System.out.println("返回的集合长度===================>"+parameterList1.size());
//
//        long end = System.currentTimeMillis();
//
//        System.out.println("耗时===================>"+(end-start)+"毫秒");
//        System.out.println("耗时===================>"+(end-start)/1000+"秒");
//
//
//        log.info("map={}",map);
//        return map;
//
//    }
//
//    /**
//     * 优化部分if语句
//     * 测试对象集合
//     * 已提取公式
//     * @param parameterList
//     * @return
//     */
//    @RequestMapping("/test5")
//    public Object test5ST(@RequestBody List<Parameter> parameterList){
//        long start = System.currentTimeMillis();
//        System.out.println("=========================================================");
//        System.out.println(parameterList);
//        System.out.println("=========================================================");
//        System.out.println("集合长度===================>"+parameterList.size());
//        //保存请求信息
//        ReqMsg reqMsg = new ReqMsg();
//        reqMsg.setDataCount(parameterList.size());  //请求数据长度
//        reqMsg.setCreateTime(new Date());  //请求时间
//
//        //保存请求信息
//        reqMsgService.addReqMsg(reqMsg);
//
//
//        String message = "";  //接收错误信息
//
//
//        Map map = new LinkedHashMap();  //存放返回值
//        Map map1 = new HashMap();  //存放ST3返回值
//        List<Parameter> parameterList1 = new ArrayList<Parameter>();  //存放回调信息
//        int i = 0;  //统计错误数
//        int j = 1;  //单次请求循环次数编号
//
//        if(parameterList.size()>0){
//
//            for (Parameter parameter:parameterList){
//                if(parameter.getIntensiveLable().get(0).toString().indexOf("x") > -1){
//
//                    map1 =  shenTuService.ST3(parameter,reqMsg.getReqId(),j);
//
//                    message = (String)map1.get("msg");
//                    if(message.length()>0){
//                        i++;
//                    }
//                    parameter.setMsg(message);
//                    parameter.setResult(map1.get("result"));
//                }else {
//                    parameter.setMsg("");
//                    parameter.setResult("");
//                }
//                parameterList1.add(parameter);
//                j++;
//            }
//            reqMsg.setErrorCount(i);
//            reqMsgService.updateReqMsg(reqMsg);
//
//
//            message = "处理成功";
//
//            map.put("state",1);
//            map.put("message",message);
//            map.put("count",i);
//            map.put("parameterList",parameterList1);
//        }else {
//            message = "没有接收到数据";
//
//            map.put("state",0);
//            map.put("message",message);
//            map.put("count",i);
//            map.put("parameterList","");
//        }
//
//        System.out.println("返回的集合长度===================>"+parameterList1.size());
//
//        long end = System.currentTimeMillis();
//
//        System.out.println("耗时===================>"+(end-start)+"毫秒");
//        System.out.println("耗时===================>"+(end-start)/1000+"秒");
//
//
//        log.info("map={}",map);
//        return map;
//
//    }
//
//
//
//    @RequestMapping("/testCrawler")
//    public Object testCrawler(String url){
//        WebPageSource webPageSource = new WebPageSource();
//        List<Standard> standards = webPageSource.test(url);
//        System.out.println("====================开始写入数据库=======================");
//        System.out.println(standards.size());
//        for (Standard standard:standards){
//            standardService.addStandard(standard);
//        }
//        return "完成";
//    }






    @PostMapping("/shenTu")
    @ApiOperation("传入识别到的JSON字符串进行处理")
    @Log(name = "审图  ==> json数据处理")
    public Map<String, Object> shenTu(@ApiParam(name = "json",value = "待处理数据",required = true)
                                @RequestBody String json, HttpSession session){
        System.out.println("==========================客户端传入======================================");
        System.out.println(json);

        System.out.println("==========================客户端传入======================================");

        //调用动态库解析数据
        long start = System.currentTimeMillis();

        ApiResult apiResult = null;


        List<P2> list=new ArrayList<P2>();
        Type type = new TypeToken<ArrayList<P2>>() {}.getType();

        list=gson.fromJson(json, type);


        DLLCLass dllClass = handleService.callJNI(list);
        System.out.println("================================DLLCLass================================================");
        System.out.println(dllClass);
        System.out.println("================================DLLCLass================================================");

        //调用审图业务处理结果
        apiResult = handleService.getResult(dllClass,apiResult,session);


        long end = System.currentTimeMillis();

        System.out.println("耗时===================>"+(end-start)+"毫秒");
        System.out.println("耗时===================>"+(end-start)/1000+"秒");




        return apiResult.toMap();

    }






}
