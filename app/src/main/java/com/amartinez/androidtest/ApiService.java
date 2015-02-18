package com.amartinez.androidtest;

import com.amartinez.androidtest.model.Contact;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by amartinez on 17/02/15.
 */
public interface ApiService {

    @GET("/contacts")
    public void getContacts(Callback<List<Contact>> callback);
}
