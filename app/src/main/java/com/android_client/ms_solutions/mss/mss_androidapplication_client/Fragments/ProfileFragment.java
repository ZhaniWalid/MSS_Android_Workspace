package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Canvas.GlideCircleTransformation;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeAdminMarchantActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.UserPatchRequestModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils.StringUtil;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 16/04/2018.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Attributes Related to the Layout
    ImageView imgView_ProfileUsr;
    //EditText  editTextId_ProfileUsr;
    EditText  editTextUsrName_ProfileUsr;
    EditText  editTextEmail_ProfileUsr;
    EditText  editTextFirstName_ProfileUser;
    EditText  editTextLastName_ProfileUser;
    //EditText  editTextPassword_ProfileUser;
    EditText  editTextPhone_ProfileUser;

    FloatingActionButton fab_activeEditUser , fab_saveAfterEditUser;

    private OnFragmentInteractionListener mListener;

    public SampleApi api = SampleApiFactory.getInstance();
    public String resultPatch_PartialUpdate;
    public UserPatchRequestModel userPatchRequestModel = new UserPatchRequestModel();

    private String getId = "" , getUserName = "" , getEmail = "" , getFName = "" , getLName = "" , getPhoneNumber = "";

    /*
    private String setUserName = "" , setEmail = "" , setFName = "" , setLName = "" , setPhoneNumber = "";

    private String getUserName_AfterUpdate = " ",getEmail_AfterUpdate = " ",getFirstName_AfterUpdate = " ";
    private String getLastName_AfterUpdate = " ",getPhoneNumb_AfterUpdate = " ";
    */

    private Handler myHander = new Handler();

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgView_ProfileUsr = (ImageView) view.findViewById(R.id.imgViewUsr_Profile);

        //editTextId_ProfileUsr = (EditText) view.findViewById(R.id.editTxtIdUsr_Profile);
        editTextUsrName_ProfileUsr = (EditText) view.findViewById(R.id.editTxtUsrName_Profile);
        editTextEmail_ProfileUsr = (EditText) view.findViewById(R.id.editTxtEmail_Profile);
        editTextFirstName_ProfileUser = (EditText) view.findViewById(R.id.editTxtFirstName_Profile);
        editTextLastName_ProfileUser = (EditText) view.findViewById(R.id.editTxtLastName_Profile);
       //editTextPassword_ProfileUser = (EditText) view.findViewById(R.id.editTxtPassword_Profile);
        editTextPhone_ProfileUser = (EditText) view.findViewById(R.id.editTxtPhoneNumber_Profile);

        fab_activeEditUser = (FloatingActionButton) view.findViewById(R.id.fab_editUsrProfile);
        fab_saveAfterEditUser = (FloatingActionButton) view.findViewById(R.id.fab_SaveUsrProfile);

        // Load Profile User Info into the Profile Fragment
        loadUserProfileInfo();

        fab_activeEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableAllFields();
            }
        });

        fab_saveAfterEditUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                partialUpdatePatchProfileUser();
                //Snackbar.make(view, "Not Usable Yet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               //myHander.removeCallbacks(loadAgainDataProfileInfo);
                //myHander.post(loadAgainDataProfileInfo);
            }
        });

       // RefrechAfterUpdate();

        return view;
    }

  /*  private  Runnable loadAgainDataProfileInfo = new Runnable() {
        @Override
        public void run() {
            //loadUserProfileInfo();
            myHander.postDelayed(this,1000);
        }
    };*/
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

    // Enable Fields
    private void enableAllFields(){

        editTextUsrName_ProfileUsr.setEnabled(true);
        editTextEmail_ProfileUsr.setEnabled(true);
        editTextFirstName_ProfileUser.setEnabled(true);
        editTextLastName_ProfileUser.setEnabled(true);
        editTextPhone_ProfileUser.setEnabled(true);
    }

    // Loading Information in the Profile User GUI
    private void loadUserProfileInfo() {

        // Loading Profile Image
        Glide.with(this.getContext())
                .load(getResources().getIdentifier("my_foto_profile", "drawable", this.getContext().getPackageName())) // load image from local storage 'drawable' to image view
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleTransformation(this.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView_ProfileUsr);

        // Loading Texts Informations to Edit Texts
        //editTextId_ProfileUsr.setText(HomeActivity.id_user_static);
        editTextUsrName_ProfileUsr.setText(HomeActivity.userName_static);
        editTextEmail_ProfileUsr.setText(HomeActivity.userEmail_static);
        editTextFirstName_ProfileUser.setText(HomeActivity.userFirstName_static);
        editTextLastName_ProfileUser.setText(HomeActivity.userLastName_static);
        //editTextPassword_ProfileUser.setText(HomeActivity.userPassword_static);
        editTextPhone_ProfileUser.setText(HomeActivity.userPhoneNumber_static);

        getId = HomeActivity.id_user_static;
        getUserName = editTextUsrName_ProfileUsr.getText().toString();
        getEmail = editTextEmail_ProfileUsr.getText().toString();
        getFName = editTextFirstName_ProfileUser.getText().toString();
        getLName = editTextLastName_ProfileUser.getText().toString();
        getPhoneNumber = editTextPhone_ProfileUser.getText().toString();

        System.out.println("The Get Values of TextFields Are : \r\n");
        System.out.println(getId + "\r\n" + getUserName + "\r\n" + getEmail + "\r\n" + getFName + "\r\n" + getLName + "\r\n" + getPhoneNumber);

    }

    /*
    private void RefreshData(){

        editTextUsrName_ProfileUsr.setText(getUserName_AfterUpdate);
        editTextEmail_ProfileUsr.setText(getEmail_AfterUpdate);
        editTextFirstName_ProfileUser.setText(getFirstName_AfterUpdate);
        editTextLastName_ProfileUser.setText(getLastName_AfterUpdate);
        editTextPhone_ProfileUser.setText(getPhoneNumb_AfterUpdate);
    }
    */

    /*
    private void RefrechAfterUpdate(){

        // refresh text views
       // HomeActivity.txtViewUsrName.setText(setUserName);
      //  HomeActivity.txtViewEmail.setText(setEmail);
        // refrech editTexts
        editTextUsrName_ProfileUsr.setText(setUserName);
        editTextEmail_ProfileUsr.setText(setEmail);
        editTextFirstName_ProfileUser.setText(setFName);
        editTextLastName_ProfileUser.setText(setLName);
        //editTextPassword_ProfileUser.setText(HomeActivity.userPassword_static);
        editTextPhone_ProfileUser.setText(setPhoneNumber);
    }*/

    private void partialUpdatePatchProfileUser(){
        new PatchUserMerchantTask().execute();
        //new PatchUserMerchantTask().execute(getUserName,getEmail,getFName,getLName,getPhoneNumber);
    }

    //// Début : Parties des : Classes AsyncTask

    /// Class : PatchUserMerchantTask

    class PatchUserMerchantTask extends AsyncTask<String,String, String> {

        /*@Override
        protected String doInBackground(UserPatchRequestModel... models) {

            models = new UserPatchRequestModel[5];

            userPatchRequestModel = models[5];

            String res = null;
            try {
                //resultPatch_PartialUpdate = api.PartialUpdateProfileUser(models[0],models[1],models[2],models[3],models[4]).execute().body();
                 Response<String> response = api.PatchUpdateProfileUser(userPatchRequestModel).execute();
                 res = response.body();
                if(!StringUtil.isNullOrWhitespace(res)){
                    resultPatch_PartialUpdate = api.PartialUpdateProfileUser(models[0].getUserName(),models[1].getEmail(),models[2].getFirstName(),models[3].getLastName(),models[4].getPhoneNumber()).execute().body();
                    //resultPatch_PartialUpdate = api.PartialUpdateProfileUser(getUserName,getEmail,getFName,getLName,getPhoneNumber).execute().body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }*/


        @Override
        protected String doInBackground(String... params) {
            params = new String[6];

            for (int i=0 ; i<params.length;i++){

                //params[0] = getId;
                params[0] = editTextUsrName_ProfileUsr.getText().toString();;
                params[1] = editTextEmail_ProfileUsr.getText().toString();
                params[2] = editTextFirstName_ProfileUser.getText().toString();
                params[3] = editTextLastName_ProfileUser.getText().toString();
                params[4] = editTextPhone_ProfileUser.getText().toString();

                // for rafraiching fields after updating profil
               /* setUserName = params[0];
                setEmail = params[1];
                setFName = params[2];
                setLName = params[3];
                setPhoneNumber = params[4];*/
            }

            System.out.println("The Get Values of Params Are : \r\n");
            //System.out.println(params[0]  + "\r\n" + params[1] + "\r\n" + params[2] + "\r\n" + params[3] + "\r\n" + params[4] + "\r\n" + params[5]);

            System.out.println(params[0]  + "\r\n" + params[1] + "\r\n" + params[2] + "\r\n" + params[3] + "\r\n" + params[4]);

            try {
                resultPatch_PartialUpdate = api.PartialUpdateProfileUser(params[0],params[1],params[2],params[3],params[4]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            getUserName_AfterUpdate   = params[0];
            getEmail_AfterUpdate      = params[1];
            getFirstName_AfterUpdate  = params[2];
            getLastName_AfterUpdate   = params[3];
            getPhoneNumb_AfterUpdate  = params[4];
            */

            //userPatchRequestModel = api.PatchUserHashMap(hashMap);
            //Gson gson = new Gson();
            //gson.toJson(userPatchRequestModel);
            Gson gson = new GsonBuilder().create();
            String resGson = gson.toJson(userPatchRequestModel);
            System.out.println("Json System Out Println : \r\n"+resGson);

           /* if(resGson != null){
                resultPatch_PartialUpdate = "Update Succeeded";
            }else{
                resultPatch_PartialUpdate = "Update Failed";
            }*/

            return resultPatch_PartialUpdate;
        }

        @Override
        protected void onPostExecute(String s) {

            if (resultPatch_PartialUpdate.equals("Update Succeeded")){
                String msgSuccess = "Update succeeded for your profile : changes will be affected after Reconnection";
                Toast.makeText(ProfileFragment.this.getContext(),msgSuccess, Toast.LENGTH_LONG).show();
                //RefreshData();
            }else{
                String msgFailed = "Update Failed For Your Profile";
                Toast.makeText(ProfileFragment.this.getContext(),msgFailed, Toast.LENGTH_LONG).show();
            }
            //RefreshData(setUserName,setEmail,setFName,setLName,setPhoneNumber);
            super.onPostExecute(s);
        }
    }
}
