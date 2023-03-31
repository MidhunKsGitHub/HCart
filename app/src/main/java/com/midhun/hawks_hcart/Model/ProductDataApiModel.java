package com.midhun.hawks_hcart.Model;

import com.midhun.hawks_hcart.View.Result;

import java.util.List;

public class ProductDataApiModel {
    List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public ProductDataApiModel(List<Result> result) {
        this.result = result;
    }


}
