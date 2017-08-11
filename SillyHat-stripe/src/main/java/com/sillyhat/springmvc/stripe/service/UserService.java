package com.sillyhat.springmvc.stripe.service;

import com.sillyhat.springmvc.stripe.dto.PaymentDetailDTO;
import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;

import java.util.List;

public interface UserService {

    public void addUser(UserDTO dto);

    public UserDTO getUserByMozatId(String mozatId);

    public List<UserCardDTO> queryUserCardList(Long userId);

    public UserCardDTO getUserCardByParams(Long userId,PaymentDetailDTO dto);

    public void addUserCard(UserCardDTO dto);

    public void updateUserCard(UserCardDTO dto);
}
