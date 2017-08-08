package com.sillyhat.springmvc.stripe.mapper;

import com.sillyhat.springmvc.stripe.dto.UserCardDTO;
import com.sillyhat.springmvc.stripe.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    public Long insertUser(UserDTO dto);

    public UserDTO getUserByMozatId(@Param("mozatId") String mozatId);

    public List<UserCardDTO> queryUserCardList(@Param("userId") Long userId);

    public Long addUserCard(UserCardDTO dto);

    public void updateUserCard(UserCardDTO dto);
}
