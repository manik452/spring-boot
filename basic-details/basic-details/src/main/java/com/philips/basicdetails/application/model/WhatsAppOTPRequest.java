package com.philips.basicdetails.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppOTPRequest implements Serializable {
    private String from;
    private String to;
    private String messageId;
    private WhatsAppContentModel content;

}
