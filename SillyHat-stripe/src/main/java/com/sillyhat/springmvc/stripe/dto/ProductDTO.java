package com.sillyhat.springmvc.stripe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = -4138261338495203812L;

    @JsonProperty("product_id")
    private String productId;

    private String size;

    private String quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
