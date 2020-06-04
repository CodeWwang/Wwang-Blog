package net.wwang.blog.service.impl;

import net.wwang.blog.enums.ErrorEnum;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.Log;
import net.wwang.blog.repository.LogRepository;
import net.wwang.blog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;

    @Override
    @Transactional
    public void insertLog(Log log) {
        if(log==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        logRepository.save(log);
    }

    @Override
    public List<Log> getNewLog(Integer limit) {
        if(limit==null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        return logRepository.findAllByLimit(limit);
    }
}
