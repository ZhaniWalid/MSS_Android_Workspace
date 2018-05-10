package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 25/04/2018.
 */

public class UserPatchRequestModel {


    private String userID;


    private String UserName;


    private String Email;


    private String FirstName;


    private String LastName;


    private String PhoneNumber;


    public UserPatchRequestModel() {
    }

    public UserPatchRequestModel(String userName, String email, String firstName, String lastName, String phoneNumber) {
        UserName = userName;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
    }

   /*public UserPatchRequestModel(String id, String firstName, String lastName, String email, String phoneNumber, String userName) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PhoneNumber = phoneNumber;
        UserName = userName;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    } */

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
