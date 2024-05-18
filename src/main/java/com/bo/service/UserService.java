package com.bo.service;

import com.bo.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author Bo
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-05-18 13:11:39
*/
public interface UserService extends IService<User> {

    long userRegister(String userName,String userAccount,String userPassword,String checkPassword);
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
    User getSafetyUser(User originUser);
    int userLogout(HttpServletRequest request);

}
