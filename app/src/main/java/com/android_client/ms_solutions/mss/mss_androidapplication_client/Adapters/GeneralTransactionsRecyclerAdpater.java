package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.gw_trnsctViewHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 08/05/2018.
 */

public class GeneralTransactionsRecyclerAdpater extends RecyclerView.Adapter<gw_trnsctViewHolder> {

    private Context context;
    public List<gw_trnsct_GeneralBindingModel> list_trnsct_general;
    private gw_trnsct_GeneralBindingModel gw_trnsct_generalBindingModel;

    SampleApi api = SampleApiFactory.getInstance();

    public GeneralTransactionsRecyclerAdpater() {
    }

    public GeneralTransactionsRecyclerAdpater(Context context, List<gw_trnsct_GeneralBindingModel> list_trnsct_general) {
        this.context = context;
        this.list_trnsct_general = list_trnsct_general;
    }

    @Override
    public gw_trnsctViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_general_data_layout,parent,false);
        loadData();

        return new gw_trnsctViewHolder(view);
    }

    @Override
    public void onBindViewHolder(gw_trnsctViewHolder holder, int position) {
        final gw_trnsct_GeneralBindingModel singlegw_trnsct_GeneralBindingModel = list_trnsct_general.get(position);

        afterViews(holder,singlegw_trnsct_GeneralBindingModel);
    }

    @Override
    public int getItemCount() {
        return list_trnsct_general.size();
    }

    private void afterViews(gw_trnsctViewHolder holder,gw_trnsct_GeneralBindingModel gw_trnsct_generalBindingModel) {

        // Info Section
        holder.textViewIdTransaction.setText("ID Transaction : " + gw_trnsct_generalBindingModel.getIdTransaction());
        holder.textViewIdMerchant.setText("ID Merchant : " + gw_trnsct_generalBindingModel.getIdTerminalMerchant());
        holder.textViewIdTerminalMerchant.setText("ID Terminal Merchant : " + gw_trnsct_generalBindingModel.getIdTerminalMerchant());
        holder.textViewIdHost.setText("ID Host : " + gw_trnsct_generalBindingModel.getIdHost());

        // Price Section
        holder.textViewAmountAuthorisedNumeric.setText("Amount : " + String.valueOf(gw_trnsct_generalBindingModel.getAmount()) + " Dinars");
        holder.textViewEtatTransaction.setText("Transaction Status : " + gw_trnsct_generalBindingModel.getEtatTransaction());

        if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction autorisée")) {
            holder.textViewEtatTransaction.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.textViewEtatTransaction.setTextColor(context.getResources().getColor(R.color.black));
        } else if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction non aboutie")) {
            holder.textViewEtatTransaction.setBackgroundColor(context.getResources().getColor(R.color.orange));
            holder.textViewEtatTransaction.setTextColor(context.getResources().getColor(R.color.white));
        } else if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction non autorisée")) {
            holder.textViewEtatTransaction.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.textViewEtatTransaction.setTextColor(context.getResources().getColor(R.color.white));
        } else if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction annulée")) {
            holder.textViewEtatTransaction.setBackgroundColor(context.getResources().getColor(R.color.blue_grey));
            holder.textViewEtatTransaction.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.textViewEtatTransaction.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.textViewEtatTransaction.setTextColor(context.getResources().getColor(R.color.red));
        }

        // Set Image Of Bank Request
        // int imageID = context.getResources().getIdentifier("attijari_bank", "drawable", context.getPackageName());
        // imageViewBankOfRequest.setImageResource(imageID);
        //get the image associated with this property
        if (gw_trnsct_generalBindingModel.getBankOfRequest().equals("ATTIJARI")) {
            int imageID = context.getResources().getIdentifier("attijari_bank", "drawable", context.getPackageName());
            holder.imageViewBankOfRequest.setImageResource(imageID);
        } else {
            int imageID_Default = context.getResources().getIdentifier("mss_logo_3", "drawable", context.getPackageName());
            holder.imageViewBankOfRequest.setImageResource(imageID_Default);
        }

    }

      private void loadData(){
        new TransactionsGeneralDataForFiltringTask().execute();
    }
    // Class AsyncTask
    /// Class : TransactionsGeneralDataForFiltringTask
    class TransactionsGeneralDataForFiltringTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            // Used for filtring in RecyclerView
            try {
                list_trnsct_general = api.GetGeneralTransactionsData().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
