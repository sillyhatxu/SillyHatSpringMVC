package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.SillyHatAJAX;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.sillyhat.springmvc.stripe.utils.HttpRequestUtils;
import com.sillyhat.springmvc.stripe.utils.JsonUtils;
import com.stripe.model.Customer;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${onemap.url}")
    private String URL;
    @Value("${onemap.params.begin}")
    private String begin;
    @Value("${onemap.params.end}")
    private String end;

    @ResponseBody
    @ApiOperation(value = "根据mozatId查询用户卡片列表", response = SillyHatAJAX.class, notes = "根据mozatId查询用户卡片列表,如未绑定卡片skipBoundCard=true,否则skipBoundCard=false,并返回cardList")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "mozatId", value = "客户身份唯一性标识号", required = true, dataType = "string", paramType = "header")
//    })
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
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "mozatId", value = "客户身份唯一性标识号", required = true, dataType = "string", paramType = "header"),
//        @ApiImplicitParam(name = "source", value = "tokenId", required = true, dataType = "string", paramType = "header")
//    })
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

    @ResponseBody
    @ApiOperation(value = "查询客户", response = SillyHatAJAX.class, notes = "查询客户")
    @RequestMapping(value = "/queryCustomerByParams", method = RequestMethod.GET)
    public SillyHatAJAX queryCustomerByParams(@RequestParam("limit") Long limit,@RequestParam("startingAfter") String startingAfter,@RequestParam("endingBefore") String endingBefore) {
        Map<String,Object> result = stripeService.queryCustomerByParams(limit,startingAfter,endingBefore);
        logger.info("Stripe result : {}",result);
        return new SillyHatAJAX(result);
    }

    /**
     * https://docs.onemap.sg/
     * @param postalCode
     * @param returnGeom
     * @param getAddrDetails
     * @param pageNum
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据邮编得到地址", response = SillyHatAJAX.class, notes = "根据邮编得到地址")
    @RequestMapping(value = "/getAddressByPostalCode/{postalCode}/returnGeom/{returnGeom}/getAddrDetails/{getAddrDetails}/pageNum/{pageNum}", method = RequestMethod.GET)
//    public SillyHatAJAX queryCustomerByParams(@PathVariable String postalCode) {
    public SillyHatAJAX queryCustomerByParams(@PathVariable String postalCode,@PathVariable String returnGeom,@PathVariable String getAddrDetails,@PathVariable String pageNum) {
        String params = "searchVal=" + postalCode + "&returnGeom="+returnGeom+"&getAddrDetails="+getAddrDetails+"&pageNum=" + pageNum;
        String jsonSrc = HttpRequestUtils.sendGet(URL,params);
        Map<String,Object> result = JsonUtils.jsonToObject(jsonSrc,Map.class);
        return new SillyHatAJAX(result);
    }

}
