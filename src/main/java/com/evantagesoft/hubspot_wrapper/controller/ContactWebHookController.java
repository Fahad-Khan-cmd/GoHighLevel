package com.evantagesoft.hubspot_wrapper.controller;

import com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest.CreateContactWebHookRequest;
import com.evantagesoft.hubspot_wrapper.response.Response;
import com.evantagesoft.hubspot_wrapper.service.ContactWebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping({"/HubSpot"})
public class ContactWebHookController {

    @Autowired
    ContactWebHookService contactWebHookService;

/*    @RequestMapping(value = {"/CreateContactWebHook"}, method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<?> CreateContactWebHook(@RequestBody CreateContactWebHookRequest createContactWebHookRequest) throws IOException {
        return new ResponseEntity(contactWebHookService.createContactWebHook(createContactWebHookRequest), HttpStatus.OK);
    }*/


    @PostMapping("/CreateContactWebHook")
    public ResponseEntity<?> processOrder(@RequestBody ArrayList <CreateContactWebHookRequest> createContactWebHookRequestArrayList){
        try {
            Response response=new Response();
            response=contactWebHookService.createContactWebHook(createContactWebHookRequestArrayList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }


    }
}
