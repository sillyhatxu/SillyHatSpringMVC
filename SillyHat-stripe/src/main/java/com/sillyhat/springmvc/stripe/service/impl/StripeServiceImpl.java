package com.sillyhat.springmvc.stripe.service.impl;

import com.sillyhat.base.utils.Constants;
import com.sillyhat.springmvc.stripe.dto.CardsDTO;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.sillyhat.springmvc.stripe.dto.SourcesDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//            params.put("description", "test capture");
            params.put("source", paymentDTO.getSource());
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
    public Customer createdCustomer(CustomerDTO dto) {
//        Map<String, Object> result = new HashMap<String, Object>();
        Customer customer = null;
        try {
            Map<String, Object> customerParams = new HashMap<String, Object>();
//            customerParams.put("object",dto.getObject());
//            customerParams.put("currency",dto.getCurrency());
            customerParams.put("email",dto.getEmail());
            customerParams.put("description", dto.getDescription());
            customer = Customer.create(customerParams,getStripeRequestOptions());
            if(dto.getSources() != null){
                Map<String, Object> sources = new HashMap<String, Object>();
                SourcesDTO sourcesDTO = dto.getSources();
                if(sourcesDTO.getData() != null && sourcesDTO.getData().size() > 0){
                    List<CardsDTO> cardsDTOList = sourcesDTO.getData();
                    List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
                    for (CardsDTO cardsDTO : cardsDTOList){
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("source", createdCardToken(cardsDTO));
                        customer.getSources().create(params,getStripeRequestOptions());
                    }
                    sources.put("data",cardList);
                }
                customerParams.put("sources",sources);
            }

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

    @Override
    public Customer updatedCustomer(CustomerDTO dto) {
//        Map<String, Object> result = new HashMap<String, Object>();
        Customer customer = null;
        try {
            customer = Customer.retrieve(dto.getId(),getStripeRequestOptions());
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email",dto.getEmail());
            customerParams.put("description", dto.getDescription());
            Customer updateCustomer = customer.update(customerParams,getStripeRequestOptions());
            if(dto.getSources() != null){
                SourcesDTO sourcesDTO = dto.getSources();
                if(sourcesDTO.getData() != null && sourcesDTO.getData().size() > 0){
                    List<CardsDTO> cardsDTOList = sourcesDTO.getData();
                    for (CardsDTO cardsDTO : cardsDTOList){
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("source", createdCardToken(cardsDTO));
                        updateCustomer.getSources().create(params,getStripeRequestOptions());
                    }
                }
            }
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

    @Override
    public String createdCardToken(CardsDTO dto) {
        String result = "";
        try {
            Map<String, Object> tokenParams = new HashMap<String, Object>();
            Map<String, Object> cardParams = new HashMap<String, Object>();
//            cardParams.put("object","card");
            cardParams.put("exp_month",dto.getExp_month());
            cardParams.put("exp_year",dto.getExp_year());
            cardParams.put("number",dto.getNumber());
            cardParams.put("name",dto.getName());
            cardParams.put("address_city",dto.getAddress_city());
            cardParams.put("address_country",dto.getAddress_country());
            cardParams.put("address_line1",dto.getAddress_line1());
            cardParams.put("address_line2",dto.getAddress_line2());
            cardParams.put("address_state",dto.getAddress_state());
            cardParams.put("address_zip",dto.getAddress_zip());
//            cardParams.put("currency",dto.getCurrency());
            cardParams.put("cvc",dto.getCvc());
//            cardParams.put("default_for_currency",dto.getDefault_for_currency());
//            cardParams.put("metadata",dto.getMetadata());
            cardParams.put("name",dto.getName());
            tokenParams.put("card", cardParams);
            Token token = Token.create(tokenParams,getStripeRequestOptions());
            logger.info("token id ----> {}",token.getId());
            return token.getId();
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
