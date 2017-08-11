package com.sillyhat.springmvc.stripe.dto;

import java.io.Serializable;

public class UserCardDTO implements Serializable {

    private static final long serialVersionUID = 7197663497307820188L;
    private Long id;

    private Long userId;

    private String cardId;

    private String cardNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "UserCardDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
