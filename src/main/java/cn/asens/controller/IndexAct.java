package cn.asens.controller;

import cn.asens.entity.User;
import cn.asens.mng.UserMng;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Asens on 2017/7/13
 */

@Controller
public class IndexAct {
    private static Logger log= LogManager.getLogger(IndexAct.class);
    @Resource
    private UserMng userMng;

    @RequestMapping("/")
    public String index(ModelMap model) {
        model.put("freeMarker","freeMarker");
        User user=userMng.getUser();
        if(user!=null){
            model.put("user",user);
        }
        return "welcome";
    }



}
