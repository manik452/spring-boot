package com.philips.basicdetails.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserEntity {
    private int userStatus;
    private Date tempLockDate;
    private String password;
}
