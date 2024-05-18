create table user
(
    id           bigint auto_increment
        primary key,
    userName     varchar(256)                       null comment '用户昵称',
    userAccount  varchar(256)                       not null comment '账号',
    userPassword varchar(256)                       not null comment '密码',
    userRole     tinyint      default 0                 not null comment '0-普通用户 1-管理员'
)
    comment '用户表' charset = utf8mb4;

create table article
(
    id           bigint auto_increment
        primary key,
    articleTitle    varchar(128)                       not null comment '文章标题',
    articleContent  text                      null comment '文章内容',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userId   bigint,
    FOREIGN KEY (userId) REFERENCES user(id)
)
    comment '文章表' charset = utf8mb4;

create table thumbs
(
    id           bigint auto_increment
        primary key,

    userId   bigint,
    articleId   bigint,
    FOREIGN KEY (userId) REFERENCES user(id),
    FOREIGN KEY (articleId) REFERENCES article(id)

)
    comment '点赞表' charset = utf8mb4;

