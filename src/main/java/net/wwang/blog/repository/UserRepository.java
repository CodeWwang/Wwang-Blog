package net.wwang.blog.repository;

import net.wwang.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);
}
