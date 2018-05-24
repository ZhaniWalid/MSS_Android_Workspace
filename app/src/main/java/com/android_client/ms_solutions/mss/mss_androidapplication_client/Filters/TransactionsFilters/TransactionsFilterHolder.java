package com.android_client.ms_solutions.mss.mss_androidapplication_client.Filters.TransactionsFilters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 08/05/2018.
 */

public class TransactionsFilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageViewBankOfRequest;
    public TextView textViewIdTransaction, textViewIdMerchant, textViewIdTerminalMerchant;
    public TextView textViewIdHost, textViewAmountAuthorisedNumeric, textViewEtatTransaction;

    public ItemClickListener itemClickListener;


    public TransactionsFilterHolder(View itemView) {
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
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener=ic;
    }
}
