package net.wwang.blog.service.impl;

import net.wwang.blog.constant.Types;
import net.wwang.blog.constant.WebConst;
import net.wwang.blog.enums.ErrorEnum;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.Content;
import net.wwang.blog.model.Meta;
import net.wwang.blog.model.Relation;
import net.wwang.blog.repository.ContentRepository;
import net.wwang.blog.repository.RelationRepository;
import net.wwang.blog.service.ContentService;
import net.wwang.blog.service.MetaService;
import net.wwang.blog.utils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentRepository contentRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    MetaService metaService;


    @Override
    public Page<Content> getAllArticles(Pageable pageable) {
        return contentRepository.findAll(pageable);
    }

    @Override
    public List<Content> getArticleByCategory(String category) {
        if(category==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        return contentRepository.findArticleByCategory(category);
    }

    @Override
    public List<Content> getArticleByTag(Meta tag) {
        if(tag==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        List<Relation> relationList = relationRepository.findRelationByMid(tag.getMid());
        if(null!=relationList&&relationList.size()>0){
            return contentRepository.findArticleByTags(Converter.RelationListToCidList(relationList));
        }
        return null;
    }

    @Override
    public Content getArticleById(Integer cid) {
        if(cid==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        Optional<Content> content = contentRepository.findById(cid);
        return content.get();
    }

    @Override
    @Transactional
    public void updateContent(Content content) {
        contentRepository.save(content);
    }

    @Override
    @Transactional
    public void saveContent(Content content) {
        if(content==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(content.getTitle()))
            throw new InternalException(ErrorEnum.TITLE_CAN_NOT_EMPTY);
        if (content.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw new InternalException(ErrorEnum.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(content.getContent()))
            throw new InternalException(ErrorEnum.CONTENT_CAN_NOT_EMPTY);
        if (content.getContent().length() > WebConst.MAX_CONTENT_COUNT)
            throw new InternalException(ErrorEnum.CONTENT_IS_TOO_LONG);

        //获取标签
        String tags = content.getTags();
        //获取分类
        String categories = content.getCategories();
        //保存文章
        contentRepository.save(content);
        // 添加分类和标签
        int cid;
        if(content.getCid()!=null){
            cid = content.getCid();
        }else{
            cid = contentRepository.findLastId();
        }
        if(tags!=null){
            metaService.saveMeta(cid, tags, Types.TAG.getType());
        }
        if(categories!=null){
            metaService.saveMeta(cid, categories, Types.CATEGORY.getType());
        }
    }

    @Override
    public Long getContentsCount() {
        return contentRepository.findContentsCount();
    }

    @Override
    @Transactional
    public void deleteArticleById(Integer cid) {
        contentRepository.deleteById(cid);
    }
}
