package com.sillyhat.springmvc.stripe.service.impl;

import com.sillyhat.base.utils.Constants;
import com.sillyhat.springmvc.stripe.dto.*;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.stripe.exception.*;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @Override
    public EphemeralKey getEphemeralKey(String customerId) {
        EphemeralKey ephemeralKey = null;
        try {
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("customer",customerId);
            RequestOptions requestOptions = RequestOptions.builder().setApiKey(STRIPE_API_KEY).setStripeVersion("2017-06-05").build();
//            params.put("stripe_version", DateUtils.getToday());
            ephemeralKey = EphemeralKey.create(params,requestOptions);
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
        return ephemeralKey;
    }

    @Override
    public RequestOptions getStripeRequestOptions() {
       return RequestOptions.builder().setApiKey(STRIPE_API_KEY).setStripeVersion("2017-06-05").build();
    }

    @Override
//    public Map<String, Object> createdPayment(PaymentDTO paymentDTO) {
    public Map<String, Object> createdPayment(String customer,String cardId,Long amount) {
        return createdPayment(customer,cardId,amount,Constants.IS_CAPTURED_YES);//false 预收款
    }

    @Override
    public Map<String, Object> createdPayment(PaymentDTO paymentDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount",paymentDTO.getAmount());
            params.put("currency", "sgd");
            params.put("source", paymentDTO.getSource());
            params.put("customer", paymentDTO.getCustomer());
//            if(paymentDTO.getApplicationFee() != null)
//                params.put("application_fee", paymentDTO.getApplicationFee());//直接向另一个账户收取费用
            if(StringUtils.isNotEmpty(paymentDTO.getDescription()))
                params.put("description", paymentDTO.getDescription());
//            params.put("destination", paymentDTO.get);//直接转账号
            if(StringUtils.isNotEmpty(paymentDTO.getTransferGroup()))
                //https://stripe.com/docs/connect/charges-transfers#grouping-transactions
                params.put("transfer_group", paymentDTO.getTransferGroup());//将多个订单一次性付款？？？？？
            if(StringUtils.isNotEmpty(paymentDTO.getOnBehalfOf()))
                params.put("on_behalf_of", paymentDTO.getOnBehalfOf());
//            if(StringUtils.isNotEmpty(paymentDTO.getDescription()))
//                params.put("metadata", paymentDTO.get);
            if(StringUtils.isNotEmpty(paymentDTO.getReceiptEmail()))
                params.put("receipt_email", paymentDTO.getReceiptEmail());
//            if(StringUtils.isNotEmpty(paymentDTO.getDescription()))
//                params.put("shipping", paymentDTO.get);
            if(StringUtils.isNotEmpty(paymentDTO.getStatementDescriptor()))
                params.put("statement_descriptor", paymentDTO.getStatementDescriptor());//信用卡账单显示的字符串，长度22，不可录入<>"'
            logger.info("Charge.create ----> {}",params);
            Charge charge = Charge.create(params,getStripeRequestOptions());
            result.put("charge",charge);
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
        return result;
    }

    @Override
    public Map<String, Object> updatePayment(String chargeId, String receiptEmail) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Charge charge = Charge.retrieve(chargeId,getStripeRequestOptions());
            Map<String, Object> updateParams = new HashMap<String, Object>();
            updateParams.put("receipt_email", receiptEmail);
            Charge chargeUpdate = charge.update(updateParams,getStripeRequestOptions());
            result.put("charge",chargeUpdate);
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
        return result;
    }

    @Override
    public Map<String, Object> queryPaymentByParams(Long limit, String startingAfter, String endingBefore) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            if(limit != null)
                params.put("limit", limit);
            if(StringUtils.isNotEmpty(startingAfter) && !startingAfter.equals("###"))
                params.put("starting_after",startingAfter);
            if(StringUtils.isNotEmpty(endingBefore) && !endingBefore.equals("###"))
                params.put("ending_before", endingBefore);
            ChargeCollection collection = Charge.list(params,getStripeRequestOptions());
            result.put("data",collection.getData());
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
        return result;
    }

    private Map<String, Object> createdPayment(String customer,String cardId,Long amount,boolean capture){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount",amount);
            params.put("currency", "sgd");
//            params.put("description", "test capture");
            params.put("source", cardId);
            params.put("customer", customer);
            params.put("capture",capture);
            logger.info("Charge.create ----> {}",params);
            Charge charge = Charge.create(params,getStripeRequestOptions());
            result.put("charge",charge);
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
        return result;
    }

    @Override
    public Map<String, Object> createdCapturePayment(String customer,String cardId,Long amount) {
        return createdPayment(customer,cardId,amount,Constants.IS_CAPTURED_NO);//false 预收款
    }

    @Override
    public Map<String,Object> confirmCapturePayment(String chargeId,Long amount){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Charge charge = Charge.retrieve(chargeId,getStripeRequestOptions());
            if(amount != null){
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("amount",amount);
                charge.capture(params,getStripeRequestOptions());//可设定收取金额，小于等于预付款金额
            }else{
                charge.capture(getStripeRequestOptions());
            }
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
        return result;
    }

    @Override
    public Map<String, Object> createdRefund(String chargeId, Long amount) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("charge", chargeId);
            if(amount != null){
                params.put("amount", amount);
            }
            Refund refund = Refund.create(params,getStripeRequestOptions());
            result.put("refund",refund);
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
        return result;
    }

    @Override
    public Map<String, Object> queryBalanceTransaction(Long limit, String startingAfter, String endingBefore) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            if(limit != null)
                params.put("limit", limit);
            if(StringUtils.isNotEmpty(startingAfter) && !startingAfter.equals("###"))
                params.put("starting_after",startingAfter);
            if(StringUtils.isNotEmpty(endingBefore) && !endingBefore.equals("###"))
                params.put("ending_before", endingBefore);
            BalanceTransactionCollection balanceTransactionCollection = BalanceTransaction.list(params,getStripeRequestOptions());
            result.put("data",balanceTransactionCollection.getData());
