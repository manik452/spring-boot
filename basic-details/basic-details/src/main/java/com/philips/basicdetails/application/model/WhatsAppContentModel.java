package com.philips.basicdetails.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppContentModel {
    private String templateName;
    private WhatsAppTemplateDataModel templateData;
    private String language;
}
