package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.SillyHatAJAX;
import com.sillyhat.springmvc.stripe.dto.*;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
    @ApiOperation(value = "创建Token", response = SillyHatAJAX.class, notes = "创建Token")
    @RequestMapping(value = "/createdToken", method = {RequestMethod.GET,RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX createdToken(@RequestBody CardsDTO dto) {
//    public SillyHatAJAX createdToken(CardsDTO dto) {
        return new SillyHatAJAX(stripeService.createdCardToken(dto));
    }


//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query",name="province",dataType="String",required=true,value="省",defaultValue="广东省"),// 每个参数的类型，名称，数据类型，是否校验，描述，默认值(这些在界面上有展示)
//            @ApiImplicitParam(paramType="query",name="area",dataType="String",required=true,value="地区",defaultValue="南山区"),
//            @ApiImplicitParam(paramType="query",name="street",dataType="String",required=true,value="街道",defaultValue="桃园路"),
//            @ApiImplicitParam(paramType="query",name="num",dataType="String",required=true,value="门牌号",defaultValue="666")
//    })
    @ResponseBody
    @ApiOperation(value = "创建一笔支付订单", response = SillyHatAJAX.class, notes = "创建一笔支付订单")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "mozatId", value = "客户身份唯一性标识号", required = true, dataType = "string", paramType = "query"),
//        @ApiImplicitParam(name = "cardId", value = "customer绑定银行卡ID", required = true, dataType = "string", paramType = "query"),
//        @ApiImplicitParam(name = "amount", value = "消费金额", required = true, dataType = "Long", paramType = "query")
//    })
    @RequestMapping(value = "/createdPayment", method = {RequestMethod.POST})
    public SillyHatAJAX createdPayment(@RequestParam("mozatId") String mozatId,@RequestParam("cardId") String cardId,@RequestParam("amount") Long amount) {
//    public SillyHatAJAX createdPayment(@RequestBody PaymentDetailDTO dto) {
        logger.info("mozatId : {}; cardId : {}; amount : {}",mozatId,cardId,amount);
        UserDTO user = userService.getUserByMozatId(mozatId);
        Customer customer = stripeService.getCustomerById(user.getCustomerId());
        Card card = stripeService.getCustomerCard(customer,cardId);
        Map<String,Object> result = stripeService.createdPayment(customer.getId(),cardId,amount);
        return new SillyHatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "创建一笔支付订单", response = SillyHatAJAX.class, notes = "创建一笔支付订单")
    @RequestMapping(value = "/createdPaymentDetail", method = {RequestMethod.POST})
    public SillyHatAJAX createdPayment(@RequestBody PaymentDTO dto) {
        logger.info("PaymentDTO : {}; ",dto.toString());
        Map<String,Object> result = stripeService.createdPayment(dto);
        return new SillyHatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "修改一笔支付订单", response = SillyHatAJAX.class, notes = "This request accepts only the description, metadata, receipt_email, fraud_details, and shipping as arguments.")
    @RequestMapping(value = "/updatedPayment", method = {RequestMethod.POST})
    public SillyHatAJAX updatedPayment(@RequestParam("chargeId") String chargeId, @RequestParam("receiptEmail")String receiptEmail) {
        Map<String,Object> result = stripeService.updatePayment(chargeId,receiptEmail);
        logger.info("Stripe result : {}",result);
        return new SillyHatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "查询交易信息", response = SillyHatAJAX.class, notes = "Limit默认10，只能录入1-100")
    @RequestMapping(value = "/queryPaymentByParams", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX queryPaymentByParams(@RequestParam("limit") Long limit,@RequestParam("startingAfter") String startingAfter,@RequestParam("endingBefore") String endingBefore) {
        Map<String,Object> result = stripeService.queryPaymentByParams(limit,startingAfter,endingBefore);
        logger.info("Stripe result : {}",result);
        return new SillyHatAJAX(result);
    }
//        logger.info("cardNo : {},source : {},amount : {} , mozatId: {}",cardNo,source,amount,mozatId);
//        try{
//            UserDTO userDTO = userService.getUserByMozatId(dto.getMozatId());
//            UserCardDTO sourceCard = null;
//            if(userDTO == null){
//                CustomerDTO customerDTO = new CustomerDTO();
//                customerDTO.setDescription(dto.getMozatId());
//                Customer customer = stripeService.createdCustomer(customerDTO);
//                userDTO = new UserDTO();
//                userDTO.setMozatId(dto.getMozatId());
//                userDTO.setCustomerId(customer.getId());
//                userService.addUser(userDTO);
//            }
//            dto.setCustomerId(userDTO.getCustomerId());
//            sourceCard  = userService.getUserCardByParams(userDTO.getId(),dto);
//            PaymentDTO paymentDTO = new PaymentDTO();
//            paymentDTO.setCustomer(userDTO.getCustomerId());
//            paymentDTO.setAmount(dto.getAmount());
////            paymentDTO.setSource(sourceCard.getTokenId());
//            stripeService.createdPayment(paymentDTO);
//            return new SillyHatAJAX("支付成功");
//        } catch (Exception e){
//            return new SillyHatAJAX("支付发生异常");
//        }
//        try{
//            UserDTO userDTO = userService.getUserByMozatId(mozatId);
//            if(userDTO == null){
//                CustomerDTO customerDTO = new CustomerDTO();
//                customerDTO.setDescription(mozatId);
//                Customer customer = stripeService.createdCustomer(customerDTO);
//                customerId = customer.getId();
//                userDTO = new UserDTO();
//                userDTO.setMozatId(mozatId);
//                userDTO.setCustomerId(customerId);
//                userService.addUser(userDTO);
//            }
//            PaymentDTO paymentDTO = new PaymentDTO();
//            paymentDTO.setCustomer(customerId);
//            paymentDTO.setAmount(Long.parseLong(amount));
//            if(StringUtils.isNotEmpty(source)){
//                boolean boundCard = true;//判断是否需要创建新绑定的卡
//                List<UserCardDTO> userCardDTOList = userService.queryUserCardList(userDTO.getId());
//                for(UserCardDTO userCardDTO : userCardDTOList){
//                    if(userCardDTO.getTokenId().equals(source)){
//                        boundCard = false;
//                    }
//                }
//                paymentDTO.setSource(source);
//                if(boundCard){
//                    stripeService.boundCustomerCard(customerId,source);//绑定银行卡
//                }
//            }
//            stripeService.createdPayment(paymentDTO);
//            return new SillyHatAJAX("支付成功");
//        } catch (Exception e){
//            return new SillyHatAJAX("支付发生异常");
//        }
//    }

    @ResponseBody
    @ApiOperation(value = "创建一笔预授权支付订单", response = SillyHatAJAX.class, notes = "创建一笔预授权支付订单")
    @RequestMapping(value = "/createdCapturePayment", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX createdCapturePayment(@RequestParam("mozatId") String mozatId,@RequestParam("cardId") String cardId,@RequestParam("amount") Long amount) {
        UserDTO user = userService.getUserByMozatId(mozatId);
        stripeService.createdCapturePayment(user.getCustomerId(),cardId,amount);
        return new SillyHatAJAX(true);
    }

    @ResponseBody
    @ApiOperation(value = "预授权订单确认收款", response = SillyHatAJAX.class, notes = "预授权订单确认收款")
    @RequestMapping(value = "/confirmCapturePayment", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX confirmCapturePayment(@RequestParam("chargeId") String chargeId,@RequestParam("amount") Long amount) {
        stripeService.confirmCapturePayment(chargeId,amount);
        return new SillyHatAJAX("确认支付成功");
    }

    @ResponseBody
    @ApiOperation(value = "退款申请", response = SillyHatAJAX.class, notes = "退款申请，金额不填写默认全额退款")
    @RequestMapping(value = "/createdRefund", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX createdRefund(@RequestParam("chargeId") String chargeId,@RequestParam("amount") Long amount) {
        Map<String,Object> result = stripeService.createdRefund(chargeId,amount);
        logger.info("Stripe result : {}",result);
        return new SillyHatAJAX("退款成功");
    }

    //Transaction type:
    // adjustment, application_fee,
    // application_fee_refund,
    // charge,
    // payment,
    // payment_failure_refund,
    // payment_refund,
    // refund,
    // transfer,
    // transfer_refund,
    // payout,
    // payout_cancel,
    // payout_failure, or validation.
    @ResponseBody
    @ApiOperation(value = "查询交易信息", response = SillyHatAJAX.class, notes = "Limit默认10，只能录入1-100")
    @RequestMapping(value = "/queryBalanceTransaction", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE)
    public SillyHatAJAX queryBalanceTransaction(@RequestParam("limit") Long limit,@RequestParam("startingAfter") String startingAfter,@RequestParam("endingBefore") String endingBefore) {
        Map<String,Object> result = stripeService.queryBalanceTransaction(limit,startingAfter,endingBefore);
        logger.info("Stripe result : {}",result);
        return new SillyHatAJAX(result);
    }


}
