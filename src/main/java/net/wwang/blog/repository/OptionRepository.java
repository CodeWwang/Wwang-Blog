package net.wwang.blog.repository;

import net.wwang.blog.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,String> {
}
