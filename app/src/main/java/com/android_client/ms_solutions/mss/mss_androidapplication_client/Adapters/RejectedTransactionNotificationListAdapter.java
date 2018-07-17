package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.NotificationsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.RejectedTransactionNotifcationViewHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_StatusCodeBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.Inflater;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 29/06/2018.
 */

public class RejectedTransactionNotificationListAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    private List<gw_StatusCodeBindingModel> List_rejectedTransNotif_holder;
    //private List<gw_trnsct_ExtendedBindingModel> List_trnsct_extendedHolders;
    //SampleApi api = SampleApiFactory.getInstance();
    //private int size_listTrnscExtended = 0;


    public RejectedTransactionNotificationListAdapter(Context context, List<gw_StatusCodeBindingModel> list_rejectedTransNotif_holder) {
        mContext = context;
        List_rejectedTransNotif_holder = list_rejectedTransNotif_holder;
        inflater = LayoutInflater.from(mContext);
    }

    RejectedTransactionNotifcationViewHolder holder;

    @Override
    public int getCount() {
        return List_rejectedTransNotif_holder.size();
    }

    @Override
    public Object getItem(int i) {
        return List_rejectedTransNotif_holder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final gw_StatusCodeBindingModel gwStatusCodeBindingModel = List_rejectedTransNotif_holder.get(position);

        if(view == null){
            holder = new RejectedTransactionNotifcationViewHolder();
            view = inflater.inflate(R.layout.rejected_transaction_notification_layout, null);

            holder.imageViewRejectedTrnsNotif = view.findViewById(R.id.imageRejectedTrnsNotif);
            holder.textViewTimeOfRejectionTrns = view.findViewById(R.id.txtView_TimeOfRejectionTrns);
            holder.textViewidTransaction_NotifRejecTrns = view.findViewById(R.id.txtView_idTransaction_NotifRejecTrns);
            holder.textViewDescriptionOfRejectionTrns = view.findViewById(R.id.txtView_DescriptionOfRejectionTrns);

            view.setTag(holder);

        }else{
            holder = (RejectedTransactionNotifcationViewHolder) view.getTag();
        }

        afterViewRejectedTransactionsNotification(position);

        NotificationsFragment.listView_RejectedTransNotif.setLongClickable(true); // in the listView in the XML Layout we should add also: android:longClickable="true"
        NotificationsFragment.listView_RejectedTransNotif.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(mContext,"No Extra Information Yet For : "+position,Toast.LENGTH_LONG).show();
                afterViewsDetailsAboutRejectedTransNoti(gwStatusCodeBindingModel);
                return true;
            }
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void afterViewRejectedTransactionsNotification(int position){

        // Info Section
        String dateTimeRejectedTransNotif = List_rejectedTransNotif_holder.get(position).getTransactionDate().toString(); //return : 2017-04-23T00:00:00+01:00 => Sun Apr 23 00:00:00 CET 2017

        String dateTimeRejectedTransNotif_Right = dateTimeRejectedTransNotif.substring(0,10); // de 0 -> 10 : Sun Apr 23 (a)
        String dateTimeRejectedTransNotif_Left  = dateTimeRejectedTransNotif.substring(dateTimeRejectedTransNotif.length()-4); // de dateTimeRejectedTransNotif.length() -> 4 pas en arriere : 2017 (b) => Get the last 4 Caracters
        String dateTimeRejectedTransNotif_Exact = dateTimeRejectedTransNotif_Right + " " + dateTimeRejectedTransNotif_Left; // Result : (a) + (b) =  Sun Apr 23 2017 (c'est un exemple pour comprendre)

       /* @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC+1"));
        Date mydate = new Date();
        try {
            mydate = formatter.parse(dateTimeRejectedTransNotif);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myDateToString = mydate.toString(); */

        holder.textViewTimeOfRejectionTrns.setText(dateTimeRejectedTransNotif_Exact);
        holder.textViewidTransaction_NotifRejecTrns.setText("Id Of Rejected Transaction : "+List_rejectedTransNotif_holder.get(position).getTransactionId());

        // Description Section
        holder.textViewDescriptionOfRejectionTrns.setText("Description : "+List_rejectedTransNotif_holder.get(position).getCodeStatusDescription());

        // Image View Section
        int imageID = mContext.getResources().getIdentifier("rejected_6", "drawable", mContext.getPackageName());
        holder.imageViewRejectedTrnsNotif.setImageResource(imageID);
    }

    @SuppressLint("SetTextI18n")
    private void afterViewsDetailsAboutRejectedTransNoti(gw_StatusCodeBindingModel gwStatusCodeBindingModel){

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.rejected_transaction_notification_extended_layout, null);

        final TextView txtViewRejected_TrnsType = subView.findViewById(R.id.txtView_Rejected_TrnsType);
        final TextView txtViewRejected_TrnsCardPayment = subView.findViewById(R.id.txtView_Rejected_TrnsCardPayment);
        final TextView txtViewRejected_TrnsBankOfRequest = subView.findViewById(R.id.txtView_Rejected_TrnsBankOfRequest);
        final TextView txtViewRejected_TrnsBankName = subView.findViewById(R.id.txtView_Rejected_TrnsBankName);
        final TextView txtViewRejected_TrnsAmount = subView.findViewById(R.id.txtView_Rejected_TrnsAmount);

        txtViewRejected_TrnsType.setText("Transaction Type : "+gwStatusCodeBindingModel.getTransactionType());
        txtViewRejected_TrnsCardPayment.setText("Payement Method : "+gwStatusCodeBindingModel.getCardOfPayement());
        txtViewRejected_TrnsBankOfRequest.setText("Bank Of Request : "+gwStatusCodeBindingModel.getBankOfRequest());
        txtViewRejected_TrnsBankName.setText("Bank Name : "+gwStatusCodeBindingModel.getBankNameGateWay());
        txtViewRejected_TrnsAmount.setText("Amount : "+gwStatusCodeBindingModel.getAmount()+" TND");

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Rejected Transaction Extra Details");
        builder.setIcon(mContext.getResources().getDrawable(R.drawable.ic_card_membership_black_24dp));
        builder.setView(subView);
        builder.create();
        builder.show();
    }
}
