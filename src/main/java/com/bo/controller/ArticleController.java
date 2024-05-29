package com.bo.controller;

import com.bo.common.BaseResponse;
import com.bo.common.ErrorCode;
import com.bo.common.ResultUtil;
import com.bo.constant.UserConstant;
import com.bo.exception.BusinessException;
import com.bo.mapper.ArticleMapper;
import com.bo.mapper.UserMapper;
import com.bo.model.domain.Article;
import com.bo.model.domain.User;
import com.bo.service.ArticleService;
import com.bo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleMapper articleMapper;

    @PostMapping("/save")
    public BaseResponse<Article> saveArticle(@RequestBody Article article,HttpServletRequest request){
        // 保存文章
        // 1. 是否登录
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User)attribute;
        Integer userRole = currentUser.getUserRole();

        if (userRole!=1){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (currentUser.getId() ==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        // 2. 判断传入的值是否合法
        Long postId = article.getId();
        if (postId == null){
            articleMapper.insert(article);
        }else {
            articleMapper.updateById(article);
        }
        // 3. 插入数据
        return ResultUtil.success(article);
        // 获取当前登录用户
//        Long id = newUser.getId();
//        if (isAdmin(request) || user.getId().equals(article.getUserId())) {
//            int result = articleService.saveArticle(article, user, request);
//            return ResultUtil.success(result);
//        }

//        throw new BusinessException(ErrorCode.NO_AUTH);
    }

    @GetMapping("/findArticle/{id}")
    public BaseResponse<Article> findArticle(@PathVariable Integer id) {
        Article article = articleService.findArticle(id);
        return ResultUtil.success(article);
    }

    @GetMapping("/findAll")
    public BaseResponse<Object> findAll(Integer pageno,Integer size) {
        Object object = articleService.findAll(pageno, size);
        return ResultUtil.success(object);
    }
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Integer> deleteArticle(@PathVariable Integer id,HttpServletRequest request) {
        // 1. 是否登录
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User)attribute;
        Integer userRole = currentUser.getUserRole();

        if (userRole!=1){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (currentUser.getId() ==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        int i = articleMapper.deleteById(id);
        return ResultUtil.success(i);
    }
    /**
     * 点赞文章
     */
    @PostMapping("/like/{postId}")
    public BaseResponse<Long> likePost(@PathVariable Long postId) {
        Article likedPost = articleService.likePost(postId);
        Long likeCount = likedPost.getLikeCount();
        return ResultUtil.success(likeCount);
    }

}
