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
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
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
    @BindView(R.id.editTextEmail) EditText _email_UsrName_editTxt;
    @BindView(R.id.editTextPassword) EditText _pwd_editTxt;

    String email, password;
    // used in changing password to verify typed old password with password in db
    public static String getPassword_StaticInLogin = "";

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
        String email = "CaisseMonoprix";
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

