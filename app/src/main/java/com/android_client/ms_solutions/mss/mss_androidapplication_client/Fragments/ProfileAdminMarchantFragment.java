package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Canvas.GlideCircleTransformation;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.HomeAdminMarchantActivity;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * Created by Walid Zhani @Walid.Zhy7 on on 19/04/2018.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileAdminMarchantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileAdminMarchantFragment extends Fragment {

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
    EditText editTextUsrName_ProfileUsr;
    EditText  editTextEmail_ProfileUsr;
    EditText  editTextFirstName_ProfileUser;
    EditText  editTextLastName_ProfileUser;
    //EditText  editTextPassword_ProfileUser;
    EditText  editTextPhone_ProfileUser;

    FloatingActionButton fab_activeEditUser , fab_saveAfterEditUser;

    private OnFragmentInteractionListener mListener;

    private String resultPatch_PartialUpdate;
    private SampleApi api = SampleApiFactory.getInstance();

    public ProfileAdminMarchantFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileAdminMarchantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileAdminMarchantFragment newInstance(String param1, String param2) {
        ProfileAdminMarchantFragment fragment = new ProfileAdminMarchantFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_admin_marchant, container, false);

        imgView_ProfileUsr = (ImageView) view.findViewById(R.id.imgViewUsr_Profile);

        //editTextId_ProfileUsr = (EditText) view.findViewById(R.id.editTxtIdUsr_Profile_AdminMarchant);
        editTextUsrName_ProfileUsr = (EditText) view.findViewById(R.id.editTxtUsrName_Profile_AdminMarchant);
        editTextEmail_ProfileUsr = (EditText) view.findViewById(R.id.editTxtEmail_Profile_AdminMarchant);
        editTextFirstName_ProfileUser = (EditText) view.findViewById(R.id.editTxtFirstName_Profile_AdminMarchant);
        editTextLastName_ProfileUser = (EditText) view.findViewById(R.id.editTxtLastName_Profile_AdminMarchant);
        //editTextPassword_ProfileUser = (EditText) view.findViewById(R.id.editTxtPassword_Profile_AdminMarchant);
        editTextPhone_ProfileUser = (EditText) view.findViewById(R.id.editTxtPhoneNumber_Profile_AdminMarchant);

        fab_activeEditUser = (FloatingActionButton) view.findViewById(R.id.fab_editUsrProfile_AdminMarchant);
        fab_saveAfterEditUser = (FloatingActionButton) view.findViewById(R.id.fab_SaveUsrProfile_AdminMarchant);

        fab_activeEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editTextId_ProfileUsr.setEnabled(true);
                editTextUsrName_ProfileUsr.setEnabled(true);
                editTextEmail_ProfileUsr.setEnabled(true);
                editTextFirstName_ProfileUser.setEnabled(true);
                editTextLastName_ProfileUser.setEnabled(true);
                //editTextPassword_ProfileUser.setEnabled(true); // il reste désactivé car password est 'Haché' , besh 5edma td5lch b3adha avec el bd
                editTextPhone_ProfileUser.setEnabled(true);

            }
        });

        fab_saveAfterEditUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               partialUpdatePatchProfileAdminMerchant();
               // Snackbar.make(view, "Not Usable Yet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        // Load Profile User Info into the Profile Fragment
        loadAdminMarchantProfileInfo();

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

    // Loading Information in the Profile User GUI
    public void loadAdminMarchantProfileInfo() {

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
        //editTextId_ProfileUsr.setText(HomeAdminMarchantActivity.id_user_static_adminMerchant);
        editTextUsrName_ProfileUsr.setText(HomeAdminMarchantActivity.userName_static_adminMerchant);
        editTextEmail_ProfileUsr.setText(HomeAdminMarchantActivity.userEmail_static_adminMerchant);
        editTextFirstName_ProfileUser.setText(HomeAdminMarchantActivity.userFirstName_static_adminMerchant);
        editTextLastName_ProfileUser.setText(HomeAdminMarchantActivity.userLastName_static_adminMerchant);
        //editTextPassword_ProfileUser.setText(HomeAdminMarchantActivity.userPassword_static_adminMerchant);
        editTextPhone_ProfileUser.setText(HomeAdminMarchantActivity.userPhoneNumber_static_adminMerchant);
    }

    private void partialUpdatePatchProfileAdminMerchant() { new PatchAdminMerchantTask().execute(); }
    //// Début : Parties des : Classes AsyncTask

    /// Class : PatchAdminMerchantTask

    class PatchAdminMerchantTask extends AsyncTask<String,String, String> {

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

            }

            System.out.println("The Get Values of Params Are : \r\n");
            //System.out.println(params[0]  + "\r\n" + params[1] + "\r\n" + params[2] + "\r\n" + params[3] + "\r\n" + params[4] + "\r\n" + params[5]);

            System.out.println(params[0]  + "\r\n" + params[1] + "\r\n" + params[2] + "\r\n" + params[3] + "\r\n" + params[4]);

            try {
                resultPatch_PartialUpdate = api.PartialUpdateProfileUser(params[0],params[1],params[2],params[3],params[4]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return resultPatch_PartialUpdate;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(ProfileAdminMarchantFragment.this.getContext(),resultPatch_PartialUpdate, Toast.LENGTH_LONG).show();
            //RefreshData(setUserName,setEmail,setFName,setLName,setPhoneNumber);
            super.onPostExecute(s);
        }

    }
}
