package com.bo.mapper;

import com.bo.model.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author Bo
* @description 针对表【article(文章表)】的数据库操作Mapper
* @createDate 2024-05-18 13:14:58
* @Entity generator.domain.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select * from article where id = #{id}")
    Article findById(Integer id);
    Integer deleteArticle(Integer id);
}




