package com.android_client.ms_solutions.mss.mss_androidapplication_client;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Classes.AuthenticationResult;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.DialogBoxes.AlertMessageBox;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.RegisterBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TokenModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils.StringUtil;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import butterknife.BindView;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    SampleApi api = SampleApiFactory.getInstance();
    TokenModel tokenModel = null; // n7otha lahné mch louta bsh ntasti beha marra jeya si login = "AdminMonoprix" thezni el activity taa Admin
    // si non thezni el activity commune ta3 'caissier+marchant+magasin'
    // w nchouf 7al zeda el fazet 'organizationId = 42 ' 5assa ken bl les 'Marchants'

    //Button  btn_login;
    @BindView(R.id.editTextEmail)
    EditText _email_UsrName_editTxt;
    @BindView(R.id.editTextPassword)
    EditText _pwd_editTxt;

    String email, password;
    // used in changing password to verify typed old password with password in db
    public static String getPassword_StaticInLogin = "";

    // Fields for Forgot Password ? => Reset Password

    // Step 1
    private EditText editTextEmailForPasswordReset;
    private String getEmailRecoveryPassword = " ", emailRecoveryPasswordResult = " ";
    private Button btnEmailRecoveryPassword_Next;

    // Step 2
    private EditText editTextVerifCodeForPasswordReset;
    private String getVerifCodeRecoveryPassword = " ", verifCodeRecoveryPasswordResult = " ";
    private Button btnVerifCodeRecoveryPassword_Next, btnVerifCodeRecoveryPassword_Back;

    // Step 3
    private EditText editTextEmailForPasswordReset_FinalStep, editTextNewPasswordReset_FinalStep, editTextConfirmPasswordReset_FinalStep;
    private Button btnResetPassword_Confirmation, btnResetPassword_Back;
    //private FloatingActionButton fab_activeEmailPasswordReset_FinalStep;
    private String getEmailRecvPassword_FinalStep = " ",getNewPasswordRecvPassword_FinalStep = " ",getConfirmNewPasswordRecvPassword_FinalStep = " ";
    private String resetPasswordRecoveryPasswordResult = " ";
    private String strongPassword = "Strong Password",mediumPassword = "Medium Password",weakPassword = "Weak Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intent From Home Activity to Login Activity
        // Intent intent = getIntent();
        //setContentView(R.layout.activity_login);

        /* we do like this or like the new other method with the annotation :
        @Click(R.id.buttonLogIn)
        void Login(){ }

        btn_login = (Button) findViewById(R.id.buttonLogin);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                 Login(); // here in this case so we should delete the annotation '@Click(R.id.buttonLogIn)' from the method 'Login()'
            }
        });
        */

        /*
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#00b8d4'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @ViewById
    EditText editTextEmail;

    @ViewById
    EditText editTextPassword;

    // => cette declaration avec l'exmple de l'annotation qui suit :
    // @ViewById
    // EditText editTextEmail;
    // => est la meme declaration que :
    // EditText edtTxtEmail = (EditText) findViewById(R.id.editTextEmail);
    // => mais ellle doit etre implémenté au seins de la méthode ' void onCreate() ' , pas l'inverse

    @AfterViews
    protected void afterViews() {
        //Long currentTimeStampe = (System.currentTimeMillis()/1000);
        //String email = currentTimeStampe.toString()+ "@mail.com";
        String email = "AdminMonoprix";
        String passwd = "Azerty12345+";
        editTextEmail.setText(email);
        editTextPassword.setText(passwd);
        //editTextPassword.setText("1@aA1@aA1@aA");
    }

    /*

    // Register() Method
    // i don't need ' Register'  for the Moment

    @Click(R.id.buttonRegister)
    void Register() {
        String email = editTxtEmail.getText().toString();
        String password = editTxtPwd.getText().toString();
        if (!StringUtil.isNullOrWhitespace(email) && !StringUtil.isNullOrWhitespace(email)) {
            if (StringUtil.isUserName(email)) {
                if (StringUtil.isMatch("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*(_|[^\\w])).{6,}$", password)) {
                    RegisterBindingModel model = new RegisterBindingModel(email, password, password);
                    new RegisterAsyncTask().execute(model);
                } else
                    AlertMessageBox.Show(MainActivity.this, "Error", "Passwords must have at least one non letter and digit character. Passwords must have at least one lowercase ('a'-'z'). Passwords must have at least one uppercase ('A'-'Z').", AlertMessageBox.AlertMessageBoxIcon.Error);

            } else
                AlertMessageBox.Show(MainActivity.this, "Error", "A valid email address is required", AlertMessageBox.AlertMessageBoxIcon.Error);
        } else
            AlertMessageBox.Show(MainActivity.this, "Error", "Email and password are required", AlertMessageBox.AlertMessageBoxIcon.Error);
    }

   // fin Register() Method

   */

    // ForgetPassword() method
    @Click(R.id.buttonForgotPassword)
    public void ForgetPassword() {
        EmailRecoveryPasswordResetUI();
    }

    // Step 1
    private void EmailRecoveryPasswordResetUI() {

        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
        View subView = inflater.inflate(R.layout.reset_password_email_ui, null);

        editTextEmailForPasswordReset = subView.findViewById(R.id.editTxtEmailPwdRecovery);
        btnEmailRecoveryPassword_Next = subView.findViewById(R.id.btn_emailPwdRcvry_toStep2);

        btnEmailRecoveryPassword_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailRecoveryValidation();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Step 1 : Email For Password Recovery");
        builder.setIcon(LoginActivity.this.getResources().getDrawable(R.drawable.ic_mail_black_24dp));
        builder.setView(subView);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                LoginActivity_.intent(LoginActivity.this).start();
                Toast.makeText(LoginActivity.this, "Password Recovery Step 1 with Email : Canceled", Toast.LENGTH_LONG).show();
            }
        });
        builder.create();
        builder.show();
    }

    private boolean isEmailAddressValid(String email) {
        boolean isValid;

        if (!StringUtil.isEmailAddress(email)) {
            String msgEmailError = "Email Invalid,Should be in this form : ' example@domain.com ' ";
            editTextEmailForPasswordReset.setError(msgEmailError);
            isValid = false;
        } else {
            editTextEmailForPasswordReset.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private void getEmailFieldValue() {
        getEmailRecoveryPassword = editTextEmailForPasswordReset.getText().toString();
    }

    private void emptyEmailNotAllowed(){
        editTextEmailForPasswordReset.setError("Email Required");
    }

    private void whenEmailIsNotExist(){
        editTextEmailForPasswordReset.setError("User Not Found : Email does not exist in our data base ");
    }

    private void EmailRecoveryValidation() {

        getEmailFieldValue();

      if(!StringUtil.isNullOrWhitespace(getEmailRecoveryPassword)){
          if (!isEmailAddressValid(getEmailRecoveryPassword)) {
              Toast.makeText(LoginActivity.this, "Email Address Not Valid,Please Verify your input ", Toast.LENGTH_LONG).show();
          } else {
              new EmailVerificationPasswordResetTask().execute();
              //Toast.makeText(LoginActivity.this,"Password Recovery Step 1 with Email : Accepted",Toast.LENGTH_LONG).show();
          }
      }else{
          emptyEmailNotAllowed();
          Toast.makeText(LoginActivity.this, "Email Address Not Valid,Please Verify your input ", Toast.LENGTH_LONG).show();
      }

    }

    //Step 2
    private void VerificationCodePasswordResetUI() {

        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
        View subView = inflater.inflate(R.layout.reset_password_verification_code_ui, null);

        editTextVerifCodeForPasswordReset = subView.findViewById(R.id.editTxtVerifCodePwdRecovery);
        btnVerifCodeRecoveryPassword_Next = subView.findViewById(R.id.btn_VerifCodePwdRcvry_toStep3);
        btnVerifCodeRecoveryPassword_Back = subView.findViewById(R.id.btn_VerifCodePwdRcvry_toStep1);

        btnVerifCodeRecoveryPassword_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailRecoveryPasswordResetUI();
            }
        });

        btnVerifCodeRecoveryPassword_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifCodeRecoveryVerification();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Step 2 : Verification Code For Password Recovery");
        builder.setIcon(LoginActivity.this.getResources().getDrawable(R.drawable.ic_euro_symbol_black_24dp));
        builder.setView(subView);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                LoginActivity_.intent(LoginActivity.this).start();
                Toast.makeText(LoginActivity.this, "Password Recovery Step 2 with Verification Code : Canceled", Toast.LENGTH_LONG).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void VerifCodeRecoveryVerification() {

        getVerifCodeFieldValue();

        if(!StringUtil.isNullOrWhitespace(getVerifCodeRecoveryPassword)){
            new CodeVerificationPasswordResetTask().execute();
        }else{
            emptyVerifCodeNotAllowed();
            Toast.makeText(LoginActivity.this, "Verification Code Not Valid,Please Verify your input ", Toast.LENGTH_LONG).show();
        }
    }

    private void getVerifCodeFieldValue() {
        getVerifCodeRecoveryPassword = editTextVerifCodeForPasswordReset.getText().toString();
    }

    private void emptyVerifCodeNotAllowed(){
        editTextVerifCodeForPasswordReset.setError("Verification Code is Required");
    }

    private void whenVerifCodeIsFalse() {
        String msgError = "Invalid Verification Code,please verify your last Verification Code received in your email !";
        editTextVerifCodeForPasswordReset.setError(msgError);
    }

    // Step 3
    private void RecoveryPasswordResetFinalStepUI(){

        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
        View subView = inflater.inflate(R.layout.reset_password_final_step_ui, null);

        editTextEmailForPasswordReset_FinalStep = subView.findViewById(R.id.editTxtEmailPwdRecovery_FinalStep);
        editTextNewPasswordReset_FinalStep = subView.findViewById(R.id.editTxtNewPwdRecovery_FinalStep);
        editTextConfirmPasswordReset_FinalStep = subView.findViewById(R.id.editTxtConfirmNewPwdRecovery_FinalStep);

        btnResetPassword_Back = subView.findViewById(R.id.btn_BackPwdRcvry_toStep2);
        btnResetPassword_Confirmation = subView.findViewById(R.id.btn_ConfirmPwdRcvry_finalStep);

        /*

        fab_activeEmailPasswordReset_FinalStep = subView.findViewById(R.id.fab_activeEmailPwdRcvry_finalStep);

        fab_activeEmailPasswordReset_FinalStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEmailInFinalStep();
            }
        });

        */

        SetEmailPasswordRecovery_afterView();

        btnResetPassword_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerificationCodePasswordResetUI();
            }
        });

        editTextNewPasswordReset_FinalStep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    isNewPasswordStrong();
                }
            }
        });

        editTextConfirmPasswordReset_FinalStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConfirmNewPasswordStrong();
            }
        });

        btnResetPassword_Confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Step 3 : Reset Password Recovery");
        builder.setIcon(LoginActivity.this.getResources().getDrawable(R.drawable.ic_lock_black_24dp));
        builder.setView(subView);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                LoginActivity_.intent(LoginActivity.this).start();
                Toast.makeText(LoginActivity.this, "Password Recovery Step 3 (final step) : Canceled", Toast.LENGTH_LONG).show();
            }
        });
        builder.create();
        builder.show();

    }

    /*
    private void enableEmailInFinalStep(){
      editTextEmailForPasswordReset_FinalStep.setEnabled(true);
    }
    */

    private void SetEmailPasswordRecovery_afterView(){

        // Set The email field in final step disabled and put in it the value of email accepted in Step1
        editTextEmailForPasswordReset_FinalStep.setText(getEmailRecoveryPassword);
        editTextEmailForPasswordReset_FinalStep.setTextColor(getResources().getColor(R.color.black));
    }

    private void getResetPasswordValues(){

        // Get values of email , new password and confirmation of new password
        getEmailRecvPassword_FinalStep = editTextEmailForPasswordReset_FinalStep.getText().toString();
        getNewPasswordRecvPassword_FinalStep = editTextNewPasswordReset_FinalStep.getText().toString();
        getConfirmNewPasswordRecvPassword_FinalStep = editTextConfirmPasswordReset_FinalStep.getText().toString();
    }

    private void emptyRestPasswordsFieldsNotAllowed(){

        //editTextEmailForPasswordReset_FinalStep.setError("Email is Required");
        editTextNewPasswordReset_FinalStep.setError("New Password is Required");
        editTextConfirmPasswordReset_FinalStep.setError("Confirmation Of New Password is Required");
    }

    private boolean validateNewPassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            editTextNewPasswordReset_FinalStep.setError("New Password should be between 4 and 15 alphanumeric characters : must contain letters a-z A-Z and at least one digit 0-9 ");
            isValid = false;
        } else {
            editTextNewPasswordReset_FinalStep.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean validateConfirmNewPassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            editTextConfirmPasswordReset_FinalStep.setError("Confirmation Of New Password should be between 4 and 15 alphanumeric characters : must contain letters a-z A-Z and at least one digit 0-9");
            isValid = false;
        } else {
            editTextConfirmPasswordReset_FinalStep.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean isTwoPasswordsEquals(String newPassword,String confirmNewPassword){
        boolean isValid;

        if (!StringUtil.isPasswordsConfirmedEquals(newPassword,confirmNewPassword)){
            isValid = false;
        }else {
            isValid = true;
        }

        return isValid;
    }

    private void isNewPasswordStrong (){

        if (StringUtil.passwordStrength(getNewPasswordRecvPassword_FinalStep).equals("StrongPassword")){
            editTextNewPasswordReset_FinalStep.setError(strongPassword);
            editTextNewPasswordReset_FinalStep.setBackgroundColor(getResources().getColor(R.color.green));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#7FFF00"));
        }else if (StringUtil.passwordStrength(getNewPasswordRecvPassword_FinalStep).equals("MediumPassword")) {
            editTextNewPasswordReset_FinalStep.setError(mediumPassword);
            editTextNewPasswordReset_FinalStep.setBackgroundColor(getResources().getColor(R.color.orange));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#FFA500"));
        }else{
            editTextNewPasswordReset_FinalStep.setError(weakPassword);
            editTextNewPasswordReset_FinalStep.setBackgroundColor(getResources().getColor(R.color.red));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    private void isConfirmNewPasswordStrong (){

        if (StringUtil.passwordStrength(getConfirmNewPasswordRecvPassword_FinalStep).equals("StrongPassword")){
            editTextConfirmPasswordReset_FinalStep.setError(strongPassword);
            editTextConfirmPasswordReset_FinalStep.setBackgroundColor(getResources().getColor(R.color.green));
        }else if (StringUtil.passwordStrength(getConfirmNewPasswordRecvPassword_FinalStep).equals("MediumPassword")) {
            editTextConfirmPasswordReset_FinalStep.setError(mediumPassword);
            editTextConfirmPasswordReset_FinalStep.setBackgroundColor(getResources().getColor(R.color.orange));
        }else{
            editTextConfirmPasswordReset_FinalStep.setError(weakPassword);
            editTextConfirmPasswordReset_FinalStep.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    private void failedResetPassword(){

        String msgNewPwdError =
                "1. Password must have at least one non letter or digit character \r\n" +
                        "2. Password must have at least one uppercase ('A'-'Z').";
        editTextNewPasswordReset_FinalStep.setError(msgNewPwdError);

        String msgConfirmNewPwdError =
                "1. Password must have at least one non letter or digit character \r\n" +
                        "2. Password must have at least one uppercase ('A'-'Z').";
        editTextConfirmPasswordReset_FinalStep.setError(msgConfirmNewPwdError);
    }

    private void ResetPassword(){

        getResetPasswordValues();

        if (   !StringUtil.isNullOrWhitespace(getNewPasswordRecvPassword_FinalStep)
            || !StringUtil.isNullOrWhitespace(getConfirmNewPasswordRecvPassword_FinalStep) ){

            if(!validateNewPassword(getNewPasswordRecvPassword_FinalStep) || !validateConfirmNewPassword(getConfirmNewPasswordRecvPassword_FinalStep)){
                Toast.makeText(LoginActivity.this,"One or More Fields are incorrect,Please try again and make sure of what you type",Toast.LENGTH_LONG).show();
            }else if(!isTwoPasswordsEquals(getNewPasswordRecvPassword_FinalStep,getConfirmNewPasswordRecvPassword_FinalStep)){
                String msgConfirmPwdFalse = " ' New Password '  &  ' Confirmation Of New Password ' did not match , please Retype Again with same values";
                Toast.makeText(LoginActivity.this,msgConfirmPwdFalse,Toast.LENGTH_LONG).show();
            }else{
                new ResetPasswordTask().execute();
            }
        }else{
            emptyRestPasswordsFieldsNotAllowed();
            Toast.makeText(LoginActivity.this,"All Fields Are Required , You Can't left empty fields",Toast.LENGTH_LONG).show();
        }
    }

    // Login() Method
    @Click(R.id.buttonLogin)
    public void Login() {

        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if (!StringUtil.isNullOrWhitespace(email) && !StringUtil.isNullOrWhitespace(password)) {

            // Start of : another addition from another tuto for the waiting process
            if (!validateUserName(email) && !validatePassword(password)) {
                onLoginFailed();
                return;
            }else {
                // End of : another addition from another tuto for the waiting process
                RegisterBindingModel model = new RegisterBindingModel(email, password, password);
                new LoginAsyncTask().execute(model);
            }
        } else {
            AlertMessageBox.Show(LoginActivity.this, "Error", "Email and/or password are required", AlertMessageBox.alertMessageBoxIcon.Error);
        }
    }
    // fin Login() Method

    // login switcher

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
        //btn_login.setEnabled(true);
    }

    public void onLoginSuccess_NeverGoBackToLogin() {
        //btn_login.setEnabled(true);
        finish(); // This will close the activity and won't be available to navigate back to without restarting the app
                 // or specifically calling the activity later in your code.
        //Toast.makeText(LoginActivity.this, "You Can't Go Back to Login Screen,You Should Logout First", Toast.LENGTH_LONG).show();
       // Snackbar.make(null, "You Can't Go Back to Login Screen,You Should Logout First", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public boolean validateUserName(String input) {
        boolean isValid;

        // Validates Email/Username in Login
        if (!StringUtil.isUserName(input)) {
            editTextEmail.setError("Enter a valid Email address Or Username : at least 3 characters");
            isValid = false;
        } else {
            editTextEmail.setError(null);
            isValid = true;
        }

        return isValid;
    }

    public boolean validatePassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            editTextPassword.setError("Password should be between 4 and 15 alphanumeric characters");
            isValid = false;
        } else {
            editTextPassword.setError(null);
            isValid = true;
        }

        return isValid;
    }

    public void AuthenticatingProgressDialog(){
      /*  new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        //onLoginFailed();
                        progressDialogLogin.dismiss();
                    }
                }, 5000); */
    }

    @Override
    public void onBackPressed() {
        // disable going back to the HomeActivity
        //moveTaskToBack(true);
        //super.onBackPressed(); // should be commented this line in order to disable back press to 'HomeActivity'
        Toast.makeText(LoginActivity.this, "You Can't Go Back to Home Screen,You Should Login First", Toast.LENGTH_LONG).show();
    }

    // fin login switcher

    // Login AsyncTask Part
    class LoginAsyncTask extends AsyncTask<RegisterBindingModel, String, AuthenticationResult> {

        @Override
        protected AuthenticationResult doInBackground(RegisterBindingModel... models) {
            //TokenModel tokenModel = null;
            try{
                tokenModel = api.Login("password", models[0].getEmail(),models[0].getPassword()).execute().body();
                // used in changing password to verify typed old password with password in db
                getPassword_StaticInLogin = models[0].getPassword();

                if(tokenModel != null && tokenModel.AccessToken != null){
                    return new AuthenticationResult(true, tokenModel.AccessToken, null);
                }else{
                    return new AuthenticationResult(false, null, "Login failed invalid_grant :\r Username or Password are invalid credentials"); // \r\n
                }
            }catch (IOException e){
                e.printStackTrace();
                return new AuthenticationResult(false, null, "Login failed :\r Network Error,Please Try Again" ); // + e.getMessage() // \r\n
            }
        }

        @Override
        protected void onPostExecute(final AuthenticationResult result) {

            final ProgressDialog progressDialogLogin = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);

            if(!result.isSuccessful()){
                AlertMessageBox.Show(LoginActivity.this, "Error", result.getError(), AlertMessageBox.alertMessageBoxIcon.Error);
            }else{

                progressDialogLogin.setIndeterminate(true);
                progressDialogLogin.setTitle("Authenticating...");
                progressDialogLogin.setMessage("Please Wait While Authenticating...");
                progressDialogLogin.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                //onLoginSuccess_NeverGoBackToLogin();
                                //onLoginFailed();
                                progressDialogLogin.dismiss();
                                //ValueActivity_.intent(LoginActivity.this).accessToken(result.getAccessToken()).start();
                                //MainActivity_.intent(LoginActivity.this).accessToken(result.getAccessToken()).start();
                         if (tokenModel.organizationID == 42) {

                             if (tokenModel.Username.equals("AdminMonoprix")) {

                                 Intent intent = new Intent(LoginActivity.this, HomeAdminMarchantActivity.class);
                                 intent.putExtra("AccessToken", result.getAccessToken());
                                 //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(intent);
                                 Toast.makeText(getApplicationContext(), "Going To ' Admin Merchant ' Side", Toast.LENGTH_LONG).show();
                                 //onLoginSuccess_NeverGoBackToLogin(); // should be called after 'startActivity(intent);'

                             } else {

                                 if (tokenModel.isBlocked == 1){ // = 1 => Bloquer User Merchant

                                     //String msgTryAgain = "Access Denied : \r You Have Been Blocked By The Admin,Contact : walid.zhani@esprit.tn !";
                                     //Toast.makeText(getApplicationContext(),msgTryAgain,Toast.LENGTH_LONG).show();

                                       new AlertDialog.Builder(LoginActivity.this)
                                                      .setTitle("Acces Denied : Blocked User")
                                                      .setMessage("You Have Been Blocked By The Admin Merchant,Contact : walid.zhani@esprit.tn")
                                                      .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.ic_block_black_24dp))
                                                      .setNeutralButton("Ok , i got it", new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(DialogInterface dialogInterface, int i) {
                                                              String msgTryAgain = "When your administrator give You Access,please try again and reconnect !";
                                                              Toast.makeText(getApplicationContext(),msgTryAgain,Toast.LENGTH_LONG).show();
                                                          }
                                                      })
                                                      .create()
                                                      .show();

                                 }else { // 0 => Débloquer User Merchant
                                     Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                     intent.putExtra("AccessToken", result.getAccessToken());
                                     //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                     startActivity(intent);
                                     Toast.makeText(getApplicationContext(), "Going To Normal ' Users Merchants ' Side", Toast.LENGTH_LONG).show();

                                     //String msg = "Invalid user token! Please login again! not found!  => Only 'Marchants' can access here , please try again";
                                     //Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
                                 }

                             }
                         } else {
                             Toast.makeText(getApplicationContext(), "Acces Denied : Only ' Merchants ' Can Access here , Please Try Again ", Toast.LENGTH_LONG).show();
                         }

                            }
                        }, 3500);

             /*   AlertMessageBox.Show(LoginActivity.this, "Successful", "Login successfully Sir : "+email+" . You're going to be forwarded to protected resource. ", AlertMessageBox.alertMessageBoxIcon.Info, new AlertMessageBoxOkClickCallback() {
                    @Override
                    public void run() {
                        AuthenticatingProgressDialog();
                        ValueActivity_.intent(LoginActivity.this).accessToken(result.getAccessToken()).start();
                        // 1 ) this line : ValueActivity_.intent ....
                        // va faire une erreur si on écrit : ValueActivity.intent
                        // et 2 ) puisque les 2 classes : ' MainActivity ' et ' ValueActivity ' sont Annotés par exmple : ' @EActivity(R.layout.activity_main)'
                        // 3 ) donc on doit les changer aussi dans 'AndroidManifest.xml' comme ça :
                        // <activity android:name=".ValueActivity_"/> ( avec tiret 8 : true ) pour quant peut executer les AnnotationProcessor between UI & Activities
                        // et pas comme sa <activity android:name=".ValueActivity"/> ( sans tiret 8 : false )
                        // et si problème du ".intent" persiste just faire les changements dans 1 ), 2 ) et 3 )
                        // et finalement : 4 ) => build project et les erreurs se disparaissent
                    }
                });*/
            }

            super.onPostExecute(result);
        }
    }
    // fin Login AsyncTask Part

    // Class EmailVerificationPasswordResetTask
    class EmailVerificationPasswordResetTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            params = new String[1];

            params[0] = getEmailRecoveryPassword;

            try {
                emailRecoveryPasswordResult = api.ForgotPassword(params[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return emailRecoveryPasswordResult;
        }

        @Override
        protected void onPostExecute(String string) {


         switch (emailRecoveryPasswordResult){

           case "Link_ResetPassword_HasBeenSent_ToTheUser":
             AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
             builder.setTitle("Email Confirmed !");
             String msg = "Your Email is confirmed,an email has been sent to you with your ' Verification Code' , use it in the next step";
             builder.setMessage(msg);
             builder.setIcon(LoginActivity.this.getResources().getDrawable(R.drawable.ic_info_black_24dp));
             builder.setNeutralButton("Ok,i got it : to the next step", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     VerificationCodePasswordResetUI();
                     Toast.makeText(LoginActivity.this,"Password Recovery Step 1 with Email : Accepted ,View your email",Toast.LENGTH_LONG).show();
                 }
             });
             builder.create();
             builder.show();
           break;

           case "Error404_UserNotFound_ByEmailAsync":
             whenEmailIsNotExist();
             Toast.makeText(LoginActivity.this,"User Not Found : Email not found in our data Base,please verify your email",Toast.LENGTH_LONG).show();
           break;
         }

            super.onPostExecute(string);
        }
    }

    // CodeVerificationPasswordResetTask
    class CodeVerificationPasswordResetTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            params = new String[1];

            params[0] = getVerifCodeRecoveryPassword;

            try {
                verifCodeRecoveryPasswordResult = api.VerificationCode(params[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return verifCodeRecoveryPasswordResult;
        }

        @Override
        protected void onPostExecute(String string) {

            if(verifCodeRecoveryPasswordResult.equals("VerificationCode_Successfully_Verified")){

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Verification Code Confirmed !");
                String msg = "Your Verification Code is confirmed,this let you to go to the final step";
                builder.setMessage(msg);
                builder.setIcon(LoginActivity.this.getResources().getDrawable(R.drawable.ic_info_black_24dp));
                builder.setNeutralButton("Ok,i got it : to the final step", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RecoveryPasswordResetFinalStepUI();
                        Toast.makeText(LoginActivity.this,"Password Recovery Step 2 with Verification Code : Accepted,we are going to the final step",Toast.LENGTH_LONG).show();
                    }
                });
                builder.create();
                builder.show();

            }else {
                whenVerifCodeIsFalse();
                Toast.makeText(LoginActivity.this,"Invalid Verification Code",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(string);
        }
    }

    // ResetPasswordTask
    class ResetPasswordTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            params = new String[4];

            for (int i=0;i<params.length;i++){

                params[0] = getEmailRecvPassword_FinalStep;
                params[1] = getNewPasswordRecvPassword_FinalStep;
                params[2] = getConfirmNewPasswordRecvPassword_FinalStep;
            }

            try {
                resetPasswordRecoveryPasswordResult = api.ResetPassword(params[0],params[1],params[2]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resetPasswordRecoveryPasswordResult;
        }

        @Override
        protected void onPostExecute(String string) {

            if (resetPasswordRecoveryPasswordResult.equals("Congratulations_ResetPassword_HaveBeen_Succeedded => View your Email !")){

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Password Reset Succeeded !");
                String msg = "Your Password is Resetted Successfully,please check your email for new changes!";
                builder.setMessage(msg);
                builder.setIcon(LoginActivity.this.getResources().getDrawable(R.drawable.ic_info_black_24dp));
                builder.setNeutralButton("Ok,i got it : close this window", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     LoginActivity_.intent(LoginActivity.this).start();
                     Toast.makeText(LoginActivity.this,"Password Recovery Step 3 (final) : Password has been Resetted Successfully,please check your email for new changes!",Toast.LENGTH_LONG).show();
                    }
                });
                builder.create();
                builder.show();
            }else{
                failedResetPassword();
                String msgFailedResult = "Update password failed for some reasons like : \r\n" +
                        "1. Passwords must have at least one non letter or digit character \r\n" +
                        "2. Passwords must have at least one uppercase ('A'-'Z') \r\n" +
                        "3. Email Problem : Same Email used with other user ( Absurde )";
                Toast.makeText(LoginActivity.this,msgFailedResult,Toast.LENGTH_LONG).show();
                //Toast.makeText(LoginActivity.this,"Error , please verify your inputs",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(string);
        }
    }

 /*
    // Registration AsyncTask Part
    // i don't need ' RegisterAsyncTask'  for the Moment
    class RegisterAsyncTask extends AsyncTask<RegisterBindingModel, String, AuthenticationResult> {
        @Override
        protected AuthenticationResult doInBackground(RegisterBindingModel... models) {
            RegisterBindingModel model = models[0];
            String result = null;
            try {
                Response<String> response = api.Register(model).execute();
                result = response.body();
                if (StringUtil.isNullOrWhitespace(result)) {
                    TokenModel tokenModel = api.Login("password", models[0].getEmail(),models[0].getPassword()).execute().body();
                    if (tokenModel != null && tokenModel.AccessToken != null) {
                        return new AuthenticationResult(true, tokenModel.AccessToken, null);
                    } else
                        return new AuthenticationResult(false, null, "Get Token failed");
                } else
                    return new AuthenticationResult(false, null, result);
            } catch (Exception e) {
                StringWriter exception = new StringWriter();
                e.printStackTrace(new PrintWriter(exception));
                return new AuthenticationResult(false, null, "Get Token failed\r\n"+exception);
            }



        }

        @Override
        protected void onPostExecute(final AuthenticationResult result) {


            if (!result.isSuccessful())
                AlertMessageBox.Show(MainActivity.this, "Error", result.getError(), AlertMessageBox.alertMessageBoxIcon.Error);
            else {
                AlertMessageBox.Show(MainActivity.this, "Successful", "Register successfully. You're going to be forwarded to protected resource. ", AlertMessageBox.alertMessageBoxIcon.Info, new AlertMessageBoxOkClickCallback() {
                    @Override
                    public void run() {
                        ValueActivity.intent(MainActivity.this).accessToken(result.getAccessToken()).start();
                    }
                });


                super.onPostExecute(result);
            }
        }
    }
    // fin Registration AsyncTask Part
*/

}

