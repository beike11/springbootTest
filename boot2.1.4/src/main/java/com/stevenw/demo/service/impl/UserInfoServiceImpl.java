package com.stevenw.demo.service.impl;

import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.dao.UserInfoView;
import com.stevenw.demo.mapper.UserMapper;
import com.stevenw.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stevenw
 * @date 2019/3/25
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserMapper userMapper;

    public static AtomicInteger num1;

    @Override
    @Cacheable(value = "allUser")
    public List<UserInfo> getAllUser() {
        System.err.println("载入缓存");
        return userMapper.getAll();
    }

    @Override
    @Transactional
    public int addUser(UserInfo userInfo) {
        Integer sort = (null == userMapper.getMaxSort())?0:userMapper.getMaxSort();
        if(null == num1){
            num1 = new AtomicInteger(sort);
        }
        System.err.println(num1);
        userInfo.setSort(num1.incrementAndGet());
        return userMapper.addUser(userInfo);
    }
}
