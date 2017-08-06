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
@RequestMapping("/customer")
public class CustomerController {

    @ResponseBody
    @ApiOperation(value = "新增客户", response = SillyHatAJAX.class, notes = "新增客户")
    @RequestMapping(value = "/addCustomer", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX addCustomer(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "修改客户", response = SillyHatAJAX.class, notes = "修改客户")
    @RequestMapping(value = "/updateCustomer", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX updateCustomer(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "删除客户", response = SillyHatAJAX.class, notes = "删除客户")
    @RequestMapping(value = "/deleteCustomer", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX deleteCustomer(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }


    @ResponseBody
    @ApiOperation(value = "批量查询客户", response = SillyHatAJAX.class, notes = "批量查询客户")
    @RequestMapping(value = "/queryCustomerByLimit", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX queryCustomerByLimit(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "查询客户", response = SillyHatAJAX.class, notes = "查询客户")
    @RequestMapping(value = "/getCustomerById", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX getCustomerById(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }


}
