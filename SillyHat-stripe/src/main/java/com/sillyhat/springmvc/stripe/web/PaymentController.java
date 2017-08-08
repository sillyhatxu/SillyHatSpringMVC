package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.SillyHatAJAX;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * LearningWordController
 *
 * @author 徐士宽
 * @date 2017/8/3 19:59
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private StripeService stripeService;

    @Autowired
    private UserService userService;

    @Value("${stripe.api.key}")
    private String STRIPE_API_KEY;

//    @ResponseBody
//    @ApiOperation(value = "获取Secret key", response = SillyHatAJAX.class, notes = "获取Secret key")
//    @RequestMapping(value = "/ephemeral_keys/{customer}", method = {RequestMethod.POST})
//    public SillyHatAJAX getStripeSecretKey(@PathVariable String customer) {
//        EphemeralKey ephemeralKey = stripeService.getEphemeralKey(customer);
//        return new SillyHatAJAX(ephemeralKey);
//    }

    @ResponseBody
    @ApiOperation(value = "获取Secret key", response = SillyHatAJAX.class, notes = "获取Secret key")
    @RequestMapping(value = "/ephemeral_keys", method = {RequestMethod.POST})
    public SillyHatAJAX getStripeSecretKey(@RequestParam("customer") String customer) {
        EphemeralKey ephemeralKey = stripeService.getEphemeralKey(customer);
        return new SillyHatAJAX(ephemeralKey);
    }

    @ResponseBody
    @ApiOperation(value = "创建一笔支付订单", response = SillyHatAJAX.class, notes = "创建一笔支付订单")
    @RequestMapping(value = "/createdPayment", method = {RequestMethod.POST})
    public SillyHatAJAX createdPayment(@RequestParam("source") String source,@RequestParam("amount") String amount,@RequestParam("mozatId") String mozatId) {
//    public SillyHatAJAX createdPayment(@PathVariable String params) {
        logger.info("source : {},amount : {} , mozatId: {}",source,amount,mozatId);
        try{
            UserDTO userDTO = userService.getUserByMozatId(mozatId);
            if(userDTO == null){
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setDescription(mozatId);
                Customer customer = stripeService.createdCustomer(customerDTO);
                userDTO = new UserDTO();
                userDTO.setMozatId(mozatId);
                userDTO.setCustomerId(customer.getId());
                userService.addUser(userDTO);
                UserCardDTO userCardDTO = new UserCardDTO();
                userCardDTO.setTokenId(source);
            }
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setAmount(Long.parseLong(amount));
            paymentDTO.setSource(source);
            stripeService.createdPayment(paymentDTO);
            return new SillyHatAJAX("支付成功");
        } catch (Exception e){
            return new SillyHatAJAX("支付发生异常");
        }
    }

    @ResponseBody
    @ApiOperation(value = "创建一笔预授权支付订单", response = SillyHatAJAX.class, notes = "创建一笔预授权支付订单")
    @RequestMapping(value = "/createdCapturePayment", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX createdCapturePayment(@RequestBody Charge charge) {
        stripeService.createdCapturePayment(null);
        return new SillyHatAJAX(true);
    }


    @ResponseBody
    @ApiOperation(value = "后台管理-系统管理-保存用户", response = String.class, notes = "后台管理-系统管理-保存用户")
    @RequestMapping(value = "/saveUser", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX saveUser(@RequestBody String user) {

        return new SillyHatAJAX(true);
    }
}
