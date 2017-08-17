package com.sillyhat.stripe.business;

import com.sillyhat.springmvc.stripe.dto.CardsDTO;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.SourcesDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.stripe.utils.JunitTestSupport;
import com.stripe.exception.*;
import com.stripe.model.Customer;
import com.stripe.model.Source;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerMainTest{

    private static final Logger logger = LoggerFactory.getLogger(CustomerMainTest.class);


    public static void main(String[] args) {
        try {
            String customerDescription = "XUSHIKUAN2";
            String email = customerDescription + "@163.com";
            int amount = 3000;
            String cardNumber = "4242424242424242";
//            int expMonth = 1;
//            int expYear = 2019;
//            String cvc = "111";
//            String cardNumber = "4012888888881881";
            int expMonth = 12;
            int expYear = 2030;
            String cvc = "222";

//        String cardNumber = "4000000000003055";//可以不验证
//        String cardNumber = "4000000000003063";//必须验证
            String returnURL = "http://localhost:8108/SillyHatSpringMVC/";
            Customer customer = createdCustomer(customerDescription,email);
            Token token = createdCardToken(cardNumber,expMonth,expYear,cvc,customerDescription);
            createdSource(token.getId(),customer,returnURL,email);
//            boolean update = updateSource("src_1Ar2KTAuC6WOU0qqqsdTyhJ8",expMonth,expYear,cvc,customerDescription);
//            boolean update = updateSource("cus_BDTHSZeu4MfByL",token.getId(),returnURL,email);
//            logger.info("update result ---> {}",update);
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            logger.error("Status is: " + e.getCode());
            logger.error("Message is: " + e.getMessage());
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
    }


    public static RequestOptions getStripeRequestOptions() {
        return RequestOptions.builder().setApiKey("sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv").setStripeVersion("2017-06-05").build();
    }

    public static Customer createdCustomer(String description,String email) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", description);
        customerParams.put("email", email);
        Customer customer = Customer.create(customerParams,getStripeRequestOptions());
        logger.info("Customer id ---->  {}", customer.getId());
        return customer;
    }

    private static Token createdCardToken(String cardNumber, int expMonth, int expYear, String cvc,String name) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expMonth);
        cardParams.put("exp_year", expYear);
        cardParams.put("cvc", cvc);
        cardParams.put("name", name);
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams,getStripeRequestOptions());
        return token;
    }

    public static Source createdSource(String tokenId,Customer customer,String returnURL,String email) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Token token = Token.retrieve(tokenId,getStripeRequestOptions());
        if(token.getCard().getFingerprint().equals("gHmHQvazFyYJ9qG1")){
            return null;
        }
        Map<String, Object> sourceParams = new HashMap<String, Object>();
        sourceParams.put("type", "card");
        sourceParams.put("usage", "reusable");
        Map<String, Object> ownerParams = new HashMap<String, Object>();
        ownerParams.put("email", email);
        sourceParams.put("owner", ownerParams);
        sourceParams.put("token", token.getId());
        Map<String, Object> redirectParams = new HashMap<String, Object>();
        redirectParams.put("return_url", returnURL);
        sourceParams.put("redirect", redirectParams);
        Source source = Source.create(sourceParams,getStripeRequestOptions());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", source.getId());
        customer.getSources().create(params,getStripeRequestOptions());
        return source;
    }

    public static boolean updateSource(String sourceId,int expMonth, int expYear, String cvc,String name) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Source source = Source.retrieve(sourceId,getStripeRequestOptions());
        Map<String, Object> card = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        card.put("exp_month", expMonth);
        card.put("exp_year", expYear);
        card.put("cvc", cvc);
        card.put("name", name);
        cardParams.put("card", card);
        source.update(cardParams,getStripeRequestOptions());
        return true;
    }
    public static boolean updateSource(String customerId,String tokenId,String returnURL,String email) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Customer customer = Customer.retrieve(customerId,getStripeRequestOptions());
        Map<String, Object> sourceParams = new HashMap<String, Object>();
        sourceParams.put("type", "card");
        sourceParams.put("usage", "reusable");
        Map<String, Object> ownerParams = new HashMap<String, Object>();
        ownerParams.put("email", email);
        sourceParams.put("owner", ownerParams);
        sourceParams.put("token", tokenId);
        Map<String, Object> redirectParams = new HashMap<String, Object>();
        redirectParams.put("return_url", returnURL);
        sourceParams.put("redirect", redirectParams);
        Source source = Source.create(sourceParams,getStripeRequestOptions());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", source.getId());
        customer.getSources().create(params,getStripeRequestOptions());

        return true;
    }







}
