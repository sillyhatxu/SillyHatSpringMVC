package com.sillyhat.springmvc.stripe.service.impl;

import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import com.sillyhat.springmvc.stripe.mapper.UserMapper;
import com.sillyhat.springmvc.stripe.service.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;



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
    public void addUserCard(UserCardDTO dto) {
        userMapper.addUserCard(dto);
    }

    @Override
    public void updateUserCard(UserCardDTO dto) {
        userMapper.updateUserCard(dto);
    }
}
