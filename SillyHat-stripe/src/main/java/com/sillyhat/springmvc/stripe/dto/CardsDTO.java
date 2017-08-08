package com.sillyhat.springmvc.stripe.dto;

import java.io.Serializable;

public class CardsDTO implements Serializable {

    private static final long serialVersionUID = 9181222411667774617L;

    /**
     * The type of payment source. Should be "card".
     */
    private String object;

    /**
     * Two digit number representing the card's expiration month.
     */
    private String exp_month;

    /**
     * Two or four digit number representing the card's expiration year.
     */
    private String exp_year;

    /**
     * The card number, as a string without any separators.
     */
    private String number;

    /**
     *
     */
    private String address_city;

    /**
     *
     */
    private String address_country;

    /**
     *
     */
    private String address_line1;

    /**
     *
     */
    private String address_line2;

    /**
     *
     */
    private String address_state;

    /**
     *
     */
    private String address_zip;

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
    private String default_for_currency;

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

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_country() {
        return address_country;
    }

    public void setAddress_country(String address_country) {
        this.address_country = address_country;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_zip() {
        return address_zip;
    }

    public void setAddress_zip(String address_zip) {
        this.address_zip = address_zip;
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

    public String getDefault_for_currency() {
        return default_for_currency;
    }

    public void setDefault_for_currency(String default_for_currency) {
        this.default_for_currency = default_for_currency;
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

}
