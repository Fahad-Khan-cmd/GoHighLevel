package com.evantagesoft.hubspot_wrapper.service;

import com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest.Claims;
import com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest.CreateContactWebHookRequest;
import com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest.IDSSelfRegisterRequest;
import com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest.User;
import com.evantagesoft.hubspot_wrapper.dto.response.createcontactwebhookresponse.GetAccountByIdResponse;
import com.evantagesoft.hubspot_wrapper.response.ErrorResponse;
import com.evantagesoft.hubspot_wrapper.response.Response;
import com.evantagesoft.hubspot_wrapper.util.GeneratePassword;
import com.evantagesoft.hubspot_wrapper.util.Marshalling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

@Service
@PropertySource(value = "classpath:application.properties")
public class ContactWebHookService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Environment env;

    public Response createContactWebHook(ArrayList <CreateContactWebHookRequest> createContactWebHookRequestArrayList) throws IOException {
        CreateContactWebHookRequest createContactWebHookRequest=createContactWebHookRequestArrayList.get(0);
        Response response = new Response();


        String[] message=new String[2];
        try {
            System.out.printf("CRequest Payload : " + createContactWebHookRequest);
            URI uri = new URI(Objects.requireNonNull(env.getProperty("CreateContactWebHookURL") + createContactWebHookRequest.getObjectId() + "?properties=email%2C%20phone%2C%20company%2C%20country%2C%20firstname%2C%20lastname%2C%20message%2C%20contact_business_type%2C%20archived=false"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", Objects.requireNonNull(env.getProperty("HubSpotAuthorizationToken")));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode().value() >= 200 && responseEntity.getStatusCode().value() < 300) {
                GetAccountByIdResponse getAccountByIdResponse = Marshalling.jsonStringToObjectMapper(responseEntity.getBody().toString(),
                        GetAccountByIdResponse.class);
                IDSSelfRegisterRequest idsSelfRegisterRequest = new IDSSelfRegisterRequest();
                User user = new User();

                user.setUsername(getAccountByIdResponse.getProperties().getEmail());
                user.setPassword(GeneratePassword.generatePassword(8));
                user.setRealm("PRIMARY");

                ArrayList<Claims> claimsList = new ArrayList<>();
                Claims claims = new Claims();
                claims.setUri("http://wso2.org/claims/givenname");
                claims.setValue(getAccountByIdResponse.getProperties().getFirstname() != null ? getAccountByIdResponse.getProperties().getFirstname() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/lastname");
                claims.setValue(getAccountByIdResponse.getProperties().getLastname() != null ? getAccountByIdResponse.getProperties().getLastname() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/companyname");
                claims.setValue(getAccountByIdResponse.getProperties().getCompany() != null ? getAccountByIdResponse.getProperties().getCompany() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/emailaddress");
                claims.setValue(getAccountByIdResponse.getProperties().getEmail() != null ? getAccountByIdResponse.getProperties().getEmail() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/phoneNumbers");
                claims.setValue(getAccountByIdResponse.getProperties().getPhone() != null ? getAccountByIdResponse.getProperties().getPhone() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/country");
                claims.setValue(getAccountByIdResponse.getProperties().getCountry() != null ? getAccountByIdResponse.getProperties().getCountry() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/message");
                claims.setValue(getAccountByIdResponse.getProperties().getMessage() != null ? getAccountByIdResponse.getProperties().getMessage() : "");
                claimsList.add(claims);

                claims = new Claims();
                claims.setUri("http://wso2.org/claims/businesstype");
                claims.setValue(getAccountByIdResponse.getProperties().getContact_business_type() != null ? getAccountByIdResponse.getProperties().getContact_business_type() : "");
                claimsList.add(claims);

                claimsList.add(claims);
                user.setClaims(claimsList);
                idsSelfRegisterRequest.setUser(user);
                String body = Marshalling.objectToJsonStringMapper(idsSelfRegisterRequest);
                uri = new URI(Objects.requireNonNull(Objects.requireNonNull(env.getProperty("IDSSelfRegisterURL"))));
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", env.getProperty("IDSAuthorizationToken"));
                entity = new HttpEntity<>(body, headers);
                try{
                    responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);
                    if (responseEntity.getStatusCode().value() >= 200 && responseEntity.getStatusCode().value() < 300) {
                        message=String.valueOf(responseEntity.getStatusCode()).split(" ");
                        response.setCode(Integer.parseInt(message[0]));
                        response.setMessage(message[1]);
                    } else {
                        message=String.valueOf(responseEntity.getStatusCode()).split(" ");
                        response.setCode(Integer.parseInt(message[0]));
                        response.setMessage(message[1]);
                        response.setData(responseEntity.getBody());
                    }
                } catch (HttpClientErrorException | HttpServerErrorException ex) {
                    HttpStatus statusCode = ex.getStatusCode();
                    String responseBody = ex.getResponseBodyAsString();
                    ErrorResponse errorResponse= Marshalling.jsonStringToObjectMapper(responseBody, ErrorResponse.class);
                    response.setCode(statusCode.value());
                    response.setMessage(errorResponse.getDescription());
                    ex.printStackTrace();
                    return response;
                }
            }
            else {
                message=String.valueOf(responseEntity.getStatusCode()).split(" ");
                response.setCode(Integer.parseInt(message[0]));
                response.setMessage(message[1]);
                response.setData(responseEntity.getBody());
            }
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            HttpStatus statusCode = ex.getStatusCode();
            response.setCode(statusCode.value());
            response.setMessage(String.valueOf(statusCode));
            ex.printStackTrace();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
    }
}
