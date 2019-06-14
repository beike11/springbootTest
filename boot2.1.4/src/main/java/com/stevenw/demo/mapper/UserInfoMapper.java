package com.stevenw.demo.mapper;

import com.stevenw.demo.dao.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author stevenw
 * @date 2019/4/26
 */
public interface UserInfoMapper {
    UserInfo getUserInfo(@Param("uid") Integer uid);
}
