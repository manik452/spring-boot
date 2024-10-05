package com.philips.basicdetails.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpViewModel {
    private String mobileNo;
    private String oneTimePassword;
}
