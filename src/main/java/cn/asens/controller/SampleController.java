package cn.asens.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Asens on 2017/7/13
 */

@Controller
public class SampleController {
    private static Logger log= LogManager.getLogger(SampleController.class);

    private String name;


    @RequestMapping("/")
    String home(ModelMap model) {
        model.put("freeMarker","freeMarker");
        return "welcome";
    }



}
