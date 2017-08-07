package com.sillyhat.springmvc.stripe.service.impl;

import com.sillyhat.base.utils.Constants;
import com.sillyhat.springmvc.stripe.utils.DateUtils;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.net.RequestOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * StripeServiceImpl
 *
 * @author 徐士宽
 * @date 2017/8/6 21:18
 */
@Service
public class StripeServiceImpl implements StripeService{

    private Logger logger = LoggerFactory.getLogger(StripeServiceImpl.class);

    @Value("${stripe.api.key}")
    private String STRIPE_API_KEY;

    @Override
    public EphemeralKey getEphemeralKey(String customerId) {
        EphemeralKey ephemeralKey = null;
        try {
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("customer",customerId);
            RequestOptions requestOptions = RequestOptions.builder().setApiKey(STRIPE_API_KEY).setStripeVersion("2017-06-05").build();;
//            params.put("stripe_version", DateUtils.getToday());
            ephemeralKey = EphemeralKey.create(params,requestOptions);
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            logger.info("Status is: " + e.getCode());
            logger.info("Message is: " + e.getMessage());
        } catch (RateLimitException e) {
            logger.error("Too many requests made to the API too quickly.",e);
            // Too many requests made to the API too quickly
        } catch (InvalidRequestException e) {
            logger.error("Invalid parameters were supplied to Stripe's API.",e);
            // Invalid parameters were supplied to Stripe's API
        } catch (AuthenticationException e) {
            logger.error("Authentication with Stripe's API failed.",e);
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
        } catch (APIConnectionException e) {
            logger.error("Network communication with Stripe failed.",e);
            // Network communication with Stripe failed
        } catch (StripeException e) {
            logger.error("Display a very generic error to the user, and maybe send.",e);
            // Display a very generic error to the user, and maybe send
            // yourself an email
        } catch (Exception e) {
            logger.error("Something else happened, completely unrelated to Stripe.",e);
            // Something else happened, completely unrelated to Stripe
        }
        return ephemeralKey;
    }

    @Override
    public RequestOptions getStripeRequestOptions() {
       return RequestOptions.builder().setApiKey(STRIPE_API_KEY).build();
    }

    @Override
    public Map<String, Object> createdPayment(PaymentDTO paymentDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount",paymentDTO.getAmount());
            params.put("currency", "sgd");
            params.put("description", "test capture");
            params.put("source", "tok_mastercard");
//            Charge charge = Charge.create(chargeParams,getStripeRequestOptions());
            Charge.create(params,getStripeRequestOptions());
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            logger.info("Status is: " + e.getCode());
            logger.info("Message is: " + e.getMessage());
        } catch (RateLimitException e) {
            logger.error("Too many requests made to the API too quickly.",e);
            // Too many requests made to the API too quickly
        } catch (InvalidRequestException e) {
            logger.error("Invalid parameters were supplied to Stripe's API.",e);
            // Invalid parameters were supplied to Stripe's API
        } catch (AuthenticationException e) {
            logger.error("Authentication with Stripe's API failed.",e);
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
        } catch (APIConnectionException e) {
            logger.error("Network communication with Stripe failed.",e);
            // Network communication with Stripe failed
        } catch (StripeException e) {
            logger.error("Display a very generic error to the user, and maybe send.",e);
            // Display a very generic error to the user, and maybe send
            // yourself an email
        } catch (Exception e) {
            logger.error("Something else happened, completely unrelated to Stripe.",e);
            // Something else happened, completely unrelated to Stripe
        }
        return result;
    }


    @Override
    public Map<String, Object> createdCapturePayment(PaymentDTO paymentDTO) {
        paymentDTO.setCapture(Constants.IS_CAPTURED_NO);//false 预收款
        return createdPayment(paymentDTO);
    }

    @Override
    public Map<String,Object> confirmCapturePayment(String paymentId,Long amount){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Charge charge = Charge.retrieve(paymentId,getStripeRequestOptions());
            if(charge != null){
                charge.setAmount(amount);//可设定收取金额，小于等于预付款金额
            }
            charge.capture(getStripeRequestOptions());
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            logger.info("Status is: " + e.getCode());
            logger.info("Message is: " + e.getMessage());
        } catch (RateLimitException e) {
            logger.error("Too many requests made to the API too quickly.",e);
            // Too many requests made to the API too quickly
        } catch (InvalidRequestException e) {
            logger.error("Invalid parameters were supplied to Stripe's API.",e);
            // Invalid parameters were supplied to Stripe's API
        } catch (AuthenticationException e) {
            logger.error("Authentication with Stripe's API failed.",e);
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
        } catch (APIConnectionException e) {
            logger.error("Network communication with Stripe failed.",e);
            // Network communication with Stripe failed
        } catch (StripeException e) {
            logger.error("Display a very generic error to the user, and maybe send.",e);
            // Display a very generic error to the user, and maybe send
            // yourself an email
        } catch (Exception e) {
            logger.error("Something else happened, completely unrelated to Stripe.",e);
            // Something else happened, completely unrelated to Stripe
        }
        return result;
    }

    @Override
    public Map<String, Object> createdCustomer(CustomerDTO dto) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("description", dto.getDescription());
            customerParams.put("source", "tok_mastercard");
            Customer.create(customerParams,getStripeRequestOptions());
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            logger.info("Status is: " + e.getCode());
            logger.info("Message is: " + e.getMessage());
        } catch (RateLimitException e) {
            logger.error("Too many requests made to the API too quickly.",e);
            // Too many requests made to the API too quickly
        } catch (InvalidRequestException e) {
            logger.error("Invalid parameters were supplied to Stripe's API.",e);
            // Invalid parameters were supplied to Stripe's API
        } catch (AuthenticationException e) {
            logger.error("Authentication with Stripe's API failed.",e);
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
        } catch (APIConnectionException e) {
            logger.error("Network communication with Stripe failed.",e);
            // Network communication with Stripe failed
        } catch (StripeException e) {
            logger.error("Display a very generic error to the user, and maybe send.",e);
            // Display a very generic error to the user, and maybe send
            // yourself an email
        } catch (Exception e) {
            logger.error("Something else happened, completely unrelated to Stripe.",e);
            // Something else happened, completely unrelated to Stripe
        }
        return result;
    }


