package com.bo.controller;

import com.bo.common.BaseResponse;
import com.bo.common.ResultUtil;
import com.bo.model.domain.Article;
import com.bo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping("/save")
    public BaseResponse<Integer> saveArticle(@RequestBody Article article){
        int result = articleService.saveArticle(article);
        return ResultUtil.success(result);
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
    public BaseResponse<Integer> deleteArticle(@PathVariable("id") Integer id) {
        int result = articleService.deleteArticle(id);
        return ResultUtil.success(result);
    }
}
