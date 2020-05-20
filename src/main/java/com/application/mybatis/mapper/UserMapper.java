package com.application.mybatis.mapper;

import com.application.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public User findByUserId(@Param("userId") String userId);
}
