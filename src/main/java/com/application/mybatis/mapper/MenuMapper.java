package com.application.mybatis.mapper;

import com.application.authen.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> findAccessibleResourcesByScreenCds(@Param("accessibleScreenArr") String []accessibleScreenArr);
}
