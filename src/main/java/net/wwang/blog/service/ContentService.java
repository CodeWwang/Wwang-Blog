package net.wwang.blog.service;

import net.wwang.blog.model.Content;
import net.wwang.blog.model.Meta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContentService {
    Page<Content> getAllArticles(Pageable pageable);

    /**
     * 根据分类名获得所有文章
     * 文章与分类一对一
     *
     * @param category
     * @return
     */
    List<Content> getArticleByCategory(String category);

    /**
     * 根据标签得到所有文章
     * 文章与标签一对多
     *
     * @param tag
     * @return
     */
    List<Content> getArticleByTag(Meta tag);

    /**
     * 根据文章id获取文章
     *
     * @param cid
     * @return
     */
    Content getArticleById(Integer cid);

    /**
     * 更新文章点击率专用
     *
     * @param content
     */
    void updateContent(Content content);

    /**
     * 后台新增及更新文章，涉及到关联表
     *
     * @param content
     */
    void saveContent(Content content);

    /**
     * 获取文章总数
     *
     * @return
     */
    Long getContentsCount();

    /**
     * 根据id删除文章
     *
     * @param cid
     */
    void deleteArticleById(Integer cid);
}
