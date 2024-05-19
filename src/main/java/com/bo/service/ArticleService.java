package com.bo.service;

import com.bo.model.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bo.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author Bo
* @description 针对表【article(文章表)】的数据库操作Service
* @createDate 2024-05-18 13:14:58
*/
public interface ArticleService extends IService<Article> {
 Integer saveArticle(Article article, User user, HttpServletRequest request);
 Integer deleteArticle(Article article, User user,HttpServletRequest request);
 Article findArticle(Integer id);
 Object findAll(Integer pageno, Integer size);

}
