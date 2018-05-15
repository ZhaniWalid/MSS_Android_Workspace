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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

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

    // PATCH ( Updates ) Parts
    @FormUrlEncoded
    @PATCH("api/User/PatchProfileUser")
    Call<String> PartialUpdateProfileUser(@Field("UserName") String UserName, @Field("Email") String Email, @Field("FirstName") String FirstName, @Field("LastName") String LastName, @Field("PhoneNumber") String PhoneNumber);

    @FormUrlEncoded
    @PATCH("api/User/ChangePassword")
    Call<String> ChangePassword(@Field("OldPassword") String OldPassword,@Field("NewPassword") String NewPassword,@Field("ConfirmPassword") String ConfirmPassword);

    @GET("api/User/GetFiltrableTransactions")
    Call<List<gw_trnsct_GeneralBindingModel>> GetGeneralTransactionsData();

    @GET("api/User/GetUsersMerchants")
    Call<List<AspNetUserBindingModel>> GetOnlyAllUsersMerchants();

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

}
