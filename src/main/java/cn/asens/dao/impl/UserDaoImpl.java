package cn.asens.dao.impl;

import cn.asens.dao.CustomUserDao;
import cn.asens.dao.UserDao;
import cn.asens.entity.User;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Asens on 2017/7/16.
 */

@Repository
public class UserDaoImpl implements CustomUserDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public User findByaaId() {
        return (User)em.createQuery("select u from User u where u.id=1")
                .getSingleResult();
    }
}
