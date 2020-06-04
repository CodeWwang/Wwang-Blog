package net.wwang.blog.service.impl;

import net.wwang.blog.constant.Types;
import net.wwang.blog.dto.StatisticsDTO;
import net.wwang.blog.model.Comment;
import net.wwang.blog.model.Content;
import net.wwang.blog.repository.CommentRepository;
import net.wwang.blog.repository.ContentRepository;
import net.wwang.blog.repository.MetaRepository;
import net.wwang.blog.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private MetaRepository metaRepository;


    @Override
    public StatisticsDTO getStatistics() {
        // 文章总数
        Long articles = contentRepository.findContentsCount();
        // 评论总数
        Long comments = commentRepository.findCommentsCount();
        // 链接数
        Long links = metaRepository.findMetasCountByType(Types.LINK.getType());
        StatisticsDTO dto = new StatisticsDTO();
        dto.setArticles(articles);
        dto.setComments(comments);
        dto.setLinks(links);
        return dto;
    }

    @Override
    public List<Content> getNewArticles(Integer limit) {
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        return contentRepository.findAllByLimit(limit);
    }

    @Override
    public List<Comment> getNewComment(Integer limit) {
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        return commentRepository.findAllByLimit(limit);
    }
}
