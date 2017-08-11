package com.sillyhat.springmvc.stripe.service.impl;

import com.sillyhat.springmvc.stripe.dto.CardsDTO;
import com.sillyhat.springmvc.stripe.dto.PaymentDetailDTO;
import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import com.sillyhat.springmvc.stripe.mapper.UserMapper;
import com.sillyhat.springmvc.stripe.service.StripeService;
import com.sillyhat.springmvc.stripe.service.UserService;
import com.sillyhat.springmvc.stripe.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Autowired
    private StripeService stripeService;



    @Override
    public void addUser(UserDTO dto) {
        userMapper.insertUser(dto);
    }

    @Override
    public UserDTO getUserByMozatId(String mozatId) {
        UserDTO userDTO = userMapper.getUserByMozatId(mozatId);
        return userDTO;
    }

    @Override
    public List<UserCardDTO> queryUserCardList(Long userId) {
        return userMapper.queryUserCardList(userId);
    }

    @Override
    public UserCardDTO getUserCardByParams(Long userId, PaymentDetailDTO dto) {
        UserCardDTO userCardDTO = userMapper.getUserCardByParams(userId, MD5Util.toMD5Upper(dto.getNumber()));
        if(userCardDTO != null){
            return userCardDTO;
        }else{
            CardsDTO cardsDTO = new CardsDTO();
            cardsDTO.setCustomerId(dto.getCustomerId());
            cardsDTO.setExpMonth(dto.getExpMonth());
            cardsDTO.setExpYear(dto.getExpYear());
            cardsDTO.setNumber(dto.getNumber());
            cardsDTO.setAddressCity(dto.getAddressCity());
            cardsDTO.setAddressCountry(dto.getAddressCountry());
            cardsDTO.setAddressLine1(dto.getAddressLine1());
            cardsDTO.setAddressLine2(dto.getAddressLine2());
            cardsDTO.setAddressState(dto.getAddressState());
            cardsDTO.setAddressZip(dto.getAddressZip());
            cardsDTO.setCvc(dto.getCvc());
            cardsDTO.setName(dto.getName());
//            String source = stripeService.boundCustomerCard(cardsDTO);//关联银行卡
//            userCardDTO = new UserCardDTO();
//            userCardDTO.setUserId(userId);
//            userCardDTO.setTokenId(source);
//            userCardDTO.setCardEncrypt(MD5Util.toMD5Upper(dto.getNumber()));
            userCardDTO.setCardNumber(dto.getNumber().length() > 0 ? dto.getNumber().substring(dto.getNumber().length()-4,dto.getNumber().length()) : "");
            userMapper.addUserCard(userCardDTO);
        }
        return userCardDTO;
    }

    @Override
    public void addUserCard(UserCardDTO dto) {
        userMapper.addUserCard(dto);
    }

    @Override
    public void updateUserCard(UserCardDTO dto) {
        userMapper.updateUserCard(dto);
    }
}
