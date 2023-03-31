package com.midhun.hawks_hcart.Model;

import com.midhun.hawks_hcart.View.ShowAddress;

import java.util.List;

public class ShowAddressApiModel {
    List<ShowAddress> status;

    public ShowAddressApiModel(List<ShowAddress> status) {
        this.status = status;
    }

    public List<ShowAddress> getStatus() {
        return status;
    }

    public void setStatus(List<ShowAddress> status) {
        this.status = status;
    }
}
