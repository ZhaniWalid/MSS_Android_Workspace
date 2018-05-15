package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 14/05/2018.
 */

public class AspNetUserBindingModel {

    @SerializedName("firstName")
    public String FirstName;
    @SerializedName("lastName")
    public String LastName;
    @SerializedName("email")
    public String Email;
    @SerializedName("phoneNumber")
    public String PhoneNumber;
    @SerializedName("userName")
    public String UserName;
    @SerializedName("organizationTypeName")
    public String OrganizationTypeName;

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getOrganizationTypeName() {
        return OrganizationTypeName;
    }

    public void setOrganizationTypeName(String organizationTypeName) {
        OrganizationTypeName = organizationTypeName;
    }
}
