package com.sillyhat.test;

import com.sillyhat.base.utils.UUIDUtils;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StripeTest {

    private static final Logger logger = LoggerFactory.getLogger(StripeTest.class);


//    Publishable key
//    pk_test_erPiOQuziqWrCotX1bps9AFw
//    Secret key
//    sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv
    public static void main(String[] args) {

//        createdPayment();//创建交易信息
//        createdPayment(UUIDUtils.getUUID());//创建交易信息
//        idempotentRequest();//重复交易
//        queryPaymentById1("ch_1AmLCcAuC6WOU0qqVQjY6jI9");//根据交易ID查询交易信息
//        queryPaymentById2("ch_1AmLHKAuC6WOU0qqg14tennl");//查询支付详情
//        reimburseTransaction();//退款申请
//        balanceRetrieve();//查询余额
//        refundPaymentById("ch_1AmKmrAuC6WOU0qqhWKDeBIg",88888);//退款申请
//        createdCustomer();//Create a customer创建客户
//        queryCustomerByID("cus_B8ZiL9YoFFduqA");//查询客户
//        queryCustomerByLimit(20);//查询客户信息
        updateCustomerByID("cus_B8ZiL9YoFFduqA");//修改客户信息
        deleteCustomerByID("");
    }


    /**
     * 处理重复请求
     */
    public static void idempotentRequest(){
        String uuid = UUIDUtils.getUUID();
        createdPayment(uuid);
        createdPayment(uuid);
        createdPayment(uuid);
    }

    public static void createdCustomer(){
        try {
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("description", "Customer for ava.robinson@example.com");
            customerParams.put("source", "tok_visa");
            // ^ obtained with Stripe.js
            Customer customer = Customer.create(customerParams);
            logger.info("--------------------------------------");
            logger.info("CustomerId : {} ",customer.getId());
            logger.info("BusinessVatId : {} ",customer.getBusinessVatId());
            logger.info("Description : {} ",customer.getDescription());
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
    }

    public static void queryCustomerByID(String customerId){
        try {
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
            Customer customer = Customer.retrieve(customerId);
            logger.info("--------------------------------------");
            logger.info("CustomerId : {} ",customer.getId());
            logger.info("BusinessVatId : {} ",customer.getBusinessVatId());
            logger.info("Description : {} ",customer.getDescription());
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
    }
    public static void updateCustomerByID(String customerId){
        try {
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
            Customer cu = Customer.retrieve(customerId);
            Map<String, Object> updateParams = new HashMap<String, Object>();
            updateParams.put("description", "Customer for anthony.wilson@example.com");
            Customer customer = cu.update(updateParams);
            logger.info("--------------------------------------");
            logger.info("CustomerId : {} ",customer.getId());
            logger.info("BusinessVatId : {} ",customer.getBusinessVatId());
            logger.info("Description : {} ",customer.getDescription());
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
    }
    public static void deleteCustomerByID(String customerId){
        try {
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
            Customer cu = Customer.retrieve("cus_B9Ddq40yr1vh1a");
            DeletedCustomer deletedCustomer = cu.delete();
            logger.info("--------------------------------------");
            logger.info("CustomerId : {} ",deletedCustomer.getId());
            logger.info("deleted : {} ",deletedCustomer.getDeleted());
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
    }

    /**
     * 创建交易信息
     */
    public static void createdPayment(){
        try {
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";

            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("amount", 2000);
            chargeParams.put("currency", "sgd");
            chargeParams.put("source", "tok_visa");
            // ^ obtained with Stripe.js
            Map<String, String> initialMetadata = new HashMap<String, String>();
            initialMetadata.put("order_id", "6735");
            chargeParams.put("metadata", initialMetadata);
            Charge charge = Charge.create(chargeParams);
            logger.info("Amount : {} ",charge.getAmount());
            logger.info("AmountRefunded : {} ",charge.getAmountRefunded());
            logger.info("Application : {} ",charge.getApplication());
            logger.info("ApplicationFee : {} ",charge.getApplicationFee());
            logger.info("BalanceTransaction : {} ",charge.getBalanceTransaction());
            logger.info("Customer : {} ",charge.getCustomer());
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
    }

    /**
     * 创建交易
     * @param idempotencyKey 交易ID
     */
    public static void createdPayment(String idempotencyKey){
        try {
            // Use Stripe's library to make requests...
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("amount", 33333);
            chargeParams.put("currency", "sgd");
            chargeParams.put("description", "Charge for heixiushamao@heihei.com");
            chargeParams.put("source", "tok_mastercard");
            // ^ obtained with Stripe.js
            logger.info("idempotencyKey is {}",idempotencyKey);
            RequestOptions requestOptions = RequestOptions.builder().setIdempotencyKey(idempotencyKey).build();
            //创建交易
            Charge charge = Charge.create(chargeParams, requestOptions);
            logger.info("Amount : {} ",charge.getAmount());
            logger.info("AmountRefunded : {} ",charge.getAmountRefunded());
            logger.info("Application : {} ",charge.getApplication());
            logger.info("ApplicationFee : {} ",charge.getApplicationFee());
            logger.info("BalanceTransaction : {} ",charge.getBalanceTransaction());
            logger.info("Customer : {} ",charge.getCustomer());
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
    }


    /**
     * 查询消费记录
     */
    public static void queryPaymentById1(String paymentId){
        Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
        RequestOptions requestOptions = RequestOptions.builder().setApiKey("sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv").build();
        try {
            //交易查询
            Charge charge = Charge.retrieve(paymentId, requestOptions);
            logger.info("id : {} ",charge.getId());
            logger.info("status : {} ",charge.getStatus());
            logger.info("Amount : {} ",charge.getAmount());
            logger.info("AmountRefunded : {} ",charge.getAmountRefunded());
            logger.info("Application : {} ",charge.getApplication());
            logger.info("ApplicationFee : {} ",charge.getApplicationFee());
            logger.info("BalanceTransaction : {} ",charge.getBalanceTransaction());
            logger.info("Customer : {} ",charge.getCustomer());
        } catch (InvalidRequestException e) {
            logger.error("InvalidRequestException",e);
        }catch (AuthenticationException e) {
            logger.error("AuthenticationException",e);
        } catch (APIConnectionException e) {
            logger.error("APIConnectionException",e);
        } catch (CardException e) {
            logger.error("CardException",e);
        } catch (APIException e) {
            logger.error("APIException",e);
        }
    }



    public static void queryPaymentById2(String paymentId){
        Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
        try {
            Charge charge = Charge.retrieve(paymentId);
            logger.info("\n\r Payment id : {} \n\r amount : {} \n\r currency : {} \n\r status : {}" ,charge.getId(),charge.getAmount(),charge.getCurrency(),charge.getStatus());
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

    }

    public static void queryCustomerByLimit(int limit){
        Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
        try {
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("limit", limit);
            CustomerCollection customerCollection = Customer.list(customerParams);
            logger.info("TotalCount : {} ",customerCollection.getTotalCount());
            logger.info("Count : {} ",customerCollection.getCount());
            logger.info("Data Size : {} ",customerCollection.getData().size());
            Iterable<Customer> itCustomers = customerCollection.autoPagingIterable();
            for (Customer customer : itCustomers) {
                // Do something with customer
                logger.info("--------------------------------------");
                logger.info("CustomerId : {} ",customer.getId());
                logger.info("BusinessVatId : {} ",customer.getBusinessVatId());
                logger.info("Description : {} ",customer.getDescription());
            }
        } catch (InvalidRequestException e) {
            logger.error("InvalidRequestException",e);
        }catch (AuthenticationException e) {
            logger.error("AuthenticationException",e);
        } catch (APIConnectionException e) {
            logger.error("APIConnectionException",e);
        } catch (CardException e) {
            logger.error("CardException",e);
        } catch (APIException e) {
            logger.error("APIException",e);
        }
    }

    /**
     * 检索当前账户余额
     */
    public static void balanceRetrieve(){
        try {
            // Use Stripe's library to make requests...
            Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
            Balance balance = Balance.retrieve();
            logger.info("Available size {}",balance.getAvailable().size());
            logger.info("Pending size {}",balance.getPending().size());
            List<Money> availableList = balance.getAvailable();
            for (Money money : availableList){
                logger.info("---------available money----------");
                logger.info("Amount is {} ,Currency is {}",money.getAmount(),money.getCurrency());
            }
            List<Money> pendingList = balance.getPending();
            for (Money money : pendingList){
                logger.info("---------pending money----------");
                logger.info("Amount is {} ,Currency is {}",money.getAmount(),money.getCurrency());
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
    }

    /**
     * 退款申请
     * @param paymentId
     */
    public static void refundPaymentById(String paymentId,long amount){
        Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
        try {
            // Use Stripe's library to make requests...
            Map<String, Object> refundParams = new HashMap<String, Object>();
            refundParams.put("charge", paymentId);
            refundParams.put("amount",amount);
            Refund refund = Refund.create(refundParams);
            logger.info("---------refund----------");
            logger.info("id is {} ,Amount is {},Description is {},Currency is {},status is {} ",refund.getId(),refund.getAmount(),refund.getDescription(),refund.getCurrency(),refund.getStatus());
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
    }
    public static void queryRefundPaymentById(String refundId){
        Stripe.apiKey = "sk_test_6tiWo6JN3nPuJ0A3fZrXYUAv";
        try {
            // Use Stripe's library to make requests...
            Refund refund = Refund.retrieve(refundId);
            logger.info("---------refund----------");
            logger.info("id is {} ,Amount is {},Description is {},Currency is {},status is {} ",refund.getId(),refund.getAmount(),refund.getDescription(),refund.getCurrency(),refund.getStatus());
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
    }


    public static void queryTest(){
//        try {
//          // Use Stripe's library to make requests...
//        } catch (CardException e) {
//         //Since it's a decline, CardException will be caught
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
    }


}
