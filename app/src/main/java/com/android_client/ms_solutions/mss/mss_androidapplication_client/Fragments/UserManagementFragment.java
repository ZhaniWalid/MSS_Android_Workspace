package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.ListViewUsersMerchantsAdapter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.AspNetUserBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils.StringUtil;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserManagementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SampleApi api = SampleApiFactory.getInstance();

    private EditText editText_SearchUsersMerchants;

    private List<AspNetUserBindingModel> List_UserMerchants;
    private List<AspNetUserBindingModel> List_UserMerchants_ForFiltring;
    private List<AspNetUserBindingModel> List_UserMerchant_ForRefresh;

    ListView ListView_UsersMerchants;
    ListViewUsersMerchantsAdapter listViewUsersMerchantsAdapter;

    int textLength = 0;

    // button to inflate the interface of addoing new user Merchant
    private FloatingActionButton fab_createUserMerchant;

    // Fields & Buttons in the Interface of adding new user Merchant
    private EditText editTextFirstNameUsrMerchant,editTextLastNameUsrMerchant,editTextEmailUsrMerchant,editTextPasswordUsrMerchant;
    private EditText editTextConfirmPasswordUsrMerchant,editTextPhoneNumUsrMerchant,editTextUserNameUsrMerchant;
    private Spinner  spinnerOrganizationId;
    private FloatingActionButton fab_addUserMerchant,fab_cancelAddUserMerchant;


    // attributes to get Fields values in the Interface of adding new user Merchant
    private String getFirstNameUsrMrch = " " ,getLastNameUsrMrch = " ",getEmailUsrMrch = " ",getPwdUsrMrch = " ";
    private String getConfirmPwdUsrMrch = " ",getPhoneNumUsrMrch = " ",getUserNameUsrMrch = " ",getOrganizationId = " ";
    private String getEqualedPassword = " ",organizationIdTodb = " ";
    //private int organizationIdTodb = 0;

    // other attributes
    private String strongPassword = "Strong Password",mediumPassword = "Medium Password",weakPassword = "Weak Password";
    private String resultCreationUsrMrch = " ";

    private TextView textView_TotalUsers;

    public UserManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserManagementFragment newInstance(String param1, String param2) {
        UserManagementFragment fragment = new UserManagementFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_management, container, false);

        ListView_UsersMerchants = view.findViewById(R.id.ListView_usersMerchants);
        editText_SearchUsersMerchants = view.findViewById(R.id.editText_search_usersMerchants);
        textView_TotalUsers = view.findViewById(R.id.txtView_totalSizeOfUsers);

        fab_createUserMerchant = view.findViewById(R.id.fab_createUserMerchant);

        List_UserMerchants_ForFiltring = new ArrayList<>();

        loadingUsersMerchantData();

        fab_createUserMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UICreateUserMerchant();
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    /*
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        setHasOptionsMenu(true);
        super.setHasOptionsMenu(hasMenu);
    }
    */

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

    private void UICreateUserMerchant() {

        LayoutInflater inflater = LayoutInflater.from(UserManagementFragment.this.getContext());
        View subView = inflater.inflate(R.layout.create_user_merchant_ui, null);

        editTextFirstNameUsrMerchant = subView.findViewById(R.id.editTxtFirstNameUsrMerchant);
        editTextLastNameUsrMerchant = subView.findViewById(R.id.editTxtLastNameUsrMerchant);
        editTextEmailUsrMerchant = subView.findViewById(R.id.editTxtEmailUsrMerchant);
        editTextPasswordUsrMerchant = subView.findViewById(R.id.editTxtPasswordUsrMerchant);
        editTextConfirmPasswordUsrMerchant = subView.findViewById(R.id.editTxtConfirmPasswordUsrMerchnat);
        editTextPhoneNumUsrMerchant = subView.findViewById(R.id.editTxtPhoneNumberUsrMerchant);
        editTextUserNameUsrMerchant = subView.findViewById(R.id.editTxtUserNameUsrMerchant);
        spinnerOrganizationId = subView.findViewById(R.id.spinnerOrganizationIdUsrMerchant);

        fab_addUserMerchant = subView.findViewById(R.id.fab_addUserMerchant);
        fab_cancelAddUserMerchant = subView.findViewById(R.id.fab_CancelAddUserMerchant);

        editTextPasswordUsrMerchant.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    isPasswordStrong();
                }
            }
        });

        editTextConfirmPasswordUsrMerchant.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    isConfirmPasswordStrong();
                }
            }
        });

        fab_addUserMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              CreateUserMerchant();
            }
        });

        fab_cancelAddUserMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyAllFields();
                Toast.makeText(UserManagementFragment.this.getContext(),"Task Create New User Merchant Canceled",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(UserManagementFragment.this.getContext());
        builder.setTitle("Creating User Merchant Interface");
        builder.setIcon(UserManagementFragment.this.getContext().getResources().getDrawable(R.drawable.ic_account_circle_black_24dp_v21));
        builder.setView(subView);
        builder.create();
        builder.show();

        //getFieldsValues();
    }

    private void getFieldsValues(){

        getFirstNameUsrMrch    = editTextFirstNameUsrMerchant.getText().toString();
        getLastNameUsrMrch     = editTextLastNameUsrMerchant.getText().toString();
        getEmailUsrMrch        = editTextEmailUsrMerchant.getText().toString();
        getPwdUsrMrch          = editTextPasswordUsrMerchant.getText().toString();
        getConfirmPwdUsrMrch   = editTextConfirmPasswordUsrMerchant.getText().toString();
        getPhoneNumUsrMrch     = editTextPhoneNumUsrMerchant.getText().toString();
        getUserNameUsrMrch     = editTextUserNameUsrMerchant.getText().toString();
        getOrganizationId      = String.valueOf(spinnerOrganizationId.getSelectedItem());

        switch (getOrganizationId){

            case "Merchant":
                organizationIdTodb = "42";
                break;

            case "Bank":
                organizationIdTodb = "1";
                break;
        }
    }

    private void isPasswordStrong (){

        if (StringUtil.passwordStrength(getPwdUsrMrch).equals("StrongPassword")){
            editTextPasswordUsrMerchant.setError(strongPassword);
            editTextPasswordUsrMerchant.setBackgroundColor(getResources().getColor(R.color.green));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#7FFF00"));
        }else if (StringUtil.passwordStrength(getPwdUsrMrch).equals("MediumPassword")) {
            editTextPasswordUsrMerchant.setError(mediumPassword);
            editTextPasswordUsrMerchant.setBackgroundColor(getResources().getColor(R.color.orange));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#FFA500"));
        }else{
            editTextPasswordUsrMerchant.setError(weakPassword);
            editTextPasswordUsrMerchant.setBackgroundColor(getResources().getColor(R.color.red));
            //editTextNewPassword.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    private void isConfirmPasswordStrong (){

        if (StringUtil.passwordStrength(getConfirmPwdUsrMrch).equals("StrongPassword")){
            editTextConfirmPasswordUsrMerchant.setError(strongPassword);
            editTextConfirmPasswordUsrMerchant.setBackgroundColor(getResources().getColor(R.color.green));
        }else if (StringUtil.passwordStrength(getConfirmPwdUsrMrch).equals("MediumPassword")) {
            editTextConfirmPasswordUsrMerchant.setError(mediumPassword);
            editTextConfirmPasswordUsrMerchant.setBackgroundColor(getResources().getColor(R.color.orange));
        }else{
            editTextConfirmPasswordUsrMerchant.setError(weakPassword);
            editTextConfirmPasswordUsrMerchant.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    private boolean validatePassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            String msgPasswordError = "Password should be between 4 and 15 alphanumeric characters : must contain letters a-z A-Z and at least one digit 0-9 ";
            editTextPasswordUsrMerchant.setError(msgPasswordError);
            isValid = false;
        } else {
            editTextPasswordUsrMerchant.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean validateConfirmPassword(String input) {
        boolean isValid;

        // Validates Password in Login
        if (!StringUtil.isValidPasswordLength(input)) {
            String msgPasswordError = "Password should be between 4 and 15 alphanumeric characters : must contain letters a-z A-Z and at least one digit 0-9 ";
            editTextConfirmPasswordUsrMerchant.setError(msgPasswordError);
            isValid = false;
        } else {
            editTextConfirmPasswordUsrMerchant.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean isTwoPasswordsEquals(String Password,String confirmPassword){
        boolean isValid;

        if (!StringUtil.isPasswordsConfirmedEquals(Password,confirmPassword)){
            isValid = false;
        }else {
            isValid = true;
        }

        return isValid;
    }

    private boolean isEmailAddressValid(String email){
        boolean isValid;

        if(!StringUtil.isEmailAddress(email)){
            String msgEmailError = "Email Invalid,Should be in this form : ' example@domain.com ' or ' example-subExample@domain.com ' or ' example.subExample@domain.com ' or ' example_subExample@domain.com ' ";
            editTextEmailUsrMerchant.setError(msgEmailError);
            isValid = false;
        }else {
            editTextEmailUsrMerchant.setError(null);
            isValid = true;
        }

        return isValid;
    }

    private boolean isPhoneNumberValid(String inputPhoneNumbr){
        boolean isValid;

        if(!StringUtil.isPhoneNumber(inputPhoneNumbr)){
            String msgErrorPhoneNumbr = "Phone Number Should be only contains digit numbers 0-9 , and between 8-15 length";
            editTextPhoneNumUsrMerchant.setError(msgErrorPhoneNumbr);
            isValid = false;
        }else {
            editTextPhoneNumUsrMerchant.setError(null);
            isValid = true;
        }

        return isValid;
    }


    private void emptyAllFields(){

        String emptyText = "";

        editTextFirstNameUsrMerchant.setText(emptyText);
        editTextLastNameUsrMerchant.setText(emptyText);
        editTextEmailUsrMerchant.setText(emptyText);
        editTextPasswordUsrMerchant.setText(emptyText);
        editTextConfirmPasswordUsrMerchant.setText(emptyText);
        editTextPhoneNumUsrMerchant.setText(emptyText);
        editTextUserNameUsrMerchant.setText(emptyText);

    }

    private void emptyFieldsNotAllowed(){

        editTextFirstNameUsrMerchant.setError("First Name Required");
        editTextLastNameUsrMerchant.setError("Last Name Required");
        editTextEmailUsrMerchant.setError("Email Required");
        editTextPasswordUsrMerchant.setError("Password Required");
        editTextConfirmPasswordUsrMerchant.setError("Confirm Password Required");
        editTextPhoneNumUsrMerchant.setError("Phone Number Required");
        editTextUserNameUsrMerchant.setError("User Name Required");
    }

    private void FilterUsersMerchantsData(){

        editText_SearchUsersMerchants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                textLength = editText_SearchUsersMerchants.getText().length();
                List_UserMerchants_ForFiltring.clear();

                for (int i=0 ; i<List_UserMerchants.size(); i++){

                    if (textLength <= List_UserMerchants.get(i).getUserName().length()){
                        Log.d("Log User Name",List_UserMerchants.get(i).getUserName().toLowerCase().trim());
                        if (    List_UserMerchants.get(i).getUserName().toLowerCase().trim().contains(
                                editText_SearchUsersMerchants.getText().toString().toLowerCase().trim())
                             || List_UserMerchants.get(i).getFirstName().toLowerCase().trim().contains(
                                editText_SearchUsersMerchants.getText().toString().toLowerCase().trim())
                             || List_UserMerchants.get(i).getLastName().toLowerCase().trim().contains(
                                editText_SearchUsersMerchants.getText().toString().toLowerCase().trim() )
                             || List_UserMerchants.get(i).getEmail().toLowerCase().trim().contains(
                                editText_SearchUsersMerchants.getText().toString().toLowerCase().trim())
                             || List_UserMerchants.get(i).getPhoneNumber().toLowerCase().trim().contains(
                                editText_SearchUsersMerchants.getText().toString().toLowerCase().trim())
                             || List_UserMerchants.get(i).getOrganizationTypeName().toLowerCase().trim().contains(
                                editText_SearchUsersMerchants.getText().toString().toLowerCase().trim()) ) {

                            List_UserMerchants_ForFiltring.add(List_UserMerchants.get(i));
                        }
                    }
                }

                listViewUsersMerchantsAdapter = new ListViewUsersMerchantsAdapter(UserManagementFragment.this.getContext(),List_UserMerchants_ForFiltring);
                ListView_UsersMerchants.setAdapter(listViewUsersMerchantsAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void CreateUserMerchant(){

        getFieldsValues();

        if (!StringUtil.isNullOrWhitespace(getFirstNameUsrMrch) && !StringUtil.isNullOrWhitespace(getLastNameUsrMrch) && !StringUtil.isNullOrWhitespace(getEmailUsrMrch)
              && !StringUtil.isNullOrWhitespace(getPwdUsrMrch) && !StringUtil.isNullOrWhitespace(getConfirmPwdUsrMrch) && !StringUtil.isNullOrWhitespace(getPhoneNumUsrMrch)
              && !StringUtil.isNullOrWhitespace(getUserNameUsrMrch) && !StringUtil.isNullOrWhitespace(getOrganizationId)){

                if(!isEmailAddressValid(getEmailUsrMrch)){
                    Toast.makeText(UserManagementFragment.this.getContext(),"Email Address Not Valid,Please Verify your input ",Toast.LENGTH_LONG).show();
                }else if(!isPhoneNumberValid(getPhoneNumUsrMrch)){
                     Toast.makeText(UserManagementFragment.this.getContext(),"Phone Number Not Valid,Please Verify Your Input if they are Digits or Not",Toast.LENGTH_LONG).show();
                }else if (!validatePassword(getPwdUsrMrch) || !validateConfirmPassword(getConfirmPwdUsrMrch)){
                    String msgPasswordsInput = "Password or Confirmation Password is incorrect,Please try again and make sure of what you type";
                    Toast.makeText(UserManagementFragment.this.getContext(),msgPasswordsInput,Toast.LENGTH_LONG).show();
                }else if (!isTwoPasswordsEquals(getPwdUsrMrch,getConfirmPwdUsrMrch)){
                    String msgConfirmPwdFalse = " ' Password '  &  ' Confirmation Password ' did not match , please Retype Again with same values";
                    Toast.makeText(UserManagementFragment.this.getContext(),msgConfirmPwdFalse,Toast.LENGTH_LONG).show();
                }else{
                    new CreateUserMerchantTask().execute();                }
        }else {
            emptyFieldsNotAllowed();
            Toast.makeText(UserManagementFragment.this.getContext(),"All Fields Are Required , You Can't left empty fields",Toast.LENGTH_LONG).show();
        }
    }

    private void loadingUsersMerchantData() {
        new UsersMerchantsDataTask().execute();
    }

    //// Début : Parties des : Classes AsyncTask

    /// Class : UsersMerchantsDataTask
    class UsersMerchantsDataTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                List_UserMerchants = api.GetOnlyAllUsersMerchants().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {

            listViewUsersMerchantsAdapter = new ListViewUsersMerchantsAdapter(UserManagementFragment.this.getContext(),List_UserMerchants);
            ListView_UsersMerchants.setAdapter(listViewUsersMerchantsAdapter);

            textView_TotalUsers.setText("Total Users : "+List_UserMerchants.size());

            FilterUsersMerchantsData();

            super.onPostExecute(s);
        }
    }

    /// Class : CreateUserMerchantTask
    class CreateUserMerchantTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            params = new String[9];

            for (int i=0;i<params.length;i++){

                params[0] = getFirstNameUsrMrch;
                params[1] = getLastNameUsrMrch;
                params[2] = getEmailUsrMrch;
                params[3] = getPwdUsrMrch;
                params[4] = getConfirmPwdUsrMrch;
                params[5] = getPhoneNumUsrMrch;
                params[6] = getUserNameUsrMrch;
                params[7] = organizationIdTodb;
            }

            int IntOf_organizationIdTodb = Integer.parseInt(params[7]);

            try {
                resultCreationUsrMrch = api.CreateUserMerchant(params[0],params[1],params[2],params[3],params[4],params[5],params[6],IntOf_organizationIdTodb).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // After Successfully Adding new User Merchant Get Again the data from db,after being updated
            // To Show them in List View & Refresh it
            try {
                List_UserMerchant_ForRefresh = api.GetOnlyAllUsersMerchants().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultCreationUsrMrch;
        }

        @Override
        protected void onPostExecute(String s) {

            if (resultCreationUsrMrch.equals("User Created Successfuly , an email has been sent to him")){
                String msgSuccess = "Congratulations ! User Created Successfuly , an email has been sent to him";
                Toast.makeText(UserManagementFragment.this.getContext(),msgSuccess,Toast.LENGTH_LONG).show();
                emptyAllFields();
                // After Successfully Adding new User Merchant Get Again the data from db,after being updated
                // Show them again into the ListView to refresh it
                 listViewUsersMerchantsAdapter = new ListViewUsersMerchantsAdapter(UserManagementFragment.this.getContext(),List_UserMerchant_ForRefresh);
                 ListView_UsersMerchants.setAdapter(listViewUsersMerchantsAdapter);

            }else{
                String msgFail = "Failure ! Unable to Create new User,Please Verify your inputs,and try again";
                Toast.makeText(UserManagementFragment.this.getContext(),msgFail,Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }
}
