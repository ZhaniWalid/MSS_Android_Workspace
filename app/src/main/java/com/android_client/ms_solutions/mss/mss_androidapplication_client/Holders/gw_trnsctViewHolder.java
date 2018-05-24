package com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

/**
 * Created by HP on 08/05/2018.
 */

public class gw_trnsctViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageViewBankOfRequest;
    public TextView textViewIdTransaction, textViewIdMerchant, textViewIdTerminalMerchant;
    public TextView textViewIdHost, textViewAmountAuthorisedNumeric, textViewEtatTransaction;

    public gw_trnsctViewHolder(View itemView) {

        super(itemView);
        // Find the Views in the View by Their Ids
        imageViewBankOfRequest = itemView.findViewById(R.id.imageBankId_GeneralData);

        // Info Section
        textViewIdTransaction = itemView.findViewById(R.id.txtView_idTransaction_GeneralData);
        textViewIdMerchant = itemView.findViewById(R.id.txtView_idMerchant_GeneralData);
        textViewIdTerminalMerchant = itemView.findViewById(R.id.txtView_idTerminalMerchant_GeneralData);
        textViewIdHost = itemView.findViewById(R.id.txtView_idHost_GeneralData);

        // Price Section
        textViewAmountAuthorisedNumeric = itemView.findViewById(R.id.txtView_amountAuthorisedNumeric_GeneralData);
        textViewEtatTransaction = itemView.findViewById(R.id.txtView_etatTransaction_GeneralData);
    }
}
