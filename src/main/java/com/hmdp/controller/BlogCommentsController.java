package com.hmdp.controller;

import com.hmdp.dto.Result;
import com.hmdp.entity.BlogComments;
import com.hmdp.service.IBlogCommentsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 博客评论控制器
 */
@RestController
@RequestMapping("/blog-comments")
public class BlogCommentsController {

    @Resource
    private IBlogCommentsService blogCommentsService;

    /**
     * 新增评论
     */
    @PostMapping
    public Result saveComment(@RequestBody BlogComments comment) {
        return blogCommentsService.saveComment(comment);
    }

    /**
     * 根据博客id查询评论列表
     */
    @GetMapping("/blog/{blogId}")
    public Result queryCommentsByBlogId(@PathVariable("blogId") Long blogId) {
        return blogCommentsService.queryCommentsByBlogId(blogId);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable("id") Long id) {
        return blogCommentsService.deleteComment(id);
    }

    /**
     * 点赞评论
     */
    @PutMapping("/like/{id}")
    public Result likeComment(@PathVariable("id") Long id) {
        return blogCommentsService.likeComment(id);
    }
}
