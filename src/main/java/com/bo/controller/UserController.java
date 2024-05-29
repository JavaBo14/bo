package com.bo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.common.BaseResponse;
import com.bo.common.ErrorCode;
import com.bo.common.ResultUtil;
import com.bo.exception.BusinessException;
import com.bo.mapper.UserMapper;
import com.bo.model.domain.User;
import com.bo.model.request.UserLoginRequest;
import com.bo.model.request.UserRegisterRequest;
import com.bo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.bo.constant.UserConstant.ADMIN_ROLE;
import static com.bo.constant.UserConstant.USER_LOGIN_STATE;
import static com.sun.javafx.font.FontResource.SALT;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    private static final String SALT = "wjb";
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
    @GetMapping("findById/{id}")
    public BaseResponse<User> findByid(@PathVariable Integer id){
        if(id == null || id<1){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        User users = userMapper.selectOne(queryWrapper);
        if(users == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtil.success(users);
    }
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> userDelete(@PathVariable long id,HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id<1){
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtil.success(b);
    }
    //待优化
    @PostMapping("/save")
    public BaseResponse<Boolean> userUpdate(@RequestBody User user,HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (user == null) {
            return null;
        }
        Long userId = user.getId();
        if(userId == null){
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //从数据库里找是否有一样的用户名
            queryWrapper.eq("userAccount", user.getUserAccount());
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2.加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + user.getUserPassword()).getBytes());
            // 3.插入数据
            User newUser = new User();
            newUser.setUserName(user.getUserName());
            newUser.setUserAccount(user.getUserAccount());
            newUser.setUserPassword(encryptPassword);
            boolean saveResult = userService.save(newUser);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            return ResultUtil.success(true);
        }else {
            boolean updateResult = userService.updateById(user);
            return ResultUtil.success(updateResult);
        }
    }
//        boolean b = user.getId() != null ? userService.updateById(user) : userService.save(user);

    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;

        }
        return true;
    }
}
