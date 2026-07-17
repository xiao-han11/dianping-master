package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.BlogComments;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 博客评论服务接口
 */
public interface IBlogCommentsService extends IService<BlogComments> {

    /**
     * 新增评论
     */
    Result saveComment(BlogComments comment);

    /**
     * 根据博客id查询评论列表
     */
    Result queryCommentsByBlogId(Long blogId);

    /**
     * 删除评论
     */
    Result deleteComment(Long id);

    /**
     * 点赞评论
     */
    Result likeComment(Long id);
}
