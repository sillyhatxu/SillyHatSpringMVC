package com.sillyhat.springmvc.stripe.service.impl;

import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
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
    public RequestOptions getStripeRequestOptions() {
       return RequestOptions.builder().setApiKey(STRIPE_API_KEY).build();
    }

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
