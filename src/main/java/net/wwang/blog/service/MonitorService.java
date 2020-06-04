package net.wwang.blog.service;

import net.wwang.blog.dto.StatisticsDTO;
import net.wwang.blog.model.Comment;
import net.wwang.blog.model.Content;

import java.util.List;

public interface MonitorService {
    /**
     * 获取文章、评论、链接统计数据
     * @return
     */
    StatisticsDTO getStatistics();

    List<Content> getNewArticles(Integer limit);

    List<Comment> getNewComment(Integer limit);
}
