package com.stevenw.demo.mapper;

import com.stevenw.demo.dao.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
}
