package com.sillyhat.springmvc.stripe.service;

import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;

/**
 * StripeService
 *
 * @author 徐士宽
 * @date 2017/8/6 21:18
 */
public interface StripeService {

    /**
     * 得到Stripe秘钥
     * @return
     */
    public RequestOptions getStripeRequestOptions();

    /**
     * 根据ID查询客户信息
     * @param id
     * @return
     */
    public Customer getCustomerById(String id);


}
