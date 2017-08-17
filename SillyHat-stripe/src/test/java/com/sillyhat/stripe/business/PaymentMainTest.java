package com.sillyhat.stripe.business;

import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.stripe.utils.JunitTestSupport;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class PaymentMainTest{

    private final static Logger logger = LoggerFactory.getLogger(PaymentMainTest.class);

    public static void main(String[] args) {
        try {
            String customerDescription = "XUSHIKUAN-6";
            int amount = 3000;
//            String cardNumber = "4242424242424242";
            String cardNumber = "4000000000003055";//可以不验证
//            String cardNumber = "4000000000003063";//必须验证
            int expMonth = 8;
            int expYear = 2028;
            String cvc = "111";
            String returnURL = "http://localhost:8108/SillyHatSpringMVC/";
//            paymentBefore(customerDescription,amount,cardNumber,expMonth,expYear,cvc,returnURL);
//            https://stripe.com/docs/sources/three-d-secure
            paymentAfter("cus_BDLGDPTnkX8SWF",amount,"payment test - 6","src_1AquZmAuC6WOU0qqmBZDzShG");


            //redirect.status value is set to succeeded and three_d_secure.authenticated set to false.
//            Map<String, Object> updateSourceParams = new HashMap<String, Object>();
//            Map<String, Object> updateRedirectParams = new HashMap<String, Object>();
//            updateRedirectParams.put("status", "succeeded");
//            updateRedirectParams.put("return_url", "http://localhost:8108/SillyHatSpringMVC/");
//            updateSourceParams.put("redirect", updateRedirectParams);
//            Map<String, Object> updateThreeDSecureParams = new HashMap<String, Object>();
//            updateThreeDSecureParams.put("card", source.getId());
//            updateThreeDSecureParams.put("authenticated", false);
//            updateSourceParams.put("three_d_secure", updateThreeDSecureParams);
//            source.update(updateSourceParams,getStripeRequestOptions());
//            source.verify(updateSourceParams,getStripeRequestOptions());
//            logger.info("------------source------------\r\n{}",source.toJson());
//            Charge charge = createdPayment(customer.getId(),amount,"payment test - 1",source.getId());
//            logger.info("------------charge------------\r\n{}",charge.toJson());

//            Map<String, Object> sourceParams = new HashMap<String, Object>();
//            sourceParams.put("amount", 3000);
//            sourceParams.put("currency", "sgd");
//            sourceParams.put("type", "three_d_secure");
//            Map<String, Object> redirectParams = new HashMap<String, Object>();
//            redirectParams.put("return_url", "hhttp://localhost:8108/SillyHatSpringMVC/");
//            sourceParams.put("redirect", redirectParams);
//            Map<String, Object> threeDSecureParams = new HashMap<String, Object>();
//            threeDSecureParams.put("card", "src_1AqfjcAuC6WOU0qq8EFNwck8");
//            sourceParams.put("three_d_secure", threeDSecureParams);
//            Source source = Source.create(sourceParams,getStripeRequestOptions());
//            logger.info("------------source------------\r\n{}",source.toJson());
//            Map<String, Object> chargeParams = new HashMap<String, Object>();
//            chargeParams.put("customer", "cus_BD5vtk6U8ee4p4");
//            chargeParams.put("amount", 2000);
//            chargeParams.put("currency", "sgd");
//            chargeParams.put("description", "test 3D");
//            chargeParams.put("source", source.getId());
//            //创建交易
//            Charge charge = Charge.create(chargeParams, getStripeRequestOptions());
//            logger.info("------------charge------------\r\n{}",charge.toJson());
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



    public static void paymentBefore(String customerDescription,int amount,String cardNumber,int expMonth,int expYear,String cvc,String returnURL) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Token token = createdCardToken(cardNumber,expMonth,expYear,cvc);
        logger.info("------------token------------\r\n{}",token.toJson());
        Map<String, Object> result = createdCustomer(customerDescription,token.getId());
        Customer customer = (Customer)result.get("customer");
        Source sourceCard = (Source)result.get("source");
        logger.info("------------customer------------\r\n{}",customer.toJson());
        logger.info("------------sourceCard------------\r\n{}",sourceCard.toJson());
        Map<String, Object> sourceParams = new HashMap<String, Object>();
        sourceParams.put("amount", amount);
        sourceParams.put("currency", "sgd");
        sourceParams.put("type", "three_d_secure");
        //redirect.status value is set to succeeded and three_d_secure.authenticated set to false.
        Map<String, Object> redirectParams = new HashMap<String, Object>();
        redirectParams.put("return_url", returnURL);
//        redirectParams.put("status", "succeeded");
        sourceParams.put("redirect", redirectParams);
        Map<String, Object> threeDSecureParams = new HashMap<String, Object>();
        threeDSecureParams.put("card", sourceCard.getId());
//        threeDSecureParams.put("authenticated", false);
        sourceParams.put("three_d_secure", threeDSecureParams);
        Source source = Source.create(sourceParams,getStripeRequestOptions());
        logger.info("------------source------------\r\n{}",source);
        logger.info("------------customerID------------\r\n{}",customer.getId());
        logger.info("------------sourceID------------\r\n{}",source.getId());
    }

    public static void paymentAfter(String customerId,int amount,String decription,String sourceId) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Charge charge = createdPayment(customerId,amount,decription,sourceId);
        logger.info("------------charge------------\r\n{}",charge.toJson());
    }

    public static RequestOptions getStripeRequestOptions() {
        return RequestOptions.builder().setApiKey("sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv").setStripeVersion("2017-06-05").build();
    }

    private static Token createdCardToken(String cardNumber,int expMonth,int expYear,String cvc) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expMonth);
        cardParams.put("exp_year", expYear);
        cardParams.put("cvc", cvc);
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams,getStripeRequestOptions());
        return token;
    }

    public static Map<String, Object> createdCustomer(String description,String tokenId) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", description);
        Customer customer = Customer.create(customerParams,getStripeRequestOptions());

        Map<String, Object> sourceParams = new HashMap<String, Object>();
        sourceParams.put("type", "card");
        sourceParams.put("usage", "reusable");
//        sourceParams.put("amount", 1000);
//        sourceParams.put("currency", "sgd");
        Map<String, Object> ownerParams = new HashMap<String, Object>();
        ownerParams.put("email", "xushikuan@owner.com");
        sourceParams.put("owner", ownerParams);
        sourceParams.put("token", tokenId);
        Map<String, Object> redirectParams = new HashMap<String, Object>();
        redirectParams.put("return_url", "http://localhost:8108/SillyHatSpringMVC/");
        sourceParams.put("redirect", redirectParams);
        Source source = Source.create(sourceParams,getStripeRequestOptions());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", source.getId());
        customer.getSources().create(params,getStripeRequestOptions());
        result.put("customer",customer);
        result.put("source",source);
        return result;
    }

    public static Charge createdPayment(String customerId,int amount,String description,String sourceId) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("customer", customerId);
//        params.put("original_source", tokenId);
//        params.put("usage", "reusable");
//        Source source = Source.create(params, getStripeRequestOptions());

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("customer", customerId);
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "sgd");
        chargeParams.put("description", description);
        chargeParams.put("source", sourceId);
        //创建交易
        Charge charge = Charge.create(chargeParams, getStripeRequestOptions());
        return charge;
    }
}
