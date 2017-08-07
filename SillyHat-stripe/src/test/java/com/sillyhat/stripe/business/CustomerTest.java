package com.sillyhat.stripe.business;

import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.stripe.utils.JunitTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerTest extends JunitTestSupport{

    private final Logger logger = LoggerFactory.getLogger(CustomerTest.class);

    @Autowired
    private StripeService stripeService;

    @Test
    public void testCreatedCustomer() {
//        https://stripe.com/docs/testing#cards
        CustomerDTO dto = new CustomerDTO();
        stripeService.createdCustomer(dto);
    }


}
