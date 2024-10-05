package com.philips.basicdetails.common;

import lombok.Data;

public enum SystemUserStatus {
    ACTIVE(1),
    AUTO_LOCKED(2);
    private int type;
    private SystemUserStatus(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
