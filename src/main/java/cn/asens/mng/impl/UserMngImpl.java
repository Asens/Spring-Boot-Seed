package cn.asens.mng.impl;

import cn.asens.dao.UserDao;
import cn.asens.entity.Permission;
import cn.asens.entity.Role;
import cn.asens.entity.User;
import cn.asens.mng.UserMng;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lenovo on 2015/12/24.
 */

@Transactional
@Service
public class UserMngImpl implements UserMng {

    @Resource
    private UserDao userDao;

    @Override
    public boolean findByName(String name) {
        User user=userDao.findByUsername(name);
        return user != null;
    }

    @Override
    public boolean validatePassword(String username,String password) {
        User user=userDao.findByUsername(username);
        return user.getPassword().equals(new Md5Hash(password, "as").toString());
    }

    @Override
    public Set<String> findRoles(String username) {
        Set<String> roleSet=new HashSet<String>();
        User user= userDao.findByUsername(username);
        if(user==null) return null;
        for(Role role:user.getRoleSet())
        {
            roleSet.add(role.getRoleName());
        }
        return roleSet;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissionSet=new HashSet<String>();
        User user= userDao.findByUsername(username);
        if(user==null) return null;
        for(Role role:user.getRoleSet())
        {
            for(Permission permission:role.getPermissionSet())
            {
                permissionSet.add(permission.getPermissionName());
            }
        }
        return permissionSet;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User getUser() {
        Subject subject=SecurityUtils.getSubject();
        if(subject.getPrincipal()==null) return null;
        String username= SecurityUtils.getSubject().getPrincipal().toString();
        User user=findByUsername(username);
        return user;
    }



    @Override
    public List<User> getList() {
        return userDao.getList();
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