//        Map<String, Object> result = new HashMap<String, Object>();
//        try {
//
//        } catch (CardException e) {
//            // Since it's a decline, CardException will be caught
//            logger.info("Status is: " + e.getCode());
//            logger.info("Message is: " + e.getMessage());
//        } catch (RateLimitException e) {
//            logger.error("Too many requests made to the API too quickly.",e);
//            // Too many requests made to the API too quickly
//        } catch (InvalidRequestException e) {
//            logger.error("Invalid parameters were supplied to Stripe's API.",e);
//            // Invalid parameters were supplied to Stripe's API
//        } catch (AuthenticationException e) {
//            logger.error("Authentication with Stripe's API failed.",e);
//            // Authentication with Stripe's API failed
//            // (maybe you changed API keys recently)
//        } catch (APIConnectionException e) {
//            logger.error("Network communication with Stripe failed.",e);
//            // Network communication with Stripe failed
//        } catch (StripeException e) {
//            logger.error("Display a very generic error to the user, and maybe send.",e);
//            // Display a very generic error to the user, and maybe send
//            // yourself an email
//        } catch (Exception e) {
//            logger.error("Something else happened, completely unrelated to Stripe.",e);
//            // Something else happened, completely unrelated to Stripe
//        }
//        return result;

    @Override
    public Customer getCustomerById(String id) {
        Customer customer = null;
        try {
            customer = Customer.retrieve(id,getStripeRequestOptions());
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            logger.info("Status is: " + e.getCode());
            logger.info("Message is: " + e.getMessage());
        } catch (RateLimitException e) {
            logger.error("Too many requests made to the API too quickly.",e);
            // Too many requests made to the API too quickly
        } catch (InvalidRequestException e) {
            logger.error("Invalid parameters were supplied to Stripe's API.",e);
            // Invalid parameters were supplied to Stripe's API
        } catch (AuthenticationException e) {
            logger.error("Authentication with Stripe's API failed.",e);
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
        } catch (APIConnectionException e) {
            logger.error("Network communication with Stripe failed.",e);
            // Network communication with Stripe failed
        } catch (StripeException e) {
            logger.error("Display a very generic error to the user, and maybe send.",e);
            // Display a very generic error to the user, and maybe send
            // yourself an email
        } catch (Exception e) {
            logger.error("Something else happened, completely unrelated to Stripe.",e);
            // Something else happened, completely unrelated to Stripe
        }
        return customer;
    }
}
