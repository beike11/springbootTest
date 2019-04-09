package com.stevenw.demo.web;

import com.stevenw.demo.annotation.JSON;
import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.dao.UserInfoView;
import com.stevenw.demo.service.UserInfoService;
import com.stevenw.demo.thread.TestVolatile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author stevenw
 * @date 2019/3/22
 */
@RestController
public class HelloController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    TestVolatile testVolatile;
    /**
     * 测试动态jackson
     * @return
     */
    @GetMapping("/hello")
    @JSON(type = UserInfo.class,filter = "pwd,name")
    public List<UserInfo> index(){
        System.err.println(userInfoService);
        List<UserInfo> users = userInfoService.getAllUser();
        System.err.println(users.size());
        return users;
    }
    @GetMapping("/addUser")
    public String addAll() throws InterruptedException{
        testVolatile.addUser();
        return "success";
    }
}
