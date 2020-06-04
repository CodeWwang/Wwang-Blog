package net.wwang.blog.service;

import net.wwang.blog.model.Option;

import java.util.List;
import java.util.Map;

public interface OptionService {
    /**
     * 获取所有网站配置
     * @return
     */
    List<Option> getOptions();

    /**
     * 更新网站配置
     * @param options
     */
    void saveOptions(Map<String,String> options);

    /**
     * 根据key查找
     * @param name
     * @return
     */
    Option getByName(String name);
}
