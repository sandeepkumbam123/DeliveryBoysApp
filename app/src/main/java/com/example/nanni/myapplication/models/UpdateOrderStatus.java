package com.example.nanni.myapplication.models;

/**
 * Created by skumbam on 9/16/17.
 */

public class UpdateOrderStatus {
    public UpdateOrderStatus(String orderId, String orderStatus) {
        this.OrderId = orderId;
        this.OrderStatus = orderStatus;
    }

    private String OrderId;
    private String OrderStatus;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.OrderStatus = orderStatus;
    }
}
