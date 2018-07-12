package com.cj.stuser.controller;

import com.cj.core.aop.Log;
import com.cj.stcommon.utils.entity.ApiResult;
import com.cj.stcommon.entity.User;
import com.cj.stuser.service.UserService;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "用户端")
public class UserController {

    @Autowired
    private UserService userService;

    Gson gson = new Gson();
    /**
     * =========================================用户端 维护===========================================================
     */

    /**
     * 添加用户账号
     */
    @ApiOperation("新增用户")
    @Log(name = "用户端 ==> 新增用户")
    @PostMapping("/addUser")
    public Map addUser(@ApiParam(name = "user",value = "用户信息",required = true)
                           @RequestBody User user){

        ApiResult apiResult = userService.addUser(user);

        return apiResult.toMap();

    }



    /**
     * =======================================登录、注销=============================================
     */

    /**
     * 登录
     */
    @ApiOperation("登录")
    @Log(name = "用户 ==> 登录")
    @PostMapping("/login")
    public Map<String, Object> login(@ApiParam(name = "user",value = "userName=用户名，userPass=密码",required = true)
                                         @RequestBody User user){
        ApiResult apiResult = userService.login(user);
        return apiResult.toMap();
    }


    /**
     * 注销
     */
    @ApiOperation("注销")
    @Log(name = "用户 ==> 注销")
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpSession session){

        ApiResult apiResult = userService.logout(session);
        return apiResult.toMap();
    }

    /**
     * ========================================用户 维护===================================================
     */

    /**
     * 用户自己修改密码
     */
    @ApiOperation("修改密码")
    @Log(name = "用户 ==> 修改密码")
    @PutMapping("/updateUserPass")
    public Map updateUserPass(@ApiParam(name = "json",value = "oldUserPass=原密码，newUserPass=新密码")
                                  @RequestBody String json,
                              HttpSession session){
        Map map = gson.fromJson(json,Map.class);

        ApiResult apiResult = userService.updateUserPass(map,session);

        return apiResult.toMap();
    }

}
