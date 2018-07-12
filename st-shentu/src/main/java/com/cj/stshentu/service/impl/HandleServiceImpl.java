package com.cj.stshentu.service.impl;

import com.cj.stcommon.utils.entity.ApiResult;
import com.cj.stcommon.utils.util.STAssistUtil;
import com.cj.stshentu.entity.*;
import com.cj.stshentu.service.HandleService;
import com.cj.stshentu.service.ReqMsgService;
import com.cj.stshentu.service.ShenTuService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 调用JNI
 * 处理判断结果
 */

@Service
@Transactional
public class HandleServiceImpl implements HandleService {

    @Autowired
    private ShenTuService shenTuService;

    @Autowired
    private ReqMsgService reqMsgService;

    public native String cadJNI(String polyline,String lable);

    Gson gson = new Gson();
    @Override
//    @Async("getAsyncExecutor")  // getAsyncExecutor 即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    public DLLCLass callJNI(List<P2> list) {

        System.load("D:/file/dll/caddll.dll");
        HandleServiceImpl handleService = new HandleServiceImpl();

        DLLCLass dllClass = new DLLCLass();
        for (P2 p : list){
            System.out.println("lable====>"+p.getLable());
            System.out.println("polyline====>"+p.getPolyline());
            String json = handleService.cadJNI(p.getPolyline(),p.getLable());
            System.out.println("====================================JNI解析结果================================================");
            System.out.println(json);
            System.out.println("====================================JNI解析结果================================================");
            //将json中的所有�替换掉
            json = STAssistUtil.repJson(json);

            DLLCLass dllcLass1 = gson.fromJson(json,DLLCLass.class);
            if(dllcLass1.getFinalBeam() != null && dllcLass1.getFinalBeam().size() > 0){
                for(Parameter parameter : dllcLass1.getFinalBeam()){
                    parameter.setSe(list.get(0).getSe());
                    parameter.setConcrete(list.get(0).getConcrete());
                    parameter.setRebar(list.get(0).getRebar());

                    List<Parameter> parameterList = dllClass.getFinalBeam();
                    if(parameterList == null){
                        parameterList = new ArrayList<Parameter>();
                    }
                    parameterList.add(parameter);
                    dllClass.setFinalBeam(parameterList);
                }

            }
            if(dllcLass1.getMatchInfoJudge() != null && dllcLass1.getMatchInfoJudge().size() > 0){
                for (SlashmatchInfo slashmatchInfo : dllcLass1.getMatchInfoJudge()){
                    List<SlashmatchInfo> slashmatchInfos = dllClass.getMatchInfoJudge();
                    if(slashmatchInfos == null){
                        slashmatchInfos = new ArrayList<SlashmatchInfo>();
                    }
                    slashmatchInfos.add(slashmatchInfo);
                    dllClass.setMatchInfoJudge(slashmatchInfos);
                }

            }

            if(dllcLass1.getSlashmatchInfo() != null && dllcLass1.getSlashmatchInfo().size() > 0){
                for (SlashmatchInfo slashmatchInfo : dllcLass1.getSlashmatchInfo()){
                    List<SlashmatchInfo> slashmatchInfos = dllClass.getSlashmatchInfo();
                    if(slashmatchInfos == null){
                        slashmatchInfos = new ArrayList<SlashmatchInfo>();
                    }
                    slashmatchInfos.add(slashmatchInfo);
                    dllClass.setSlashmatchInfo(slashmatchInfos);
                }

            }
        }


        return dllClass;
    }


    @Override
    public ApiResult getResult(DLLCLass dllClass, ApiResult apiResult, HttpSession session ) {


        if (dllClass == null  || dllClass.getFinalBeam() ==null) {
            apiResult = ApiResult.NO_know;
           return apiResult;

        }

        List<Parameter> parameterList = dllClass.getFinalBeam();


        System.out.println("=========================================================");
        System.out.println(parameterList);
        System.out.println("=========================================================");
        System.out.println("集合长度===================>" + parameterList.size());


        //保存请求信息
        ReqMsg reqMsg = new ReqMsg();
        reqMsg.setUserId((Long) session.getAttribute("userId"));
        reqMsg.setDataCount(parameterList.size());  //请求数据长度
        reqMsg.setCreateTime(new Date());  //请求时间

        //保存请求信息
        reqMsgService.addReqMsg(reqMsg);


        String message = "";  //接收错误信息


        Map map = new LinkedHashMap();  //存放返回值
        Map map1 = new HashMap();  //存放ST3返回值
        List<Parameter> parameterList1 = new ArrayList<Parameter>();  //存放回调信息
        int i = 0;  //统计错误数
        int j = 1;  //单次请求循环次数编号

        if (parameterList.size() > 0) {

            for (Parameter parameter : parameterList) {
                if (parameter.getIntensiveLable().get(0).toString().indexOf("x") > -1) {
                    map1 = shenTuService.ST3(parameter, reqMsg.getReqId(), j);
                    message = (String) map1.get("msg");
                    if (message.length() > 0) {
                        i++;
                    }
                    parameter.setMsg(message);
                    parameter.setRets((Map) map1.get("rets"));
                    parameter.setResult(map1.get("result"));

                } else {
                    parameter.setMsg("");
                    parameter.setResult("");
                }
                parameterList1.add(parameter);
                j++;
            }
            reqMsg.setErrorCount(i);
            reqMsgService.updateReqMsg(reqMsg);
            map.put("count", i);
            map.put("reqId",reqMsg.getReqId());
            dllClass.setFinalBeam(parameterList1);
            map.put("dllClass", dllClass);

            System.out.println("返回的集合长度===================>" + parameterList1.size());
            apiResult = ApiResult.SUCCESS_know;
            apiResult.setData(map);
            return apiResult;
        } else {
            apiResult = ApiResult.NO_data;
            return apiResult;
        }
    }
}
