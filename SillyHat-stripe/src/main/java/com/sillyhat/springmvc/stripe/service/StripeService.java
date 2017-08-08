package com.sillyhat.springmvc.stripe.service;

import com.sillyhat.springmvc.stripe.dto.CardsDTO;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.net.RequestOptions;

import java.util.Map;

/**
 * StripeService
 *
 * @author 徐士宽
 * @date 2017/8/6 21:18
 */
public interface StripeService {

    /**
     * 为客户端提供访问KEY值
     * @param customerId
     * @return
     */
    public EphemeralKey getEphemeralKey(String customerId);

    /**
     * 得到Stripe秘钥
     * @return
     */
    public RequestOptions getStripeRequestOptions();

    /**
     * 创建支付申请
     * @param paymentDTO
     * @return
     */
    public Map<String,Object> createdPayment(PaymentDTO paymentDTO);

    /**
     * 创建预授权支付申请
     * @param paymentDTO
     * @return
     */
    public Map<String,Object> createdCapturePayment(PaymentDTO paymentDTO);

    /**
     * 确认预授权支付款
     * @param paymentId
     * @param amount
     * @return
     */
    public Map<String,Object> confirmCapturePayment(String paymentId,Long amount);

    /**
     * 创建customer
     * @param dto
     * @return
     */
    public Customer createdCustomer(CustomerDTO dto);

    /**
     * 修改customer
     * @param dto
     * @return
     */
    public Customer updatedCustomer(CustomerDTO dto);

    /**
     * 创建token id
     * @param dto
     * @return
     */
    public String createdCardToken(CardsDTO dto);

    /**
     * 根据ID查询客户信息
     * @param id
     * @return
     */
    public Customer getCustomerById(String id);


}