//            Iterable<BalanceTransaction> itBalanceTransaction = balanceTransactionCollection.autoPagingIterable();
//            for (BalanceTransaction balanceTransactionomer : itBalanceTransaction) {
//            }
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
        return customer;
    }

    @Override
    public String createdCardToken(CardsDTO dto) {
        String result = "";
        try {
            Map<String, Object> tokenParams = new HashMap<String, Object>();
            Map<String, Object> cardParams = new HashMap<String, Object>();
//            cardParams.put("object","card");
            cardParams.put("exp_month",dto.getExpMonth());
            cardParams.put("exp_year",dto.getExpYear());
            cardParams.put("number",dto.getNumber());
            cardParams.put("name",dto.getName());
            if(StringUtils.isNotEmpty(dto.getAddressCity()))
                cardParams.put("address_city",dto.getAddressCity());
            if(StringUtils.isNotEmpty(dto.getAddressCountry()))
                cardParams.put("address_country",dto.getAddressCountry());
            if(StringUtils.isNotEmpty(dto.getAddressLine1()))
                cardParams.put("address_line1",dto.getAddressLine1());
            if(StringUtils.isNotEmpty(dto.getAddressLine2()))
                cardParams.put("address_line2",dto.getAddressLine2());
            if(StringUtils.isNotEmpty(dto.getAddressState()))
                cardParams.put("address_state",dto.getAddressState());
            if(StringUtils.isNotEmpty(dto.getAddressZip()))
                cardParams.put("address_zip",dto.getAddressZip());
//            cardParams.put("currency",dto.getCurrency());
            cardParams.put("cvc",dto.getCvc());
//            cardParams.put("default_for_currency",dto.getDefault_for_currency());
//            cardParams.put("metadata",dto.getMetadata());
            tokenParams.put("card", cardParams);
            Token token = Token.create(tokenParams,getStripeRequestOptions());
            logger.info("token id ----> {}",token.getId());
            return token.getId();
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
        return result;
    }


//        Map<String, Object> result = new HashMap<String, Object>();
//        try {
//
//        } catch (CardException e) {
//            // Since it's a decline, CardException will be caught
//            logger.error("Status is: " + e.getCode());
//            logger.error("Message is: " + e.getMessage());
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
        return customer;
    }

    @Override
    public Map<String, Object> queryCustomerByParams(Long limit, String startingAfter, String endingBefore) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            if(limit != null)
                params.put("limit", limit);
            if(StringUtils.isEmpty(startingAfter) && !startingAfter.equals("###"))
                params.put("starting_after",startingAfter);
            if(StringUtils.isNotEmpty(endingBefore) && !endingBefore.equals("###"))
                params.put("ending_before", endingBefore);
            CustomerCollection customerCollection = Customer.list(params,getStripeRequestOptions());
            result.put("data",customerCollection.getData());
//            Iterable<Customer> itCustomers = customerCollection.autoPagingIterable();
//            for (Customer customer : itCustomers) {
//                customer.delete();
//            }
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
        return result;
    }

    @Override
    public boolean boundCustomerCard(Long userId,String customerId,String source) {
        boolean bound = false;
        try {
            Customer customer = getCustomerById(customerId);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("source", source);
            Card card = (Card)customer.getSources().create(params,getStripeRequestOptions());
            UserCardDTO dto = new UserCardDTO();
            dto.setUserId(userId);
            dto.setCardNumber(card.getLast4());
            dto.setCardId(card.getId());
            userService.addUserCard(dto);
            bound = true;
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
        return bound;
    }

    @Override
    public Card getCustomerCard(Customer customer,String cardId) {
        try {
            return (Card) customer.getSources().retrieve(cardId,getStripeRequestOptions());
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
        return null;
    }
}
