package com.android_client.ms_solutions.mss.mss_androidapplication_client.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 14/05/2018.
 */

public class AspNetUserBindingModel {

    //
    @SerializedName("id")
    public String Id;
    //
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
    //
    @SerializedName("isBlocked")
    public int isBlocked; // = 1 => Bloquer User Merchant | = 0 => Débloquer User Merchant
    //
    //public String isBlockedToString; // = 1 => Bloquer User Merchant | = 0 => Débloquer User Merchant
    public String Password;
    public String ConfirmPassword;
    public int    Organization_Id;
    //

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public int getOrganization_Id() {
        return Organization_Id;
    }

    public void setOrganization_Id(int organization_Id) {
        Organization_Id = organization_Id;
    }

    //

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
