package com.cj.stadmin.service.impl;


import com.cj.stadmin.mapper.AdminMapper;
import com.cj.stadmin.service.AdminService;
import com.cj.stcommon.entity.Admin;
import com.cj.stcommon.jwt.JwtUtil;
import com.cj.stcommon.utils.entity.ApiResult;
import com.cj.stcommon.utils.entity.MemoryData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

import static com.cj.stcommon.utils.md5.Md5Utils.MD5Encode;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;






    //添加账号
    @Override
    public ApiResult addAdmin(Admin admin) {
        ApiResult apiResult;
        //检查账号是否已存在
        Admin oldAdmin = adminMapper.findAdminByName(admin);

        long time = System.currentTimeMillis();

        //账号已存在
        if(oldAdmin != null){
            apiResult = ApiResult.account_repeat;
            return apiResult;
        }


        //生成盐值
        String uuid = UUID.randomUUID().toString();
        admin.setSaltVal(uuid);
        admin.setAdminType("1");  //系统管理员
        admin.setCreateTime(new Date(time));
        int i = adminMapper.insertSelective(admin);

        //加密密码，MD5（主键+盐值+密码）
        String adminPass = MD5Encode(admin.getAdminId()+admin.getSaltVal()+admin.getAdminPass(),"UTF-8",true);

        admin.setAdminPass(adminPass);

        //更新密码
       int j = adminMapper.updateByPrimaryKey(admin);

       if(i ==1 && j==1){
           apiResult = ApiResult.ADD_SUCCESS;
           return apiResult;
       }else {
           apiResult = ApiResult.ADD_FAIL;
           return apiResult;
       }

    }

    @Override
    public int updateAdmin(Admin admin) {
        ApiResult apiResult;
        Admin oldAdmin = adminMapper.findAdminByName(admin);
        String newAdminPass = MD5Encode(oldAdmin.getAdminId()+oldAdmin.getSaltVal()+admin.getAdminPass(),"UTF-8",true);
        oldAdmin.setAdminPass(newAdminPass);
        int i = adminMapper.updateAdminPass(admin);

        return i;
    }

    @Override
    public int delete(Long adminId) {
        return adminMapper.deleteAdmin(adminId);
    }

    @Override
    public List<Admin> findAllAdmin() {
        return adminMapper.findAllAdmin();
    }


    //登录
    @Override
    public ApiResult ifLogin(Admin admin) {
        ApiResult apiResult = null;

        Admin oldAdmin = adminMapper.findAdminByUserName(admin);

        long time = System.currentTimeMillis();

        Map map = new HashMap();
        if(oldAdmin == null){
            apiResult = ApiResult.account_not_find;

            return  apiResult;

        }else if(oldAdmin.getAdminPass().equals(MD5Encode(oldAdmin.getAdminId()+oldAdmin.getSaltVal()+admin.getAdminPass(),"UTF-8",true))){  //密码正确
            String token = "";

            Long adminId = oldAdmin.getAdminId();
            String adminName = oldAdmin.getAdminName();

            String tokenKey = "a"+adminName;



            //设置token，有效期
            token = JwtUtil.getAdminToken(time,adminId,adminName,oldAdmin.getAdminType());



            if (!MemoryData.getTokenMap().containsKey(tokenKey)) { //不存在，首次登陆，放入Map
                MemoryData.getTokenMap().put(tokenKey,token);  //添加adminId-token
            }else if( MemoryData.getTokenMap().containsKey(tokenKey) && !StringUtils.equals(token, MemoryData.getTokenMap().get(tokenKey))){
                MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
                MemoryData.getTokenMap().put(tokenKey,token);  //添加adminId-token
            }



            map.put("token",token);
            map.put("issuedAt",new Date(time));
//            map.put("expiration",new Date(time+1000*60*60*2));
            apiResult = ApiResult.SUCCESS;
            apiResult.setData(map);
            System.out.println(apiResult);
            return apiResult;
        }else {

            apiResult = ApiResult.account_not_find;
            return  apiResult;

        }


    }

    //注销
    @Override
    public int ifLogout(HttpSession session) {
        String adminName = (String) session.getAttribute("adminName");
        String tokenKey = "a"+adminName;
        if(MemoryData.getTokenMap().containsKey(tokenKey)){
            MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
        }
        return 1;
    }



}