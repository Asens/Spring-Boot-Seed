package cn.asens.shiro.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2015/12/5.
 */
public class FormFilter extends FormAuthenticationFilter {

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
       // AuthenticationToken token = createToken(request, response);
        UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("username"), request.getParameter("password"), true);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = (String) token.getPrincipal();
        Subject subject =getSubject(request,response);

        try{
            subject.login(token);
            return onLoginSuccess(token,subject,request,response);
        }catch (AuthenticationException e){
//            req.setAttribute("fail1","fail");
//          saveRequestAndRedirectToLogin(req, response);
//            req.getRequestDispatcher("/signIn").forward(req,response);
//
            //return false;
            return super.onLoginFailure(token,e,req,res);
        }

    }








}
