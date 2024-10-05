package com.philips.basicdetails.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppPlaceholderModel {
    private List<String> placeholders;

}
