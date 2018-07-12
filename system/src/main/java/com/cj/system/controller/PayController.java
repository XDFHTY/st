package com.cj.system.controller;


import com.cj.core.aop.Log;
import com.cj.system.utils.alipay.util.AlipayNotify;
import com.cj.system.utils.pay.PayUtils;
import com.cj.system.utils.wxpay.PayHelper;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 支付
 */
@ResponseBody
@Controller
@RequestMapping
@Api(tags = "支付信息处理")
@ApiIgnore
public class PayController {

    /**
     * =====================================================支付宝=============================================
     */
    //http://139.224.42.24:8899
    //http://tt.tunnel.2bdata.com
    public static String alinotify = "/api/json/money/alipay/succ";

    @GetMapping("/alipaySucc")
    @Log(name = "支付宝支付回调地址")
    @Transactional(rollbackFor = Throwable.class)
    public String alipaySucc(HttpServletRequest request) throws IOException {
        System.out.println("支付宝回调");
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //String out_trade_no = request.getParameter("out_trade_no");// 商户订单号

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        //String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号

        //String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                String total_fee = params.get("total_fee");
                Long userId = Long.valueOf(params.get("out_trade_no").split("O")[0]);
                //updateUserPay(userId, total_fee);
                return "success";
            }
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            System.out.println("+++++++++++++++++++=验证失败");
            return "fail";
        }
        return "fail";
    }

     /*
    private void updateUserPay(Long userId, String total_fee) {
        User user = userService.selectByPrimaryKey(userId);

        if (user != null) {
            user.setPassword(null);
            //余额
            BigDecimal account = user.getAccount() == null ? BigDecimal.valueOf(0.00) : user.getAccount();
            user.setAccount(account.add(BigDecimal.valueOf(Double.valueOf(total_fee))));
            //积分
            //BigDecimal integral = user.getIntegral() == null ? BigDecimal.valueOf(0.00) : user.getIntegral();
            //user.setIntegral(integral.add(BigDecimal.valueOf(Double.valueOf(total_fee))));
            int i = userService.updateByPrimaryKeySelective(user);
            if (i <= 0) {
                log.debug("更新用户出错");
            }

            //充值记录
            MoneyRecord moneyRecord = new MoneyRecord();
            moneyRecord.setTitle("充值");
            moneyRecord.setType(1);
            moneyRecord.setMoneyChange("+" + total_fee);
            moneyRecord.setUserId(userId);

            moneyRecordService.insertJson(moneyRecord);
            log.debug("记录添加成功");
        }
    }
　　*/

    @GetMapping("/getAlipayOrderSign")
    @Log(name = "支付宝充值")
    @Transactional(rollbackFor = Throwable.class)
    public String getAlipayOrderSign(Double money, HttpServletRequest request) throws Exception {
        String sym = request.getRequestURL().toString().split("/api/")[0];
        Long userId = 33l;
        String str;
        String trade_no0 = userId + "O" + UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        String trade_no = trade_no0.substring(0,32);
        String stringStringMap = null;
        stringStringMap =  PayUtils.AliPay(
                trade_no,
                money, //金额
                "纺织达人充值",   //订单名称
                "纺织达人充值",   //订单说明
                sym + alinotify //回调哪个方法
        );
        if (stringStringMap != null) {
            str = stringStringMap.substring(17,stringStringMap.length()-3);
        }

        return stringStringMap;

    }


    /**
     * =======================================微信====================================================
     */


    @RequestMapping("/pay")
    @ResponseBody
    public Map<String,String> pay(HttpServletRequest request){
        try{
            //此处通过request获取相应的参数
            //....................

            //"appId","mchId","mchKey"初始化支付对象
            PayHelper payHelper = new PayHelper("appId","mchId","mchKey");

            //公众号统一支付，获取预支付id
            String prepayId = payHelper.unifiedH5(
                    "openId",100,"tradeNo","notifyUrl","ip","body",new HashMap<String,String>());

            //生成包括签名的map对象到前端（json）,有公众号中完成支付
            Map<String,String> json = payHelper.getClientPrepayMap(prepayId);

            return json;

        }catch(Exception e){
            Map<String,String> errorMap = new HashMap<String, String>();
            errorMap.put("errcode","-1");
            errorMap.put("msg","预支付失败");
            return errorMap;
        }
    }

}