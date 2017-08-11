package com.sillyhat.stripe.business;

import com.sillyhat.springmvc.stripe.dto.CardsDTO;
import com.sillyhat.springmvc.stripe.dto.CustomerDTO;
import com.sillyhat.springmvc.stripe.dto.SourcesDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.stripe.utils.JunitTestSupport;
import com.stripe.model.Customer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomerTest extends JunitTestSupport{

    private final Logger logger = LoggerFactory.getLogger(CustomerTest.class);

    @Autowired
    private StripeService stripeService;

    @Test
    public void testCreatedCardToken(){
        CardsDTO dto = new CardsDTO();
        dto.setNumber("371449635398431");
        dto.setExpMonth("10");
        dto.setExpYear("2019");
        dto.setCvc("111");
        String tokenId = stripeService.createdCardToken(dto);
        logger.info("token id -----> {}",tokenId);
    }


    @Test
    public void testCreatedCustomer() {
//        https://stripe.com/docs/testing#cards
        CustomerDTO customerDTO = new CustomerDTO();
        SourcesDTO sourcesDTO = new SourcesDTO();
//        customerDTO.setObject("customer");
//        customerDTO.setCurrency("sgd");
        customerDTO.setEmail("XUSHIKUAN1@test.com");
        customerDTO.setDescription("Test XUSHIKUAN1");
        CardsDTO cardsDTO = new CardsDTO();
        cardsDTO.setNumber("371449635398431");
        cardsDTO.setExpMonth("10");
        cardsDTO.setExpYear("2019");
        cardsDTO.setCvc("111");
        List<CardsDTO> cardsDTOList = new ArrayList<CardsDTO>();
        cardsDTOList.add(cardsDTO);
        sourcesDTO.setData(cardsDTOList);
        customerDTO.setSources(sourcesDTO);
        stripeService.createdCustomer(customerDTO);
    }

    @Test
    public void testUpdatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        SourcesDTO sourcesDTO = new SourcesDTO();
        customerDTO.setId("cus_BAk8z6cdZStLRF");
        customerDTO.setEmail("xushikuan1@163.com");
        customerDTO.setDescription("Test XUSHIKUAN1");
        CardsDTO cardsDTO = new CardsDTO();
        cardsDTO.setNumber("371449635398431");
        cardsDTO.setExpMonth("10");
        cardsDTO.setExpYear("2019");
        cardsDTO.setCvc("111");
        List<CardsDTO> cardsDTOList = new ArrayList<CardsDTO>();
        cardsDTOList.add(cardsDTO);
        sourcesDTO.setData(cardsDTOList);
        customerDTO.setSources(sourcesDTO);
        stripeService.updatedCustomer(customerDTO);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = stripeService.getCustomerById("cus_BAk8z6cdZStLRF");
        logger.info(customer.toJson());
    }

}
