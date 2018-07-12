package com.cj.stuser.service;


import com.cj.stcommon.entity.User;
import com.cj.stcommon.utils.entity.ApiResult;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {


    /**
     * 添加用户账号
     */
    public ApiResult addUser(User user);




    //登录
    public ApiResult login(User user);

    //注销
    public ApiResult logout(HttpSession session);



    //用户修改密码
    public ApiResult updateUserPass(Map map,HttpSession session);
}
