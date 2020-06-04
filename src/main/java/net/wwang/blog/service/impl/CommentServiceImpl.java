package net.wwang.blog.service.impl;

import io.swagger.annotations.ApiResponse;
import net.wwang.blog.enums.CommentEnum;
import net.wwang.blog.enums.ErrorEnum;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.Comment;
import net.wwang.blog.model.Content;
import net.wwang.blog.repository.CommentRepository;
import net.wwang.blog.repository.ContentRepository;
import net.wwang.blog.service.CommentService;
import net.wwang.blog.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private CommentService commentService;

    @Override
    public List<Comment> getCommentsByCid(Integer cid) {
        if(cid==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        return commentRepository.findAllByCid(cid);
    }

    @Override
    public void insertComment(Comment comment) {
        if(comment==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        // TODO 可设置作者，需要后端管理联动
        // 插入评论
        comment.setStatus(CommentEnum.UNCHECKED.getType());
        comment.setCreated(DateUtil.getCurrentUnixTime());
        commentRepository.save(comment);
        // 文章评论总数+1
        try{
            Content article = contentRepository.findById(comment.getCid()).get();
            article.setCommentsNum(article.getCommentsNum()+1);
            contentRepository.save(article);
        }catch (InternalException e){
            throw new InternalException(ErrorEnum.ARTICLE_IS_NULL);
        }
    }

    @Override
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void updateComment(Integer coid, String status) {
        if (null == coid)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        commentRepository.updateCommentStatus(coid, status);
    }

    @Override
    @Transactional
    public void deleteComment(Integer coid) {
        Comment comment = commentService.getCommentByCoid(coid);
        // 文章评论总数-1
        Content article = contentRepository.findById(comment.getCid()).get();
        article.setCommentsNum(article.getCommentsNum()-1);
        contentRepository.save(article);
        //先进行文章评论数-1，再进行删除操作
        commentRepository.deleteCommentByCoid(coid);
    }
    @Override
    public Comment getCommentByCoid(Integer coid) {
        return commentRepository.findCommentByCoid(coid);
    }

}
