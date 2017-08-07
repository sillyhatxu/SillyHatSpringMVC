package com.sillyhat.springmvc.stripe.dto;

import java.util.Map;


/**
 * 付款
 */
public class PaymentDTO {


    /**
     * A positive integer in the smallest currency unit (
     * e.g., 100 cents to charge $1.00 or 100 to charge ¥100,
     * a zero-decimal currency) representing how much to charge the card.
     * The minimum amount is $0.50 US or equivalent in charge currency.
     * 货币最小单位，收取金额
     */
    private Long amount;

    /**
     * 3-letter ISO code for currency.
     * https://stripe.com/docs/currencies
     * 货币单位
     */
    private String currency;

    /**
     *
     * 好像是收取费用
     */
    private Long applicationFee;

    /**
     * default true;
     * Whether or not to immediately capture the charge. When false,
     * the charge issues an authorization (or pre-authorization),
     * and will need to be captured later. Uncaptured charges expire in 7 days.
     * For more information, see authorizing charges and settling later.
     */
    private boolean capture = true;

    /**
     * An arbitrary string which you can attach to a Charge object.
     * It is displayed when in the web interface alongside the charge.
     * Note that if you use Stripe to send automatic email receipts to your customers,
     * your receipt emails will include the description of the charge(s) that they are describing.
     */
    private String description;

    /**
     * If specified, the charge will be attributed to the destination account for tax reporting,
     * and the funds from the charge will be transferred to the destination account.
     * The ID of the resulting transfer will be returned in the transfer field of the response.
     * See the documentation for details.
     * 好像是可以直接转账到另一个账户
     */
    private Map<String,Object> destination;

    /**
     * A string that identifies this transaction as part of a group.
     * See the Connect documentation for details.
     */
    private String transferGroup;


    /**
     * The Stripe account ID that these funds are intended for.
     * Automatically set if you use the destination parameter.
     * See the Connect documentation for details.
     */
    private String on_behalf_of;

    /**
     * Set of key/value pairs that you can attach to an object.
     * It can be useful for storing additional information about the object in a structured format.
     * Individual keys can be unset by posting an empty value to them.
     * All keys can be unset by posting an empty value to metadata.
     */
    private Map<String,Object> metadata;

    /**
     * The email address to send this charge's receipt to.
     * The receipt will not be sent until the charge is paid.
     * If this charge is for a customer,
     * the email address specified here will override the customer's email address.
     * Receipts will not be sent for test mode charges.
     * If receipt_email is specified for a charge in live mode,
     * a receipt will be sent regardless of your email settings.
     * 收款时发送电子邮件
     */
    private String receiptEmail;

    /**
     * Shipping information for the charge.
     * Helps prevent fraud on charges for physical goods.
     * For more information,
     * see the Charge object documentation.
     */
    private Map<String,Object> shipping;

    /**
     * customer ID
     */
    private String customer;

    /**
     * A payment source to be charged, such as a credit card.
     * If you also pass a customer ID,
     * the source must be the ID of a source belonging to the customer (e.g., a saved card).
     * Otherwise, if you do not pass a customer ID,
     * the source you provide must either be a token,
     * like the ones returned by Stripe.js, or a Map containing a user's credit card details,
     * with the options described below. Although not all information is required,
     * the extra info helps prevent fraud.
     * 资金来源，如果customer不为空，则此资金来源卡号要与customer绑定
     */
    private Map<String,Object> source;

    /**
     * An arbitrary string to be displayed on your customer's credit card statement.
     * This may be up to 22 characters. As an example,
     * if your website is RunClub and the item you're charging for is a race ticket,
     * you may want to specify a statement_descriptor of RunClub 5K race ticket.
     * The statement description may not include <>"' characters,
     * and will appear on your customer's statement in capital letters.
     * Non-ASCII characters are automatically stripped.
     * While most banks display this information consistently,
     * some may display it incorrectly or not at all.
     * 信用卡对账单上显示的字符串（22个字符）不包含<>"' 
     */
    private String statementDescriptor;


    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(Long applicationFee) {
        this.applicationFee = applicationFee;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getDestination() {
        return destination;
    }

    public void setDestination(Map<String, Object> destination) {
        this.destination = destination;
    }

    public String getTransferGroup() {
        return transferGroup;
    }

    public void setTransferGroup(String transferGroup) {
        this.transferGroup = transferGroup;
    }

    public String getOn_behalf_of() {
        return on_behalf_of;
    }

    public void setOn_behalf_of(String on_behalf_of) {
        this.on_behalf_of = on_behalf_of;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }

    public void setReceiptEmail(String receiptEmail) {
        this.receiptEmail = receiptEmail;
    }

    public Map<String, Object> getShipping() {
        return shipping;
    }

    public void setShipping(Map<String, Object> shipping) {
        this.shipping = shipping;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", applicationFee=" + applicationFee +
                ", capture=" + capture +
                ", description='" + description + '\'' +
                ", destination=" + destination +
                ", transferGroup='" + transferGroup + '\'' +
                ", on_behalf_of='" + on_behalf_of + '\'' +
                ", metadata=" + metadata +
                ", receiptEmail='" + receiptEmail + '\'' +
                ", shipping=" + shipping +
                ", customer='" + customer + '\'' +
                ", source=" + source +
                ", statementDescriptor='" + statementDescriptor + '\'' +
                '}';
    }
}
