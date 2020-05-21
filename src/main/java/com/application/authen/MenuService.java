package com.application.authen;

import com.application.model.User;
import com.application.mybatis.mapper.MenuMapper;
import com.application.mybatis.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    public List<Menu> getMenuFromUserId(String userId){
        User user = userMapper.findByUserId(userId);
        if(StringUtils.equalsIgnoreCase(user.getAuthorities(),"All")){
            return menuMapper.findAccessibleResourcesByScreenCds(null);
        }else{
            String []accessibleScreenArr = StringUtils.split(StringUtils.replace(user.getAuthorities()," ",""),",");
            return menuMapper.findAccessibleResourcesByScreenCds(accessibleScreenArr);
        }
    }
}
