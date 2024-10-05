package com.philips.basicdetails.auth;

import com.google.gson.Gson;
import com.philips.basicdetails.application.model.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class WhatsAppOTPService {


    public void sendOtp(OtpViewModel otpViewModel) throws UnsupportedEncodingException {

        WhatsAppOTPRequest whatsAppOTPRequest = new WhatsAppOTPRequest();
        whatsAppOTPRequest.setFrom("447860099299");
        whatsAppOTPRequest.setTo("+8801738579572");
        whatsAppOTPRequest.setMessageId("a8663a8a-e68d-480d-9387-5edf442078e4");
        whatsAppOTPRequest.setContent(new WhatsAppContentModel("infobip_welcome_message", new WhatsAppTemplateDataModel(new WhatsAppPlaceholderModel(Collections.singletonList("Test"))), "en"));

        List<WhatsAppOTPRequest> messages= new ArrayList<>();
        messages.add(whatsAppOTPRequest);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","App fe33dbc6c98cb446bda8e0afbb017663-e030a4ab-b2be-4b56-9373-4f5e1888ec9b");

            Map<String,Object> map = new HashMap<>();
            map.put("messages",messages);
            HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(map), headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.exchange("https://e51vx2.api.infobip.com/whatsapp/1/message/template", HttpMethod.POST, entity, String.class);
            System.out.printf(responseEntity.getBody());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
