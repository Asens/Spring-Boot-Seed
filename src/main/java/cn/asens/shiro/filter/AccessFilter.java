package cn.asens.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2015/12/26.
 */
public class AccessFilter extends AccessControlFilter {

    private String unauthorizedUrl="/noAccess";

     protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String[] permissions = (String[])mappedValue;
        if(permissions == null) {
            return true;//如果没有设置角色参数，默认成功
        }
        for(String permission : permissions) {
            if(getSubject(request, response).isPermitted(permission)) {
                return true;
            }
        }
        return false;//跳到onAccessDenied处理
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面
            saveRequestAndRedirectToLogin(request, response);

        } else {
            if (StringUtils.hasText(unauthorizedUrl)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}