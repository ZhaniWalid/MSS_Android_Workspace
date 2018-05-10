package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 09/05/2018.
 */

public class ListViewTransactionsDataAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<gw_trnsct_GeneralBindingModel> List_trnsct_generalHolders;
    ViewTransactionHolder holder;

    public ListViewTransactionsDataAdapter() {
    }

    public ListViewTransactionsDataAdapter(Context context, List<gw_trnsct_GeneralBindingModel> List_trnsct_generalHolders) {
        mContext = context;
        this.List_trnsct_generalHolders = List_trnsct_generalHolders;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return List_trnsct_generalHolders.size();
    }

    @Override
    public Object getItem(int i) {
        return List_trnsct_generalHolders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //final ViewTransactionHolder holder;
        if (view == null) {
            holder = new ViewTransactionHolder();
            view = inflater.inflate(R.layout.transactions_general_data_layout, null);
            // Locate the TextViews in listview_item.xml
            //holder.name = (TextView) view.findViewById(R.id.name);

            // Find the Views in the View by Their Ids
            holder.imageViewBankOfRequest = view.findViewById(R.id.imageBankOfRequest_GeneralData);

            // Info Section
            holder.textViewIdTransaction = view.findViewById(R.id.txtView_idTransaction_GeneralData);
            holder.textViewIdMerchant = view.findViewById(R.id.txtView_idMerchant_GeneralData);
            holder.textViewIdTerminalMerchant = view.findViewById(R.id.txtView_idTerminalMerchant_GeneralData);
            holder.textViewIdHost = view.findViewById(R.id.txtView_idHost_GeneralData);

            // Price Section
            holder.textViewAmountAuthorisedNumeric = view.findViewById(R.id.txtView_amountAuthorisedNumeric_GeneralData);
            holder.textViewEtatTransaction = view.findViewById(R.id.txtView_etatTransaction_GeneralData);

            view.setTag(holder);
        } else {
            holder = (ViewTransactionHolder) view.getTag();
        }
        // Set the results into TextViews
        //holder.name.setText(arraylist.get(position).getMovieName());
        afterViews(position);
        return view;
    }

    private void afterViews(int position) {

        // Info Section
        holder.textViewIdTransaction.setText("ID Transaction : " + List_trnsct_generalHolders.get(position).getIdTransaction());
        holder.textViewIdMerchant.setText("ID Merchant : " + List_trnsct_generalHolders.get(position).getIdTerminalMerchant());
        holder.textViewIdTerminalMerchant.setText("ID Terminal Merchant : " + List_trnsct_generalHolders.get(position).getIdTerminalMerchant());
        holder.textViewIdHost.setText("ID Host : " + List_trnsct_generalHolders.get(position).getIdHost());

        // Price Section
        holder.textViewAmountAuthorisedNumeric.setText("Amount : " + List_trnsct_generalHolders.get(position).getAmountAuthorisedNumeric() + " Dinars");
        holder.textViewEtatTransaction.setText("Transaction Status : " + List_trnsct_generalHolders.get(position).getEtatTransaction());

        if (List_trnsct_generalHolders.get(position).getEtatTransaction().equals("Transaction autorisée")) {
            holder.textViewEtatTransaction.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            holder.textViewEtatTransaction.setTextColor(mContext.getResources().getColor(R.color.black));
        } else if (List_trnsct_generalHolders.get(position).getEtatTransaction().equals("Transaction non aboutie")) {
            holder.textViewEtatTransaction.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
            holder.textViewEtatTransaction.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (List_trnsct_generalHolders.get(position).getEtatTransaction().equals("Transaction non autorisée")) {
            holder.textViewEtatTransaction.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            holder.textViewEtatTransaction.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (List_trnsct_generalHolders.get(position).getEtatTransaction().equals("Transaction annulée")) {
            holder.textViewEtatTransaction.setBackgroundColor(mContext.getResources().getColor(R.color.blue_grey));
            holder.textViewEtatTransaction.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.textViewEtatTransaction.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.textViewEtatTransaction.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        // Set Image Of Bank Request
        // int imageID = context.getResources().getIdentifier("attijari_bank", "drawable", context.getPackageName());
        // imageViewBankOfRequest.setImageResource(imageID);
        //get the image associated with this property
        if (List_trnsct_generalHolders.get(position).getBankOfRequest().equals("ATTIJARI")) {
            int imageID = mContext.getResources().getIdentifier("attijari_bank", "drawable", mContext.getPackageName());
            holder.imageViewBankOfRequest.setImageResource(imageID);
        } else {
            int imageID_Default = mContext.getResources().getIdentifier("mss_logo_3", "drawable", mContext.getPackageName());
            holder.imageViewBankOfRequest.setImageResource(imageID_Default);
        }

    }


    // ViewHolder Class
    class ViewTransactionHolder {

        public ImageView imageViewBankOfRequest;
        public TextView textViewIdTransaction, textViewIdMerchant, textViewIdTerminalMerchant;
        public TextView textViewIdHost, textViewAmountAuthorisedNumeric, textViewEtatTransaction;
    }
}
