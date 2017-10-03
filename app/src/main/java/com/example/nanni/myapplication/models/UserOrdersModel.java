package com.example.nanni.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by skumbam on 9/16/17.
 */

public class UserOrdersModel {

/* {
        "ord_id": "248",
        "ord_order_number": "00000310",
        "ord_cart_data_fk": "334",
        "ord_user_fk": "mahammadjubersk2@gmail.com",
        "ord_item_summary_total": "365.00",
        "ord_item_summary_savings_total": "0.00",
        "ord_shipping": "Normal",
        "ord_shipping_total": "0.00",
        "ord_item_shipping_total": "365.00",
        "ord_summary_discount_desc": "",
        "ord_summary_savings_total": "0.00",
        "ord_savings_total": "0.00",
        "ord_surcharge_desc": "",
        "ord_surcharge_total": "0.00",
        "ord_reward_voucher_desc": "",
        "ord_reward_voucher_total": "0.00",
        "ord_tax_rate": "0",
        "ord_tax_total": "0.00",
        "ord_sub_total": "0.00",
        "ord_total": "365.00",
        "ord_total_rows": "1",
        "ord_total_items": "1.00",
        "ord_total_weight": "1.00",
        "ord_total_reward_points": "3650",
        "ord_currency": "INR",
        "ord_exchange_rate": "1.0000",
        "ord_status": "4",
        "ord_date": "2017-08-25 14:07:18",
        "ord_demo_bill_name": "Mahammad Juber",
        "ord_demo_bill_company": "",
        "ord_demo_bill_address_01": ",LB Nagar",
        "ord_demo_bill_city": "Hyderabad",
        "ord_demo_bill_state": "Telangana",
        "ord_demo_bill_store": "LBNAGAR",
        "ord_demo_bill_post_code": "500074",
        "ord_demo_bill_country": "India",
        "ord_demo_email": "mahammadjubersk2@gmail.com",
        "ord_demo_phone": "7893547275",
        "ord_user_latitude": "",
        "ord_user_longitude": "",
        "ord_payment_mode": "Cash On Delivery",
        "ord_type": "standard",
        "ord_pre_date": "",
        "ord_pre_time": "",
        "ord_payment_transaction": "",
        "ord_demo_bill_landmark": "",
        "ord_payment_typeoforder": "",
        "ord_payment_last4": "",
        "ord_payment_bankid": ""
    }*/

    @SerializedName("ord_order_number")
    @Expose
    private String mOrderId;
    @SerializedName("ord_demo_bill_name")
    @Expose
    private String customerName;
    @SerializedName("ord_demo_email")
    @Expose
    private String customerMail;
    @SerializedName("ord_total")
    @Expose
    private String orderAmount;
    @SerializedName("ord_demo_bill_address_01")
    @Expose
    private String orderAddress;
    @SerializedName("ord_demo_bill_city")
    @Expose
    private String orderCity;
    @SerializedName("ord_demo_bill_state")
    @Expose
    private String orderState;
    @SerializedName("ord_demo_bill_store")
    @Expose
    private String orderStore;
    @SerializedName("ord_demo_phone")
    @Expose
    private String customerPhoneNumber;
    @SerializedName("ord_payment_mode")
    @Expose
    private String orderPaymentType;
    @SerializedName("ord_user_latitude")
    @Expose
    private String orderLatitude;
    @SerializedName("ord_user_longitude")
    @Expose
    private  String orderLongitude;

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderStore() {
        return orderStore;
    }

    public void setOrderStore(String orderStore) {
        this.orderStore = orderStore;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getOrderPaymentType() {
        return orderPaymentType;
    }

    public void setOrderPaymentType(String orderPaymentType) {
        this.orderPaymentType = orderPaymentType;
    }

    public String getOrderLatitude() {
        return orderLatitude;
    }

    public void setOrderLatitude(String orderLatitude) {
        this.orderLatitude = orderLatitude;
    }

    public String getOrderLongitude() {
        return orderLongitude;
    }

    public void setOrderLongitude(String orderLongitude) {
        this.orderLongitude = orderLongitude;
    }
}
