package com.sillyhat.springmvc.stripe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PaymentDetailDTO implements Serializable {

    private static final long serialVersionUID = 9181222411667774617L;

    private String customerId;

    @JsonProperty("mozat_id")
    private String mozatId;

    private Long amount;

    /**
     * The type of payment source. Should be "card".
     */
    private String object;

    /**
     * Two digit number representing the card's expiration month.
     */
    private String expMonth;

    /**
     * Two or four digit number representing the card's expiration year.
     */
    private String expYear;

    /**
     * The car number, as a string without any separators.
     */
    private String number;

    /**
     *
     */
    private String addressCity;

    /**
     *
     */
    private String addressCountry;

    /**
     *
     */
    private String addressLine1;

    /**
     *
     */
    private String addressLine2;

    /**
     *
     */
    private String addressState;

    /**
     *
     */
    private String addressZip;

    /**
     * Required when adding a card to an account (not applicable to a customers or recipients).
     * The card (which must be a debit card) can be used as a transfer destination for funds in this currency.
     * Currently, the only supported currency for debit card transfers is usd
     */
    private String currency;

    /**
     * Card security code. Highly recommended to always include this value,
     * but it's only required for accounts based in European countries.
     */
    private String cvc;

    /**
     * Only applicable on accounts (not customers or recipients).
     * If you set this to true (or if this is the first external account being added in this currency)
     * this card will become the default external account for its currency.
     */
    private String defaultForCurrency;

    /**
     * A set of key/value pairs that you can attach to a card object.
     * It can be useful for storing additional information about the card in a structured format.
     */
    private String metadata;

    /**
     * Cardholder's full name.
     */
    private String name;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getDefaultForCurrency() {
        return defaultForCurrency;
    }

    public void setDefaultForCurrency(String defaultForCurrency) {
        this.defaultForCurrency = defaultForCurrency;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMozatId() {
        return mozatId;
    }

    public void setMozatId(String mozatId) {
        this.mozatId = mozatId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentDetailDTO{" +
                "customerId='" + customerId + '\'' +
                ", mozatId='" + mozatId + '\'' +
                ", amount=" + amount +
                ", object='" + object + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", number='" + number + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressCountry='" + addressCountry + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressState='" + addressState + '\'' +
                ", addressZip='" + addressZip + '\'' +
                ", currency='" + currency + '\'' +
                ", cvc='" + cvc + '\'' +
                ", defaultForCurrency='" + defaultForCurrency + '\'' +
                ", metadata='" + metadata + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
