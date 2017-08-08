package com.sillyhat.springmvc.stripe.dto;

import java.io.Serializable;

public class UserCardDTO implements Serializable {

    private static final long serialVersionUID = 7197663497307820188L;
    private Long id;

    private Long userId;

    private String tokenId;

    private String cardId;

    private String cardNumber;

    private String cardEncrypt;

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

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
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

    public String getCardEncrypt() {
        return cardEncrypt;
    }

    public void setCardEncrypt(String cardEncrypt) {
        this.cardEncrypt = cardEncrypt;
    }

    @Override
    public String toString() {
        return "UserCardDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardEncrypt='" + cardEncrypt + '\'' +
                '}';
    }
}
