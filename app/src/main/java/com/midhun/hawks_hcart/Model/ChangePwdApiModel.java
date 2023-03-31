package com.midhun.hawks_hcart.Model;

import com.midhun.hawks_hcart.View.ChangePwd;

import java.util.List;

public class ChangePwdApiModel {
    public ChangePwd getStatus() {
        return status;
    }

    public void setStatus(ChangePwd status) {
        this.status = status;
    }

    ChangePwd status;

    public ChangePwdApiModel(ChangePwd status) {
        this.status = status;
    }
}
