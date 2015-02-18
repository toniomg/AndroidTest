package com.amartinez.androidtest.model;

/**
 * Created by amartinez on 17/02/15.
 */
public class Contact {

    private String first_name;
    private String surname;
    private String address;
    private String phone_number;
    private String email;
    private Long id;
    private String createdAt;
    private String updatedAt;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(final String pFirst_name) {
        first_name = pFirst_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String pSurname) {
        surname = pSurname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String pAddress) {
        address = pAddress;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(final String pPhone_number) {
        phone_number = pPhone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String pEmail) {
        email = pEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long pId) {
        id = pId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String pCreatedAt) {
        createdAt = pCreatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final String pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }
}
