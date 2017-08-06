package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.SillyHatAJAX;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.stripe.model.Customer;
import com.wordnik.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private StripeService stripeService;

    @ResponseBody
    @ApiOperation(value = "新增客户", response = SillyHatAJAX.class, notes = "新增客户")
    @RequestMapping(value = "/addCustomer", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX addCustomer(@RequestBody Customer customer) {

        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "修改客户", response = SillyHatAJAX.class, notes = "修改客户")
    @RequestMapping(value = "/updateCustomer", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX updateCustomer(@RequestBody Customer customer) {

        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "删除客户", response = SillyHatAJAX.class, notes = "删除客户")
    @RequestMapping(value = "/deleteCustomer", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX deleteCustomer(@RequestBody Customer customer) {

        return new SillyHatAJAX(true);
    }


    @ResponseBody
    @ApiOperation(value = "批量查询客户", response = SillyHatAJAX.class, notes = "批量查询客户")
    @RequestMapping(value = "/queryCustomerByLimit", method = {RequestMethod.GET},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX queryCustomerByLimit(@RequestBody Customer customer) {

        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "查询客户", response = SillyHatAJAX.class, notes = "查询客户")
    @RequestMapping(value = "/getCustomerById/{id}", method = RequestMethod.GET)
    public SillyHatAJAX getCustomerById(@PathVariable String id) {
        logger.info("id is {}",id);
        Customer customer = stripeService.getCustomerById(id);
        logger.info("--------------------------------------");
        logger.info("CustomerId : {} ",customer.getId());
        logger.info("BusinessVatId : {} ",customer.getBusinessVatId());
        logger.info("Description : {} ",customer.getDescription());
        return new SillyHatAJAX(customer);
    }


//    @ResponseBody
//    @ApiOperation(value = "新增客户", response = SillyHatAJAX.class, notes = "新增客户")
//    @RequestMapping(value = "/addCustomer", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
//    public SillyHatAJAX addCustomer(@RequestBody String user) {
//
//        return new SillyHatAJAX(true);
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "修改客户", response = SillyHatAJAX.class, notes = "修改客户")
//    @RequestMapping(value = "/updateCustomer", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
//    public SillyHatAJAX updateCustomer(@RequestBody String user) {
//
//        return new SillyHatAJAX(true);
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "删除客户", response = SillyHatAJAX.class, notes = "删除客户")
//    @RequestMapping(value = "/deleteCustomer", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
//    public SillyHatAJAX deleteCustomer(@RequestBody String user) {
//
//        return new SillyHatAJAX(true);
//    }
//
//
//    @ResponseBody
//    @ApiOperation(value = "批量查询客户", response = SillyHatAJAX.class, notes = "批量查询客户")
//    @RequestMapping(value = "/queryCustomerByLimit", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
//    public SillyHatAJAX queryCustomerByLimit(@RequestBody String user) {
//
//        return new SillyHatAJAX(true);
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "查询客户", response = SillyHatAJAX.class, notes = "查询客户")
//    @RequestMapping(value = "/getCustomerById", method = {RequestMethod.GET, RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
//    public SillyHatAJAX getCustomerById(@RequestBody String user) {
//
//        return new SillyHatAJAX(true);
//    }


}
