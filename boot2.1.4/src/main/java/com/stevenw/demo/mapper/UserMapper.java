package com.stevenw.demo.mapper;

import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.dao.UserInfoView;
import org.apache.ibatis.annotations.*;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author stevenw
 * @date 2019/3/25
 */
public interface UserMapper {
    @Select("select * from user_info")
    @Results({
            @Result(property = "uid",column = "uid")
    })
    List<UserInfo> getAll();

    @Insert("insert into user_info (account,name,pwd,sort) values(#{userInfo.account},#{userInfo.name},#{userInfo.pwd},#{userInfo.sort})")
    int addUser(@Param("userInfo") UserInfo userInfo);

    @Select("SELECT MAX(sort) from user_info")
    Integer getMaxSort();
}
