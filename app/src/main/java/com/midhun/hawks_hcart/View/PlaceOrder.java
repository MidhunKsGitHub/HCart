package com.midhun.hawks_hcart.View;

public class PlaceOrder {
    public PlaceOrder(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
