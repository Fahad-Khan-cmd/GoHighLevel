package com.evantagesoft.hubspot_wrapper.dto.response.createcontactwebhookresponse;

import java.util.Date;

public class Properties {
    private String company;
    private String contact_business_type;
    private String country;
    private Date createdate;
    private String email;
    private String firstname;
    private String hs_object_id;
    private Date lastmodifieddate;
    private String lastname;
    private String message;
    private String phone;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact_business_type() {
        return contact_business_type;
    }

    public void setContact_business_type(String contact_business_type) {
        this.contact_business_type = contact_business_type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getHs_object_id() {
        return hs_object_id;
    }

    public void setHs_object_id(String hs_object_id) {
        this.hs_object_id = hs_object_id;
    }

    public Date getLastmodifieddate() {
        return lastmodifieddate;
    }

    public void setLastmodifieddate(Date lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
