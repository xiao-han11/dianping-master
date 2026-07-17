package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.BlogComments;
import com.hmdp.mapper.BlogCommentsMapper;
import com.hmdp.service.IBlogCommentsService;
import com.hmdp.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 博客评论服务实现类
 */
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

    @Override
    public Result saveComment(BlogComments comment) {
        Long userId = UserHolder.getUser().getId();
        comment.setUserId(userId);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        comment.setLiked(0);
        comment.setStatus(false);
        boolean success = save(comment);
        if (!success) {
            return Result.fail("评论失败");
        }
        return Result.ok(comment.getId());
    }

    @Override
    public Result queryCommentsByBlogId(Long blogId) {
        // 查询该博客的一级评论（parent_id = 0）
        return Result.ok(query()
                .eq("blog_id", blogId)
                .eq("parent_id", 0)
                .orderByAsc("create_time")
                .list());
    }

    @Override
    public Result deleteComment(Long id) {
        Long userId = UserHolder.getUser().getId();
        BlogComments comment = getById(id);
        if (comment == null) {
            return Result.fail("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            return Result.fail("无权删除他人评论");
        }
        removeById(id);
        return Result.ok();
    }

    @Override
    public Result likeComment(Long id) {
        // 数据库点赞数 +1
        boolean success = update()
                .setSql("liked = liked + 1")
                .eq("id", id)
                .update();
        if (!success) {
            return Result.fail("点赞失败");
        }
        return Result.ok();
    }
}
