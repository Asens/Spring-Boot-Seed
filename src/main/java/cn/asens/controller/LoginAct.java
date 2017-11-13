package cn.asens.controller;

import cn.asens.entity.User;
import cn.asens.mng.UserMng;
import cn.asens.util.EmailUtils;
import cn.asens.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by asens on 2016/7/4.
 */
@Controller
public class LoginAct {
    private static Logger log= LogManager.getLogger(LoginAct.class);

    private final static Map<String,Integer> DATE_MAP=new ConcurrentHashMap<>();

    private final static Map<String,Long> ALL_MAP=new ConcurrentHashMap<>();
    private final static Map<String,String> EMAIL_CODE_MAP=new ConcurrentHashMap<>();
    private static Long REFRESH_DATE=System.currentTimeMillis();

    @Resource
    private UserMng userMng;

    @RequestMapping("/signIn")
    public String signIn(ModelMap model){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal()!=null){
            return "redirect:/";
        }
        Session session = subject.getSession();
        Object o=session.getAttribute("shiroSavedRequest");
        if(o!=null){
            model.put("returnUrl",((SavedRequest)o).getRequestUrl());
        }
        return "user/login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal()!=null){
            return "redirect:/";
        }
        return "user/register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void doRegister(@ModelAttribute User user, String captcha, HttpServletResponse response) throws IOException {

        String email=user.getEmail();
        if(StringUtils.isBlank(email)||userMng.findByEmail(email)!=null){
            response.getWriter().write("fail");
            return;
        }
        String username=user.getUsername();
        if(StringUtils.isBlank(username)||userMng.findByUsername(username)!=null){
            response.getWriter().write("fail");
            return;
        }

        if(!EMAIL_CODE_MAP.containsKey(email)||!Objects.equals(EMAIL_CODE_MAP.get(email),captcha)){
            response.getWriter().write("fail");
            return;
        }
        String password=user.getPassword();
        user.setPassword(new Md5Hash(password, "as").toString());
        userMng.save(user);
        response.setContentType("application/json");
        JSONObject result=new JSONObject();
        result.put("status","success");
        response.getWriter().write(result.toString());
    }


    @RequestMapping(value = "/hasUserName",method = RequestMethod.GET)
    public void hasUserName(String username,HttpServletResponse response) throws IOException {
        if(StringUtils.isBlank(username)){
            response.getWriter().write("false");
            return;
        }

        if(userMng.findByUsername(username)!=null){
            response.getWriter().write("false");
            return;
        }

        response.getWriter().write("true");
    }


    @RequestMapping(value = "/hasEmail",method = RequestMethod.GET)
    public void hasEmail(String email,HttpServletResponse response) throws IOException {
        if(StringUtils.isBlank(email)){
            response.getWriter().write("false");
            return;
        }

        if(userMng.findByEmail(email)!=null){
            response.getWriter().write("false");
            return;
        }

        response.getWriter().write("true");
    }

    @RequestMapping(value = "/getCode",method = RequestMethod.GET)
    public void getCode(String email,HttpServletRequest request,
                        HttpServletResponse response) throws IOException {


        if(StringUtils.isBlank(email)){
            response.getWriter().write("empty");
            return;
        }

        //每天最多只能发200封邮件。
        Long cur=System.currentTimeMillis();
        String date=StringUtils.getStringDate();
        if(DATE_MAP.containsKey(date)){
            if(DATE_MAP.get(date)>200){
                response.getWriter().write("limit");
                return;
            }
        }

        //同一session或ip或email59秒之内只能发一封邮件
        String ip = request.getRemoteAddr();
        log.info(ip+" "+email);
        if(ALL_MAP.containsKey(ip)&&cur-ALL_MAP.get(ip)<59*1000L){
            response.getWriter().write("later");
            return;
        }
        if(ALL_MAP.containsKey(email)&&cur-ALL_MAP.get(email)<59*1000L){
            response.getWriter().write("later");
            return;
        }

        Long ONE_DAY = 24 * 3600 * 1000L;
        if(cur-REFRESH_DATE> ONE_DAY){
            REFRESH_DATE=cur;
            ALL_MAP.clear();
        }
        String code=StringUtils.makeCode();
        if(EmailUtils.sendEmail(email,code)){
            if(DATE_MAP.containsKey(date)){
                DATE_MAP.put(date,DATE_MAP.get(date)+1);
            }else{
                DATE_MAP.put(date,1);
            }
            ALL_MAP.put(ip,cur);
            ALL_MAP.put(email,cur);
            EMAIL_CODE_MAP.put(email,code);
            response.getWriter().write("success");
            return;
        }

        response.getWriter().write("fail");
    }

    @RequestMapping(value = "/validateCode",method = RequestMethod.GET)
    public void validateCode(String email,String code,HttpServletRequest request,HttpServletResponse response) throws IOException {
        if(StringUtils.isBlank(code)){
            response.getWriter().write("false");
            return;
        }
        code=code.trim();

        if(EMAIL_CODE_MAP.containsKey(email)&&EMAIL_CODE_MAP.get(email).equals(code)){
            //EMAIL_CODE_MAP.remove(email);
            response.getWriter().write("true");
        }else{
            response.getWriter().write("false");
        }
    }


    @RequestMapping("/note")
    @RequiresPermissions("service")
    public void note(HttpServletResponse response) throws IOException {
        response.getWriter().write("note");
    }

    @RequestMapping("/noAccess")
    public void noAccess(HttpServletResponse response) throws IOException {
        response.getWriter().write("no access");
    }

    @RequestMapping("/login")
    public void login(String username, String password, String rememberMe,HttpServletResponse resp) throws IOException {
        boolean remember=false;
        if(StringUtils.isNotBlank(rememberMe)){
            remember=true;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username,password, remember);
        Subject subject =SecurityUtils.getSubject();

        try{
            subject.login(token);
        }catch (AuthenticationException e){
            resp.getWriter().write("fail");
            return;
        }

        resp.getWriter().write("success");
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess() throws Exception {
        return "redirect:/";
    }
}
