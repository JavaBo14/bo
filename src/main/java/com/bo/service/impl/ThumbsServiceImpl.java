package com.bo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.model.domain.Thumbs;
import com.bo.service.ThumbsService;
import com.bo.mapper.ThumbsMapper;
import org.springframework.stereotype.Service;

/**
* @author Bo
* @description 针对表【thumbs(点赞表)】的数据库操作Service实现
* @createDate 2024-05-18 13:16:35
*/
@Service
public class ThumbsServiceImpl extends ServiceImpl<ThumbsMapper, Thumbs>
    implements ThumbsService{

}




