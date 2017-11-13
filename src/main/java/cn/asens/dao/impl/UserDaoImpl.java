package cn.asens.dao.impl;

import cn.asens.dao.UserDao;
import cn.asens.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Asens on 2017/7/16
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {


    @Override
    public void save(User user) {
        getSession().save(user);
    }

    @Override
    public User findByUsername(String name) {
        return (User)getSession().createQuery("from User bean where bean.username=:name")
                .setParameter("name",name)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getList() {
        return getSession().createQuery("from User").list();
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public User findByEmail(String email) {
        return (User)getSession().createQuery("from User bean where bean.email=:email")
                .setParameter("email",email)
                .setMaxResults(1)
                .uniqueResult();
    }
}
