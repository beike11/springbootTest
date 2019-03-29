package com.stevenw.demo.service.impl;

import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.dao.UserInfoView;
import com.stevenw.demo.mapper.UserMapper;
import com.stevenw.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author stevenw
 * @date 2019/3/25
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserInfo> getAllUser() {
        return userMapper.getAll();
    }
}
