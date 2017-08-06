package com.sillyhat.springmvc.common.web;


import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * LearningWordController
 *
 * @author 徐士宽
 * @date 2017/8/3 19:59
 */
@Controller("commonController")
public class CommonController{

    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    /**
     * 主页(进入项目)
     *
     * @return
     */
    @ApiOperation(value = "进入主界面", response = String.class, notes = "进入主界面")
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "/index";
    }

}
