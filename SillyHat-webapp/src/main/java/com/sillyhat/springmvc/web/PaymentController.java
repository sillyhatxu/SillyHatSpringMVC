package com.sillyhat.springmvc.web;


import com.sillyhat.springmvc.dto.SillyHatAJAX;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * LearningWordController
 *
 * @author 徐士宽
 * @date 2017/8/3 19:59
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @ApiOperation(value = "进入单词主界面", response = String.class, notes = "进入主界面")
    @RequestMapping(value = "/learing-word/toLearningWord", method = {RequestMethod.GET})
    public String toLearningWord() {

        return "/payment/wordLearnging";
    }

    @ResponseBody
    @ApiOperation(value = "后台管理-系统管理-保存用户", response = String.class, notes = "后台管理-系统管理-保存用户")
    @RequestMapping(value = "/saveUser", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX saveUser(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }
}
