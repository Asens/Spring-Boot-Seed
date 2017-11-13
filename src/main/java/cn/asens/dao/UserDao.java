package cn.asens.dao;

import cn.asens.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

/**
 * Created by Asens on 2017/7/16
 */

public interface UserDao {
    void save(User user);

    User findByUsername(String name);

    List<User> getList();

    void update(User user);

    User findByEmail(String email);
}
