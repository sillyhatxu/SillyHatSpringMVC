package com.sillyhat.stripe.business;

import com.sillyhat.springmvc.stripe.dto.PaymentDTO;
import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.sillyhat.springmvc.stripe.utils.MD5Util;
import com.sillyhat.stripe.utils.JunitTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserTest extends JunitTestSupport{

    private final Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void testCreatedUser() {
        UserDTO dto = new UserDTO();
        dto.setMozatId("test-mozat");
        dto.setCustomerId("test-customer");
        userService.addUser(dto);
    }

    @Test
    public void testGetUserByMozatId() {
        UserDTO dto = userService.getUserByMozatId("test-mozat");
        logger.info(dto.toString());
    }

    @Test
    public void testQueryUserCardList() {
        List<UserCardDTO> list = userService.queryUserCardList(1l);
        for(UserCardDTO dto : list){
            logger.info("---------------------------------");
            logger.info(dto.toString());
            logger.info("---------------------------------");
        }
    }

    @Test
    public void testAddUserCard() {
        UserCardDTO dto = new UserCardDTO();
        dto.setUserId(1l);
        dto.setTokenId("asdfsdfsgsagdasgsagfdgfdgdfhd");
        dto.setCardId("card_1AoOdwAuC6WOU0qqlXQNS6B2");
        dto.setCardNumber("0259");
        dto.setCardEncrypt(MD5Util.toMD5Upper("4000000000000259"));
        userService.addUserCard(dto);
    }

    @Test
    public void testUpdateUserCard() {
        UserCardDTO dto = new UserCardDTO();
        dto.setId(1l);
        dto.setUserId(1l);
        dto.setTokenId("asdfsdfsgsagdasgsagfdgfdgdfhd");
        dto.setCardId("card_1AoOdwAuC6WOU0qqlXQNS6B2");
        dto.setCardNumber("1234");
        dto.setCardEncrypt(MD5Util.toMD5Upper("4000000000000259"));
        userService.updateUserCard(dto);
    }


}
