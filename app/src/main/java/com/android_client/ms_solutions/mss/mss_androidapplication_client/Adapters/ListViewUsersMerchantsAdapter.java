package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.ViewUserMerchantHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.AspNetUserBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 14/05/2018.
 */

public class ListViewUsersMerchantsAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    //SampleApi api = SampleApiFactory.getInstance();
    private List<AspNetUserBindingModel> listUsersMerchants_Holder;

    private String blockedUser = " " , unblockedUser = " ", deletedUser = " ";
    private SampleApi api = SampleApiFactory.getInstance();

    private AspNetUserBindingModel userMerchant;

    private ViewUserMerchantHolder holder;

    private String getId_ToBlock = "",getUserName_ToBlock = " ";
    private String getId_ToUnblock = "",getUserName_ToUnblock = " ";
    private String getId_ToDelete = " ",getUserName_ToDelete = " ";

    private int getPosition = 0;

    public ListViewUsersMerchantsAdapter() {
    }

    public ListViewUsersMerchantsAdapter(Context context, List<AspNetUserBindingModel> listUsersMerchants_Holder) {
        mContext = context;
        this.listUsersMerchants_Holder = listUsersMerchants_Holder;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listUsersMerchants_Holder.size();
    }

    @Override
    public Object getItem(int i) {
        return listUsersMerchants_Holder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        //final AspNetUserBindingModel userMerchant = listUsersMerchants_Holder.get(position);
        userMerchant = listUsersMerchants_Holder.get(position);

        if (view == null){

            holder = new ViewUserMerchantHolder();
            view = inflater.inflate(R.layout.list_users_merchants,null);

            holder.imageViewUserMerchant = view.findViewById(R.id.image_UserMerchant);

            // Info Section
            holder.textViewFullNameUserMerchant = view.findViewById(R.id.txtView_FullName_UserMerchant);
            holder.textViewEmailUserMerchant = view.findViewById(R.id.txtView_Email_UserMerchant);
            holder.textViewPhoneNumbUserMerchant = view.findViewById(R.id.txtView_PhoneNumber_UserMerchant);

            // Other Section
            holder.textViewUserNameUserMerchant = view.findViewById(R.id.txtView_UserName_UserMerchant);
            holder.textViewOrganizationTypeUserMerchant = view.findViewById(R.id.txtView_OrganizationType_UserMerchant);

            // Blockin && Deleting Section
            holder.imageViewBlockUserMerchant = view.findViewById(R.id.img_blockUserMerchant);
            holder.imageViewUnblockUserMerchant = view.findViewById(R.id.img_unblockUserMerchant);
            holder.imageViewDeleteUserMerchant = view.findViewById(R.id.img_removeUserMerchant);
            holder.textViewStatus = view.findViewById(R.id.txtView_Status_UserMerchant);

            view.setTag(holder);
        }else{
            holder = (ViewUserMerchantHolder) view.getTag();
        }

        afterViewsUsersMerchantsData (position);

        // Block User Merchant
        holder.imageViewBlockUserMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mContext)
                                                 .setTitle("Please Make Choice !")
                                                 .setMessage("Are You Sure to Block this User Merchant ?")
                                                 .setIcon(mContext.getResources().getDrawable(R.drawable.ic_question_primary_48dp))
                                                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialogInterface, int i) {
                                                         BlockUserMerchant(position);
                                                         //notifyDataSetChanged();
                                                     }
                                                 })
                                                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialogInterface, int i) {
                                                         String msgCancelBlocking = "Blocking this user is canceled";
                                                         Toast.makeText(mContext,msgCancelBlocking,Toast.LENGTH_LONG).show();
                                                     }
                                                 })
                                                 .create()
                                                 .show();
                //setUserMerchantBlocked(position);
                //StatusBlocked();
                //afterViewsUsersMerchantsData (position);
            }
        });

        // Unblock User Merchant
        holder.imageViewUnblockUserMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Please Make Choice !")
                        .setMessage("Are You Sure to Unblock this User Merchant ?")
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_question_primary_48dp))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UnblockUserMerchant(position);
                                //notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String msgCancelBlocking = "Unblocking this user is canceled";
                                Toast.makeText(mContext,msgCancelBlocking,Toast.LENGTH_LONG).show();
                            }
                        })
                        .create()
                        .show();
               // setUserMerchantUnblocked(position);
                //StatusUnblocked();
                //afterViewsUsersMerchantsData (position);
            }
        });

        // Delete User Merchnat
        holder.imageViewDeleteUserMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                       new AlertDialog.Builder(mContext)
                                       .setTitle("Please Make Choice !")
                                       .setMessage("Are You Sure You Want To Delete this User Merchant ?")
                                       .setIcon(mContext.getResources().getDrawable(R.drawable.ic_question_primary_48dp))
                                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialogInterface, int i) {
                                               DeleteUserMerchant(position);
                                           }
                                       })
                                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialogInterface, int i) {
                                               String msgCancelDeleting = "Deleting this user is canceled";
                                               Toast.makeText(mContext,msgCancelDeleting,Toast.LENGTH_LONG).show();
                                           }
                                       })
                                       .create()
                                       .show();
            }
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void afterViewsUsersMerchantsData (int position){

        // Info Section
        holder.textViewFullNameUserMerchant.setText("Full Name : "+listUsersMerchants_Holder.get(position).getLastName()
                                                    +"  "+listUsersMerchants_Holder.get(position).getFirstName()
        );
        holder.textViewEmailUserMerchant.setText("Email : "+listUsersMerchants_Holder.get(position).getEmail());
        holder.textViewPhoneNumbUserMerchant.setText("Phone N° : "+listUsersMerchants_Holder.get(position).getPhoneNumber());

        // Other Section
        holder.textViewUserNameUserMerchant.setText("User Name : "+listUsersMerchants_Holder.get(position).getUserName());
        holder.textViewOrganizationTypeUserMerchant.setText("Organization : "+listUsersMerchants_Holder.get(position).getOrganizationTypeName());

        // Blocking Section
        if (listUsersMerchants_Holder.get(position).getIsBlocked() == 1) {
            StatusBlocked();
        }else{
            StatusUnblocked();
        }

        if (listUsersMerchants_Holder.get(position).getOrganizationTypeName().equals("Merchant")){
            holder.textViewOrganizationTypeUserMerchant.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            holder.textViewOrganizationTypeUserMerchant.setTextColor(mContext.getResources().getColor(R.color.black));
        }else{
            holder.textViewOrganizationTypeUserMerchant.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            holder.textViewOrganizationTypeUserMerchant.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        if(listUsersMerchants_Holder.get(position).getId().equals("723b5041-3819-4edc-bcd7-d190a87f2a75") //CaisseMonoprix
           || listUsersMerchants_Holder.get(position).getId().equals("a2dbad70-825f-4a6c-9bcb-2594334b491a") //MagasinMonoprix
           || listUsersMerchants_Holder.get(position).getId().equals("c5657068-cb63-4b0f-a929-32f0601d3898")  ) { //MarchantMonoprix

            int imageID = mContext.getResources().getIdentifier("monoprix", "drawable", mContext.getPackageName());
            holder.imageViewUserMerchant.setImageResource(imageID);

        }else {
            int imageID_Default = mContext.getResources().getIdentifier("my_foto_profile", "drawable", mContext.getPackageName());
            holder.imageViewUserMerchant.setImageResource(imageID_Default);
        }

    }

    private void BlockUserMerchant(int position){
        getId_ToBlock = listUsersMerchants_Holder.get(position).getId();
        getUserName_ToBlock = listUsersMerchants_Holder.get(position).getUserName();
        new BlockUserMerchantTask().execute();
        StatusBlocked();
        //notifyDataSetChanged();

        //((Activity) mContext).finish();
        //mContext.startActivity(((Activity) mContext).getIntent());
    }

    private void UnblockUserMerchant(int position){
        getId_ToUnblock = listUsersMerchants_Holder.get(position).getId();
        getUserName_ToUnblock = listUsersMerchants_Holder.get(position).getUserName();
        new UnblockUserMerchantTask().execute();
        StatusUnblocked();
        //notifyDataSetChanged();

        //((ListActivity) mContext).finish();
         //mContext.startActivity(((ListActivity) mContext).getIntent());
    }

    private void DeleteUserMerchant(int position){
        getId_ToDelete = listUsersMerchants_Holder.get(position).getId();
        getUserName_ToDelete = listUsersMerchants_Holder.get(position).getUserName();
        getPosition = position;

        new DeleteUserMerchantTask().execute();
    }

    private void StatusBlocked(){
        String statusBlocked = "User Status : Blocked";
        holder.textViewStatus.setText(statusBlocked);
        holder.textViewStatus.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        holder.textViewStatus.setTextColor(mContext.getResources().getColor(R.color.white));
    }

    private void StatusUnblocked(){
        String statusUnblocked = "User Status : Unblocked";
        holder.textViewStatus.setText(statusUnblocked);
        holder.textViewStatus.setBackgroundColor(mContext.getResources().getColor(R.color.green));
        holder.textViewStatus.setTextColor(mContext.getResources().getColor(R.color.black));
    }

    private void RefreshListViewAfterDeleteSuccess(int position){

        // Refresh List View After Deleting Item
        listUsersMerchants_Holder.remove(position);
        notifyDataSetChanged();
    }
    /*
    private void setUserMerchantBlocked(int position){
                StatusBlocked();
                notifyDataSetChanged();
    }

    private void setUserMerchantUnblocked(int position){
                StatusUnblocked();
                notifyDataSetChanged();


    }
   */
    // Partie des Classes AsyncTask

    // Class AsyncTask BlockUserMerchantTask
    class BlockUserMerchantTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... queryParam) {

            queryParam = new String[1];

            queryParam[0] = getId_ToBlock;

            try {
                blockedUser = api.BlockUserMerchant(queryParam[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return blockedUser;
        }

        @Override
        protected void onPostExecute(String s) {
            String userNameBlockedUser = getUserName_ToBlock+" ";
            Toast.makeText(mContext,userNameBlockedUser+blockedUser,Toast.LENGTH_LONG).show();
            //StatusBlocked();
            super.onPostExecute(s);

        }
    }

    // Class AsyncTask UnblockUserMerchantTask
    class  UnblockUserMerchantTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... queryParam) {

            queryParam = new String[1];

            queryParam[0] = getId_ToUnblock;

            try {
                unblockedUser = api.UnblockUserMerchant(queryParam[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return unblockedUser;
        }

        @Override
        protected void onPostExecute(String s) {
            String userNameUnblockedUser = getUserName_ToUnblock+" ";
            Toast.makeText(mContext,userNameUnblockedUser+unblockedUser,Toast.LENGTH_LONG).show();
            //StatusUnblocked();
            super.onPostExecute(s);

        }

    }

    // Class AsyncTask DeleteUserMerchantTask
    class DeleteUserMerchantTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... queryParam) {

            queryParam = new String[1];

            queryParam[0] = getId_ToDelete;

            try {
                deletedUser = api.DeleteUserMerchant(queryParam[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return deletedUser;
        }

        @Override
        protected void onPostExecute(String s) {

            if(deletedUser.equals("Delete_Succeed")){
                String msgSuccess = getUserName_ToDelete + " Has Been Deleted Successfully ! ";
                Toast.makeText(mContext,msgSuccess,Toast.LENGTH_LONG).show();
                // Refresh List View After Deleting Item
                RefreshListViewAfterDeleteSuccess(getPosition);
            }else if(deletedUser.equals("Strictly_Forbidden_Delete")){
                String msgForbidden = getUserName_ToDelete + " : It is strictly forbidden to delete main users of Monoprix !";
                Toast.makeText(mContext,msgForbidden,Toast.LENGTH_LONG).show();
            }else{
                String msgFailure = "Delete operation failed,please thabet mli7 !";
                Toast.makeText(mContext,msgFailure,Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(s);
        }
    }
}
