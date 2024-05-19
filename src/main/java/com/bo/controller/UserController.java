package com.bo.controller;


import com.bo.common.BaseResponse;
import com.bo.common.ErrorCode;
import com.bo.common.ResultUtil;
import com.bo.exception.BusinessException;
import com.bo.model.domain.User;
import com.bo.model.request.UserLoginRequest;
import com.bo.model.request.UserRegisterRequest;
import com.bo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.bo.constant.UserConstant.ADMIN_ROLE;
import static com.bo.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userName = userRegisterRequest.getUserName();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        //  再校验一次的目的是：对请求参数本身的校验，不涉及业务逻辑，而service层是对业务逻辑的校验
        if (StringUtils.isAnyBlank(userName, userAccount, userPassword,
                checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userName, userAccount, userPassword, checkPassword);
        return ResultUtil.success(result);
    }

    @PostMapping("/login")

    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtil.error(ErrorCode.PARAMS_ERROR);
        }


        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //  再校验一次的目的是：对请求参数本身的校验，不涉及业务逻辑，而service层是对业务逻辑的校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtil.error(ErrorCode.PARAMS_ERROR);
        }


        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtil.success(user);
    }

    @PostMapping("/Logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtil.success(result);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody long id,HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id<1){
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtil.success(b);
    }
    @PostMapping("/save")
    public BaseResponse<Boolean> userUpdate(@RequestBody User user,HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (user == null) {
            return null;
        }
        boolean b = user.getId() != null ? userService.updateById(user) : userService.save(user);
        return ResultUtil.success(b);
    }

    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;

        }
        return true;
    }
}
