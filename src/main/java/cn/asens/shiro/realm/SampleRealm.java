package cn.asens.shiro.realm;


import cn.asens.mng.UserMng;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2015/12/4.
 */
@Component
public class SampleRealm extends AuthorizingRealm {

    @Resource
    private UserMng userMng;
    

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username=(String)token.getPrincipal();
        String password=new String((char[]) token.getCredentials());
        if(!userMng.findByName(username))
        {
            throw new UnknownAccountException();
        }

        if(!userMng.validatePassword(username,password))
        {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userMng.findRoles(username));
        authorizationInfo.setStringPermissions(userMng.findPermissions(username));
        return authorizationInfo;
    }
}
