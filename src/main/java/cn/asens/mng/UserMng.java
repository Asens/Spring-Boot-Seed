package cn.asens.mng;

import cn.asens.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 2015/12/24.
 */
public interface UserMng {
    boolean findByName(String name);

    boolean validatePassword(String username, String password);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

    void update(User user);
    void save(User user);

    User findByUsername(String username);

    User getUser();


    List<User> getList();

    User findByEmail(String email);
}
