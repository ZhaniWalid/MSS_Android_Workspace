package com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.AspNetUserBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MessageBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MoneyCurrencyFixerIoBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TheEconomistArticleBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TheEconomistGoogleNewsBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_BankOfPayementBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_BinCardBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_MerchantTypeTransactionBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_StatusCodeBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_TransactionStatusBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.RegisterBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TokenModel;
import com.google.firebase.messaging.RemoteMessage;

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

    @POST("api/User/PostNotifRejectedTranscFromFireBaseCloud")
    Call<MessageBindingModel> PostNotifRejectedTranscFromFireBaseCloud();

    @POST("https://fcm.googleapis.com/fcm/send")
    Call<RemoteMessage> fireBaseRassZebi();

    @POST("api/User/GetNotificationAboutRejectedTransactions")
    Call<MessageBindingModel>  GetNotificationOfRejectedTransactionsFirebase();

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

    @GET("api/User/ReportingStatusTransactions")
    Call<List<gw_TransactionStatusBindingModel>>  GetOnlyTransactionsStatus();

    @GET("api/User/ReportingMerchantTypeTransactions")
    Call<List<gw_MerchantTypeTransactionBindingModel>>  GetOnlyMerchantTypes();

    @GET("api/User/ReportingCardBinLabels")
    Call<List<gw_BinCardBindingModel>>  GetOnlyBinCardsLabels();

    @GET("api/User/ReportingBankOfPayement")
    Call<List<gw_BankOfPayementBindingModel>> GetOnlyBankNamesOfPayment();

    @GET(" api/User/GetRejectedStatusCodeWithDesc")
    Call<List<gw_StatusCodeBindingModel>> GetOnlyRejectedStatusCodeWithDesc();

    // my google news api json key
    String my_googleNewsApiKey = "3a91333937be47e8a4a608a899229e95";
    @GET("https://newsapi.org/v2/everything?sources=the-economist&apiKey="+my_googleNewsApiKey)
    Call<TheEconomistGoogleNewsBindingModel> GetEconomistNewsFromGoogleNews();

    // my fixer.io for currency money real time conversion json API KEY
    String my_fixerIoApiKey_MoneyConverter = "822370372dc2bb51d949809a8b1f9d4b";
    // Conversion from 1 Euro to other money currency => Unit = 1
    String unitBaseCurrencyToConverFrom = "EUR";
    String symbolsCurrencyToConverTo = "TND,MAD,DZD,LYD,QAR,KWD,SAR,USD,GBP,CAD,CHF,AUD,SEK";
    @GET("http://data.fixer.io/api/latest?access_key="+my_fixerIoApiKey_MoneyConverter+"&base="+unitBaseCurrencyToConverFrom+"&symbols="+symbolsCurrencyToConverTo)
    Call<MoneyCurrencyFixerIoBindingModel> GetMoneyCurrencyRealTimeFromFixerIO();

    // DELETE Parts
    @DELETE("api/User/DeleteUserMerchantByAdminMerchant/{idUserMerchantToDelete}")
    Call<String> DeleteUserMerchant(@Query("idUserMerchantToDelete") String idUserMerchantToDelete);
}
