package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 07/05/2018.
 */

public class GeneralTransactionsDataAdapter extends ArrayAdapter {

    private Context context;
    private List<gw_trnsct_GeneralBindingModel> trnsct_generalHolders = new ArrayList<gw_trnsct_GeneralBindingModel>();
    //private List<gw_trnsct_GeneralBindingModel> trnsct_generalHolders_Filtered = new ArrayList<gw_trnsct_GeneralBindingModel>();
    private gw_trnsct_GeneralBindingModel gw_trnsct_generalBindingModel;

    private ImageView imageViewBankOfRequest;
    private TextView textViewIdTransaction, textViewIdMerchant, textViewIdTerminalMerchant;
    private TextView textViewIdHost, textViewAmountAuthorisedNumeric, textViewEtatTransaction;

    //constructor, call on creation
    public GeneralTransactionsDataAdapter(Context context, int resource, List<gw_trnsct_GeneralBindingModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.trnsct_generalHolders = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        gw_trnsct_generalBindingModel = trnsct_generalHolders.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.transactions_general_data_layout, null);

        // Find the Views in the View by Their Ids
        imageViewBankOfRequest = view.findViewById(R.id.imageBankOfRequest_GeneralData);

        // Info Section
        textViewIdTransaction = view.findViewById(R.id.txtView_idTransaction_GeneralData);
        textViewIdMerchant = view.findViewById(R.id.txtView_idMerchant_GeneralData);
        textViewIdTerminalMerchant = view.findViewById(R.id.txtView_idTerminalMerchant_GeneralData);
        textViewIdHost = view.findViewById(R.id.txtView_idHost_GeneralData);

        // Price Section
        textViewAmountAuthorisedNumeric = view.findViewById(R.id.txtView_amountAuthorisedNumeric_GeneralData);
        textViewEtatTransaction = view.findViewById(R.id.txtView_etatTransaction_GeneralData);

        afterViews();
        return view;
    }

    private void afterViews() {

        // Info Section
        textViewIdTransaction.setText("ID Transaction : " + gw_trnsct_generalBindingModel.getIdTransaction());
        textViewIdMerchant.setText("ID Merchant : " + gw_trnsct_generalBindingModel.getIdTerminalMerchant());
        textViewIdTerminalMerchant.setText("ID Terminal Merchant : " + gw_trnsct_generalBindingModel.getIdTerminalMerchant());
        textViewIdHost.setText("ID Host : " + gw_trnsct_generalBindingModel.getIdHost());

        // Price Section
        textViewAmountAuthorisedNumeric.setText("Amount : " + gw_trnsct_generalBindingModel.getAmountAuthorisedNumeric() + " Dinars");
        textViewEtatTransaction.setText("Transaction Status : " + gw_trnsct_generalBindingModel.getEtatTransaction());

        if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction autorisée")) {
            textViewEtatTransaction.setBackgroundColor(this.getContext().getResources().getColor(R.color.green));
            textViewEtatTransaction.setTextColor(this.getContext().getResources().getColor(R.color.black));
        } else if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction non aboutie")) {
            textViewEtatTransaction.setBackgroundColor(this.getContext().getResources().getColor(R.color.orange));
            textViewEtatTransaction.setTextColor(this.getContext().getResources().getColor(R.color.white));
        } else if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction non autorisée")) {
            textViewEtatTransaction.setBackgroundColor(this.getContext().getResources().getColor(R.color.red));
            textViewEtatTransaction.setTextColor(this.getContext().getResources().getColor(R.color.white));
        } else if (gw_trnsct_generalBindingModel.getEtatTransaction().equals("Transaction annulée")) {
            textViewEtatTransaction.setBackgroundColor(this.getContext().getResources().getColor(R.color.blue_grey));
            textViewEtatTransaction.setTextColor(this.getContext().getResources().getColor(R.color.white));
        } else {
            textViewEtatTransaction.setBackgroundColor(this.getContext().getResources().getColor(R.color.white));
            textViewEtatTransaction.setTextColor(this.getContext().getResources().getColor(R.color.red));
        }

        // Set Image Of Bank Request
        // int imageID = context.getResources().getIdentifier("attijari_bank", "drawable", context.getPackageName());
        // imageViewBankOfRequest.setImageResource(imageID);
        //get the image associated with this property
        if (gw_trnsct_generalBindingModel.getBankOfRequest().equals("ATTIJARI")) {
            int imageID = context.getResources().getIdentifier("attijari_bank", "drawable", context.getPackageName());
            imageViewBankOfRequest.setImageResource(imageID);
        } else {
            int imageID_Default = context.getResources().getIdentifier("mss_logo_3", "drawable", context.getPackageName());
            imageViewBankOfRequest.setImageResource(imageID_Default);
        }

    }

}
