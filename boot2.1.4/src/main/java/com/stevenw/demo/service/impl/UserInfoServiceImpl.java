package com.stevenw.demo.service.impl;

import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.mapper.UserInfoMapper;
import com.stevenw.demo.mapper.UserMapper;
import com.stevenw.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private UserInfoMapper userInfoMapper;


    public static AtomicInteger num1;

    @Override
    @Cacheable(value = "allUser")
    public List<UserInfo> getAllUser() {
        System.err.println("载入缓存");
        return userMapper.getAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUser(UserInfo userInfo) {
        Integer sort = (null == userMapper.getMaxSort())?0:userMapper.getMaxSort();
        if(null == num1){
            num1 = new AtomicInteger(sort);
        }
        System.err.println(num1);
        userInfo.setSort(num1.incrementAndGet());
        return userMapper.addUser(userInfo);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "allUser",allEntries = true),@CacheEvict(value = "user",allEntries = true)})
    public void deleteAllUserCache() {

    }

    @Override
    public int updateUserByUid() {
        return 0;
    }

    @Override
    @CacheEvict(value = "user",key = "#uid")
    public int updatePwd(Integer uid,String pwd) {
        return userMapper.updatePwd(uid,pwd);
    }

    @Override
    @Cacheable(value = "user",key = "#id")
    public UserInfo getUserInfo(Integer id) {
        System.err.println("获取user的id为: "+id);
        UserInfo userInfo = userInfoMapper.getUserInfo(id);
        return userInfo;
    }

}
