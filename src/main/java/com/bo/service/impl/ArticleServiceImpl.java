package com.bo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.exception.BusinessException;
import com.bo.model.domain.Article;
import com.bo.model.domain.User;
import com.bo.service.ArticleService;
import com.bo.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* @author Bo
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2024-05-18 13:14:58
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

//    @Override
//    public Integer saveArticle(Article article, HttpServletRequest request) {
//        int result = article.getId() != null ? articleMapper.updateById(article) : articleMapper.insert(article);
//        return result;
//    }

//    @Override
//    public Integer deleteArticle(Article article, User user,HttpServletRequest request) {
//      return articleMapper.deleteArticle(article, user, request);
//    }

    @Override
    public Article findArticle(Integer id) {
         return articleMapper.findById(id);
    }

    @Override
    public Object findAll(Integer pageno, Integer size) {
        if (pageno != null && size != null) {
            IPage<Article> page = new Page<>(pageno, size);
            return articleMapper.selectPage(page, null);
        } else {
            return articleMapper.selectList(null);
        }
    }
    @Transactional
    public Article likePost(Long postId) {
        articleMapper.incrementLikeCount(postId);
        return articleMapper.selectPost(postId);
    }

}




