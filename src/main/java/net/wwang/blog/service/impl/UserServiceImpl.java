package net.wwang.blog.service.impl;

import net.wwang.blog.enums.ErrorEnum;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.User;
import net.wwang.blog.repository.UserRepository;
import net.wwang.blog.service.UserService;
import net.wwang.blog.utils.SpecialUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw new InternalException(ErrorEnum.USERNAME_PASSWORD_IS_EMPTY);
        password = SpecialUtil.MD5encode(password);
        User user = userRepository.findByUsername(username);
        //打印空指针异常信息，打印异常信息会出现错误用户名和密码页面无响应问题。
        //System.out.println("login: " + user.toString());
        if (null == user)
            throw new InternalException(ErrorEnum.USERNAME_PASSWORD_ERROR);
        if (password != null && password.equals(user.getPassword())) {
            return user;
        } else {
            throw new InternalException(ErrorEnum.USERNAME_PASSWORD_ERROR);
        }
    }

    @Override
    public User getUserById(Integer uid) {
        Optional<User> user = userRepository.findById(uid);
        return user.get();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user == null)
            throw new InternalException(ErrorEnum.PARAM_IS_EMPTY);
        userRepository.save(user);
    }
}
