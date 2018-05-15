package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.ViewUserMerchantHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.AspNetUserBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 14/05/2018.
 */

public class ListViewUsersMerchantsAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    //SampleApi api = SampleApiFactory.getInstance();
    private List<AspNetUserBindingModel> listUsersMerchants_Holder;

    ViewUserMerchantHolder holder;

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

            view.setTag(holder);
        }else{
            holder = (ViewUserMerchantHolder) view.getTag();
        }

        afterViewsUsersMerchantsData (position);

        return view;
    }

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

        if (listUsersMerchants_Holder.get(position).getOrganizationTypeName().equals("Merchant")){
            holder.textViewOrganizationTypeUserMerchant.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            holder.textViewOrganizationTypeUserMerchant.setTextColor(mContext.getResources().getColor(R.color.black));
        }else{
            holder.textViewOrganizationTypeUserMerchant.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            holder.textViewOrganizationTypeUserMerchant.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        if(listUsersMerchants_Holder.get(position).getUserName().equals("AdminMonoprix")){
            int imageID = mContext.getResources().getIdentifier("my_foto_profile", "drawable", mContext.getPackageName());
            holder.imageViewUserMerchant.setImageResource(imageID);
        }else {
            int imageID_Default = mContext.getResources().getIdentifier("monoprix", "drawable", mContext.getPackageName());
            holder.imageViewUserMerchant.setImageResource(imageID_Default);
        }

    }
}
