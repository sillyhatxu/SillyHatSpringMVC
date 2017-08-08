package com.sillyhat.springmvc.stripe.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -139672065472160404L;

    private Long id;

    private String mozatId;

    private String customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMozatId() {
        return mozatId;
    }

    public void setMozatId(String mozatId) {
        this.mozatId = mozatId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", mozatId='" + mozatId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
