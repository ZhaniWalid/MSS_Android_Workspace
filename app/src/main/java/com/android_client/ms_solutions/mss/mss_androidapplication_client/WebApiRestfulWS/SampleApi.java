package com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.AspNetUserBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_ExtendedBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.RegisterBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TokenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SampleApi {

    /*
    @FormUrlEncoded
    @POST("Token")
    Call<TokenModel> Login1(@Field("grant_type") String grant_type, @Field("username") String email, @Field("password") String password);
    */

    // POST Parts
    @FormUrlEncoded
    @POST("api/User/Login")
    Call<TokenModel> Login(@Field("grant_type") String grant_type, @Field("username") String email, @Field("password") String password);

    @POST("api/User/Logout")
    Call<String> Logout(@Header("Authorize") String authorization);

    @POST("api/Account/Register")
    Call<String> Register(@Body RegisterBindingModel model);

    @FormUrlEncoded
    @POST("api/User/CreateUserMerchantByAdminMerchant")
    Call<String> CreateUserMerchant(@Field("FirstName") String FirstName,@Field("LastName") String LastName,@Field("Email") String Email,@Field("Password") String Password,@Field("ConfirmPassword") String ConfirmPassword,@Field("PhoneNumber") String PhoneNumber,@Field("UserName") String UserName,@Field("Organization_Id") int Organization_Id);

    @FormUrlEncoded
    @POST("api/User/ForgotPassword")
    Call<String> ForgotPassword(@Field("Email_ForgetPwd") String Email_ForgetPwd);

    @FormUrlEncoded
    @POST("api/User/VerificationCode")
    Call<String> VerificationCode(@Field("VerificationCode") String VerificationCode);

    // PATCH ( Updates ) Parts
    @FormUrlEncoded
    @PATCH("api/User/PatchProfileUser")
    Call<String> PartialUpdateProfileUser(@Field("UserName") String UserName, @Field("Email") String Email, @Field("FirstName") String FirstName, @Field("LastName") String LastName, @Field("PhoneNumber") String PhoneNumber);

    @FormUrlEncoded
    @PATCH("api/User/ChangePassword")
    Call<String> ChangePassword(@Field("OldPassword") String OldPassword,@Field("NewPassword") String NewPassword,@Field("ConfirmPassword") String ConfirmPassword);

    @PATCH("api/User/BlockUserMerchantByAdminMerchant/{idUserMerchantToBlock}")
    Call<String> BlockUserMerchant(@Query("idUserMerchantToBlock") String idUserMerchantToBlock);

    @PATCH("api/User/UnblockUserMerchantByAdminMerchant/{idUserMerchantToUnblock}")
    Call<String> UnblockUserMerchant(@Query("idUserMerchantToUnblock") String idUserMerchantToUnblock);

    @FormUrlEncoded
    @PATCH("api/User/ResetPassword")
    Call<String> ResetPassword(@Field("Email_ResetPwd") String Email_ResetPwd,@Field("Password_ResetPwd") String Password_ResetPwd,@Field("ConfirmPassword_ResetPwd") String ConfirmPassword_ResetPwd);

   // @GET("api/User/GetExtendedFiltrableTransactions")
   // Call<List<gw_trnsct_ExtendedBindingModel>> GetExtendedTransactionsData();

    // PATCH ( Updates ) Parts
    /*
    @PATCH("api/User/PatchProfileUser")
    Call<String> PatchUpdateProfileUser(@Body UserPatchRequestModel body);

    @PATCH("api/User/PatchProfileUser")
    Call<UserPatchRequestModel> PatchUser(@Body String body);

    @PATCH("api/User/PatchProfileUser")
    UserPatchRequestModel PatchUserHashMap (@Body HashMap<String,String> body);
    */

    // GET Parts
    @GET("api/User/Profile")
    Call<String[]> GetUserProfile(@Header("Authorize") String authorization);

    @GET("api/values")
    Call<String[]> GetValues(@Header("Authorize") String authorization);

    @GET("api/User/GetFiltrableTransactions")
    Call<List<gw_trnsct_GeneralBindingModel>> GetGeneralTransactionsData();

    @GET("api/User/GetUsersMerchants")
    Call<List<AspNetUserBindingModel>> GetOnlyAllUsersMerchants();

    // DELETE Parts
    @DELETE("api/User/DeleteUserMerchantByAdminMerchant/{idUserMerchantToDelete}")
    Call<String> DeleteUserMerchant(@Query("idUserMerchantToDelete") String idUserMerchantToDelete);
}
