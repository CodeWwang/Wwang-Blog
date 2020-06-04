package net.wwang.blog.service.impl;

import net.wwang.blog.model.Option;
import net.wwang.blog.repository.OptionRepository;
import net.wwang.blog.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public List<Option> getOptions() {
        return optionRepository.findAll();
    }

    @Override
    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            Option option = new Option();
            options.forEach((k,v)->{
                option.setName(k);
                option.setValue(v);
                optionRepository.save(option);
            });
        }
    }

    @Override
    public Option getByName(String name) {
        return optionRepository.findById(name).get();
    }
}
