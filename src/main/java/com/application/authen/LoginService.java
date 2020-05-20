package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.model.User;
import com.application.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public String login(Login login) throws ApplicationException{
        User user = userMapper.findByUserId(login.getUsername());
        if(user == null){
            throw new ApplicationException("ML0001000001ERR");
        }
        log.debug("login.getPassword() >>> "+login.getPassword());
        log.debug("user.getPassword() >>> "+user.getPassword());
        if(!passwordEncoder.matches(login.getPassword(),user.getPassword())){
            throw new ApplicationException("ML0001000002ERR");
        }
        return tokenUtil.generateJWT(user);
    }
}
