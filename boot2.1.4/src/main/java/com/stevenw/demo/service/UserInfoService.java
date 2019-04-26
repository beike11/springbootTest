package com.stevenw.demo.service;

import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.dao.UserInfoView;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author stevenw
 * @date 2019/3/25
 */

public interface UserInfoService {
    List<UserInfo> getAllUser();
    int addUser(UserInfo userInfo);
    void deleteAllUserCache();
    int updateUserByUid();
    int updatePwd(Integer uid,String pwd);
    UserInfo getUserInfo(Integer uid);
}
