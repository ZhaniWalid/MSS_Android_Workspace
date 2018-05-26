package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeAdminMarchantActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.LoginActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.LoginActivity_;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils.StringUtil;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsScrollViewAdminMerchantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsScrollViewAdminMerchantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsScrollViewAdminMerchantFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Début : Attributes for Changing Password
    private EditText editTextOldPassword;
    private EditText  editTextNewPassword;
    private EditText  editTextConfirmNewPassword;

    private FloatingActionButton fab_activeEditChangePassword , fab_saveChangePassword;

    public  SampleApi api = SampleApiFactory.getInstance();
    public  String resultPatch_ChangePassword;
    private String msgFailedResult = "" , msgSucessResult = "" , msgSucessTitle = " ";
    private String getOldPassword = "" , getNewPassword = "" , getConfirmNewPassword = " ";

    private String strongPassword = "Strong Password",mediumPassword = "Medium Password",weakPassword = "Weak Password";
    public String  goodByUser = "";
    // Fin : Attributes for Changing Password

    public SettingsScrollViewAdminMerchantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsScrollViewAdminMerchantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsScrollViewAdminMerchantFragment newInstance(String param1, String param2) {
        SettingsScrollViewAdminMerchantFragment fragment = new SettingsScrollViewAdminMerchantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_scroll_view_admin_merchant, container, false);

        editTextOldPassword = view.findViewById(R.id.editTxtOldPassword_admin);
        editTextNewPassword = view.findViewById(R.id.editTxtNewPassword_admin);
        editTextConfirmNewPassword = view.findViewById(R.id.editTxtConfirmNewPassword_admin);

        fab_activeEditChangePassword = view.findViewById(R.id.fab_editChangePassword_admin);
        fab_saveChangePassword = view.findViewById(R.id.fab_SaveChangePassword_admin);

        fab_activeEditChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableTextsFields();
            }
        });

        fab_saveChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        editTextNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    isNewPasswordStrong();
                }
            }
        });

        editTextConfirmNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    isConfirmNewPasswordStrong();
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        } */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // My Methods

    private void getPasswordsValues(){

        getOldPassword = editTextOldPassword.getText().toString();;
        getNewPassword = editTextNewPassword.getText().toString();
        getConfirmNewPassword = editTextConfirmNewPassword.getText().toString();
    }

    public boolean validateOldPassword(String input){
        boolean isValid;

        if (!input.equals(LoginActivity.getPassword_StaticInLogin)){
            editTextOldPassword.setError("The input of Old Password doesn't match with value in data base");
            isValid = false;
        }else{
            isValid = true;
        }

        return isValid;
    }

    public boolean validateNewPassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            editTextNewPassword.setError("New Password should be between 4 and 15 alphanumeric characters : must contain letters a-z A-Z and at least one digit 0-9 ");
            isValid = false;
        } else {
            editTextNewPassword.setError(null);
            isValid = true;
        }

        return isValid;
    }

    public boolean validateConfirmNewPassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            editTextConfirmNewPassword.setError("Confirmation Of New Password should be between 4 and 15 alphanumeric characters : must contain letters a-z A-Z and at least one digit 0-9");
            isValid = false;
        } else {
            editTextConfirmNewPassword.setError(null);
            isValid = true;
        }

        return isValid;
    }

    public boolean isTwoPasswordsEquals(String newPassword,String confirmNewPassword){
        boolean isValid;

        if (!StringUtil.isPasswordsConfirmedEquals(newPassword,confirmNewPassword)){
            isValid = false;
        }else {
            isValid = true;
        }

        return isValid;
    }

    private void enableTextsFields(){

        editTextOldPassword.setEnabled(true);
        editTextNewPassword.setEnabled(true);
        editTextConfirmNewPassword.setEnabled(true);

    }

    private void emptyFieldsNotAllowed(){
        editTextOldPassword.setError("Old Password Required");
        editTextNewPassword.setError("New Password Required");
        editTextConfirmNewPassword.setError("Confirmation Of New Password Required");
    }

    private void isNewPasswordStrong (){

        if (StringUtil.passwordStrength(getNewPassword).equals("StrongPassword")){
            editTextNewPassword.setError(strongPassword);
            editTextNewPassword.setBackgroundColor(getResources().getColor(R.color.green));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#7FFF00"));
        }else if (StringUtil.passwordStrength(getNewPassword).equals("MediumPassword")) {
            editTextNewPassword.setError(mediumPassword);
            editTextNewPassword.setBackgroundColor(getResources().getColor(R.color.orange));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#FFA500"));
        }else{
            editTextNewPassword.setError(weakPassword);
            editTextNewPassword.setBackgroundColor(getResources().getColor(R.color.red));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    private void isConfirmNewPasswordStrong (){

        if (StringUtil.passwordStrength(getConfirmNewPassword).equals("StrongPassword")){
            editTextConfirmNewPassword.setError(strongPassword);
            editTextConfirmNewPassword.setBackgroundColor(getResources().getColor(R.color.green));
        }else if (StringUtil.passwordStrength(getConfirmNewPassword).equals("MediumPassword")) {
            editTextConfirmNewPassword.setError(mediumPassword);
            editTextConfirmNewPassword.setBackgroundColor(getResources().getColor(R.color.orange));
        }else{
            editTextConfirmNewPassword.setError(weakPassword);
            editTextConfirmNewPassword.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    private void changePassword(){

        getPasswordsValues();

        if ( !StringUtil.isNullOrWhitespace(getOldPassword) && !StringUtil.isNullOrWhitespace(getNewPassword) && !StringUtil.isNullOrWhitespace(getConfirmNewPassword) ){

            if ( !validateOldPassword(getOldPassword) || !validateNewPassword(getNewPassword) || !validateConfirmNewPassword(getConfirmNewPassword)){
                Toast.makeText(SettingsScrollViewAdminMerchantFragment.this.getContext(),"One or More Fields are incorrect,Please try again and make sure of what you type",Toast.LENGTH_LONG).show();
            }else if (!isTwoPasswordsEquals(getNewPassword,getConfirmNewPassword)){
                String msgConfirmPwdFalse = " ' New Password '  &  ' Confirmation Of New Password ' did not match , please Retype Again with same values";
                Toast.makeText(SettingsScrollViewAdminMerchantFragment.this.getContext(),msgConfirmPwdFalse,Toast.LENGTH_LONG).show();
            }else{
                new ChangePasswordAdminMerchantTask().execute();
            }
        } else{
            emptyFieldsNotAllowed();
            Toast.makeText(SettingsScrollViewAdminMerchantFragment.this.getContext(),"All Fields Are Required , You Can't left empty fields",Toast.LENGTH_LONG).show();
        }
    }

    private void failedUpdate(){

        String msgNewPwdError =
                "1. Password must have at least one non letter or digit character \r\n" +
                "2. Password must have at least one uppercase ('A'-'Z').";
        editTextNewPassword.setError(msgNewPwdError);

        String msgConfirmNewPwdError =
                "1. Password must have at least one non letter or digit character \r\n" +
                "2. Password must have at least one uppercase ('A'-'Z').";
        editTextConfirmNewPassword.setError(msgConfirmNewPwdError);
    }

    private void logoutViews() {
        new logoutAdminMerchantTask().execute(HomeAdminMarchantActivity.id_user_static_adminMerchant);
    }

    //// Début : Parties des : Classes AsyncTask

    /// Class : ChangePasswordAdminMerchantTask

    class ChangePasswordAdminMerchantTask extends AsyncTask<String,String, String> {

        @Override
        protected String doInBackground(String... params) {

            params = new String[4];

            for (int i=0 ; i<params.length ; i++){
                params[0] = editTextOldPassword.getText().toString();;
                params[1] = editTextNewPassword.getText().toString();
                params[2] = editTextConfirmNewPassword.getText().toString();
            }

            System.out.println("The Get Values of Params Are : \r\n");
            System.out.println(params[0]  + "\r\n" + params[1] + "\r\n" + params[2]);

            try {
                resultPatch_ChangePassword = api.ChangePassword(params[0],params[1],params[2]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultPatch_ChangePassword;
        }

        @Override
        protected void onPostExecute(String s) {

            ProgressDialog progressDialogUpdatePasswordSuccess = new ProgressDialog(SettingsScrollViewAdminMerchantFragment.this.getContext(), R.style.AppTheme_Dark_Dialog);

            if(resultPatch_ChangePassword.equals("True => Update_Password_Succeed")){   // "True => Update_Password_Succeed" => Valeur Retournée from Web Services in Asp.Net
                msgSucessTitle = "Congratulations Mr : "+HomeAdminMarchantActivity.userName_static_adminMerchant+ "...\r\n";
                msgSucessResult = "Please Check Your Email for Changes You Have Made...\r\n You Will be Redirected to Login Screen Now...";

                progressDialogUpdatePasswordSuccess.setIndeterminate(true);
                progressDialogUpdatePasswordSuccess.setTitle(msgSucessTitle);
                progressDialogUpdatePasswordSuccess.setMessage(msgSucessResult);
                progressDialogUpdatePasswordSuccess.setIcon(R.drawable.info);
                progressDialogUpdatePasswordSuccess.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                // TO do here before redirecting Home Activity : LogoutAsyncTask to logout from sessions
                                logoutViews();
                                LoginActivity_.intent(SettingsScrollViewAdminMerchantFragment.this.getContext()).start();
                                Toast.makeText(SettingsScrollViewAdminMerchantFragment.this.getContext(),"Redirecting To Login Screen ...Good By Mr : "+HomeAdminMarchantActivity.userName_static_adminMerchant,Toast.LENGTH_LONG).show();
                            }
                        },4000
                );
            }

            // "False => Update_Password_Failed" => Valeur Retournée from Web Services in Asp.Net
            else {
                //msgFailedResult = "Updating Password Failed ,because the email : "+ HomeAdminMarchantActivity.userEmail_static_adminMerchant + " is already used ! we can't complete process of changing password and sending you changes ! Please Try Again With another Email !";
                failedUpdate();
                msgFailedResult = "Update password failed for some reasons like : \r\n" +
                        "1. Passwords must have at least one non letter or digit character \r\n" +
                        "2. Passwords must have at least one uppercase ('A'-'Z') \r\n" +
                        "3. Email Problem : Same Email used with other user ( Absurde )";
                Toast.makeText(SettingsScrollViewAdminMerchantFragment.this.getContext(),msgFailedResult,Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(s);
        }
    }

    /// Class : logoutAdminMerchantTask
    class logoutAdminMerchantTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                goodByUser = api.Logout(HomeAdminMarchantActivity.id_user_static_adminMerchant).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.activity_main,welcomeUser);
            //setListAdapter(adapter);
            //Toast.makeText(SettingsScrollViewFragment.this.getContext(),goodByUser,Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }
}

