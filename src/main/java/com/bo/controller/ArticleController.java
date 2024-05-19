package com.bo.controller;

import com.bo.common.BaseResponse;
import com.bo.common.ErrorCode;
import com.bo.common.ResultUtil;
import com.bo.exception.BusinessException;
import com.bo.model.domain.Article;
import com.bo.model.domain.User;
import com.bo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ArticleController extends UserController{
    @Resource
    private ArticleService articleService;

    @PostMapping("/save")
    public BaseResponse<Integer> saveArticle(@RequestBody Article article, User user,HttpServletRequest request){
        if (isAdmin(request) || user.getId().equals(article.getUserId())) {
            int result = articleService.saveArticle(article, user, request);
            return ResultUtil.success(result);
        }

        throw new BusinessException(ErrorCode.NO_AUTH);
    }

    @GetMapping("/findArticle/{id}")
    public BaseResponse<Article> findArticle(@PathVariable("id") Integer id) {
        Article article = articleService.findArticle(id);
        return ResultUtil.success(article);
    }

    @GetMapping("/findAll")
    public BaseResponse<Object> findAll(Integer pageno,Integer size) {
        Object object = articleService.findAll(pageno, size);
        return ResultUtil.success(object);
    }
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Integer> deleteArticle(@PathVariable("id") Article article, User user,HttpServletRequest request) {
        if (isAdmin(request) || user.getId().equals(article.getUserId())) {
            Long longid = article.getId();
            int id = (int) (long) longid;
            int result = articleService.deleteArticle(article,user,request);
            return ResultUtil.success(result);
        }
        throw new BusinessException(ErrorCode.NO_AUTH);
    }
}
