package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.SillyHatAJAX;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.stripe.model.Customer;
import com.wordnik.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;


    @ResponseBody
    @ApiOperation(value = "根据mozatId查询用户卡片列表", response = SillyHatAJAX.class, notes = "根据mozatId查询用户卡片列表,如未绑定卡片skipBoundCard=true,否则skipBoundCard=false,并返回cardList")
    @RequestMapping(value = "/queryUserCardByMozatId/{mozatId}", method = {RequestMethod.GET,RequestMethod.POST})
    public SillyHatAJAX queryUserCardByMozatId(@PathVariable String mozatId) {
        Map<String,Object> result = new HashMap<String,Object>();
        UserDTO user = userService.getUserByMozatId(mozatId);
        if(user == null){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setEmail(mozatId+"@test.com");
            customerDTO.setDescription(mozatId+"-description");
            Customer customer = stripeService.createdCustomer(customerDTO);
            user = new UserDTO();
            user.setCustomerId(customer.getId());
            user.setMozatId(mozatId);
            userService.addUser(user);
        }
        List<UserCardDTO> userCardList = userService.queryUserCardList(user.getId());
        if(userCardList != null && userCardList.size() > 0){
            result.put("skipBoundCard",false);
            result.put("cardList",userCardList);
        }else{
            result.put("skipBoundCard",true);
        }
        return new SillyHatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "customer绑定银行卡", response = SillyHatAJAX.class, notes = "xxxxxx")
    @RequestMapping(value = "/boundCard/{mozatId}/source/{source}", method = {RequestMethod.GET,RequestMethod.POST})
    public SillyHatAJAX boundCard(@PathVariable String mozatId,@PathVariable String source) {
//    public SillyHatAJAX boundCard(@RequestParam("mozatId")String mozatId,@RequestParam("source")String source) {
        Map<String,Object> result = new HashMap<String,Object>();
        UserDTO user = userService.getUserByMozatId(mozatId);
        boolean bound = stripeService.boundCustomerCard(user.getId(),user.getCustomerId(),source);
        if(bound){
            List<UserCardDTO> userCardList = userService.queryUserCardList(user.getId());
            result.put("boundCardCode",true);
            result.put("message","绑定银行卡成功");
            result.put("cardList",userCardList);
        }else{
            result.put("boundCardCode",false);
            result.put("message","绑定银行卡失败");
        }
        return new SillyHatAJAX(result);
    }

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
