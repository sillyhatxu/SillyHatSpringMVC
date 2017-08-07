package com.sillyhat.stripe.business;

import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.stripe.utils.JunitTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentTest extends JunitTestSupport{

    private final Logger logger = LoggerFactory.getLogger(PaymentTest.class);

    @Autowired
    private StripeService stripeService;

    @Test
    public void testCreatedCapturePayment() {
        PaymentDTO dto = new PaymentDTO();
        dto.setAmount(5000l);
        stripeService.createdCapturePayment(dto);
    }



    @Test
    public void testCreatedPayment() {
        stripeService.confirmCapturePayment("ch_1Ao5McAuC6WOU0qqFYWUXU2N",100l);
//        Map<String, Object> chargeParams = new HashMap<String, Object>();
//        chargeParams.put("amount", 1000);
//        chargeParams.put("currency", "sgd");
//        chargeParams.put("source", "tok_visa");
//        // ^ obtained with Stripe.js
//        Map<String, String> initialMetadata = new HashMap<String, String>();
//        initialMetadata.put("order_id", "6735");
//        chargeParams.put("metadata", initialMetadata);
//        stripeService.createdPayment(chargeParams);
    }
}
