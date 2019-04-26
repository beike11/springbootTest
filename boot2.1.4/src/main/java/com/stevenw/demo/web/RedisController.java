package com.stevenw.demo.web;

import com.stevenw.demo.annotation.JSON;
import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author stevenw
 * @date 2019/4/23
 */
@RestController
public class RedisController {
    @Autowired
    private UserInfoService userInfoService;
    @GetMapping("/redis/getAllUser")
    public List<UserInfo> getAllUserInfo(){
        return userInfoService.getAllUser();
    }

    @GetMapping("/redis/deAllUserCache")
    @JSON(type = UserInfo.class,filter = "pwd")
    public String deleteAllUserCache(){
        userInfoService.deleteAllUserCache();
        return "success";
    }

    @GetMapping("/redis/getUserInfo")
    public UserInfo getUserInfo(Integer uid){
        uid = 106;
        UserInfo userInfo = userInfoService.getUserInfo(uid);
        return userInfo;
    }

    @GetMapping("/redis/updatePwd")
    public String updatePwd(Integer uid,String pwd){
        userInfoService.updatePwd(uid,pwd);
        return "success";
    }
}
