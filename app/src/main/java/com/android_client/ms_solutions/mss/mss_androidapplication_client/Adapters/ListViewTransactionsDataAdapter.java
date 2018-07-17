package com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Holders.ViewTransactionHolder;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 09/05/2018.
 */

public class ListViewTransactionsDataAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<gw_trnsct_GeneralBindingModel> List_trnsct_generalHolders;
    //private List<gw_trnsct_ExtendedBindingModel> List_trnsct_extendedHolders;
    //SampleApi api = SampleApiFactory.getInstance();
    //private int size_listTrnscExtended = 0;

    ViewTransactionHolder holder;

    public ListViewTransactionsDataAdapter() {
    }

    /*
    public ListViewTransactionsDataAdapter(List<gw_trnsct_ExtendedBindingModel> List_trnsct_extendedHolders,Context context) {
        mContext = context;
        this.List_trnsct_extendedHolders = List_trnsct_extendedHolders;
        inflater = LayoutInflater.from(mContext);
    }
    */

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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        //final ViewTransactionHolder holder;
        final gw_trnsct_GeneralBindingModel gw_trnsct_generalBindingModel = List_trnsct_generalHolders.get(position);
        //final gw_trnsct_ExtendedBindingModel gw_trnsct_extendedBindingModel = new gw_trnsct_ExtendedBindingModel();
        //gw_trnsct_extendedBindingModel.setIdTransaction(gw_trnsct_generalBindingModel.getIdTransaction());

        if (view == null) {
            holder = new ViewTransactionHolder();
            view = inflater.inflate(R.layout.transactions_general_data_layout, null);
            // Locate the TextViews in listview_item.xml
            //holder.name = (TextView) view.findViewById(R.id.name);

            // Find the Views in the View by Their Ids
            holder.imageViewBankId = view.findViewById(R.id.imageBankId_GeneralData);

            // Info Section
            holder.textViewIdTransaction = view.findViewById(R.id.txtView_idTransaction_GeneralData);
            holder.textViewIdMerchant = view.findViewById(R.id.txtView_idMerchant_GeneralData);
            holder.textViewIdTerminalMerchant = view.findViewById(R.id.txtView_idTerminalMerchant_GeneralData);
            holder.textViewIdHost = view.findViewById(R.id.txtView_idHost_GeneralData);

            // Price Section
            holder.textViewAmountAuthorisedNumeric = view.findViewById(R.id.txtView_amountAuthorisedNumeric_GeneralData);
            holder.textViewEtatTransaction = view.findViewById(R.id.txtView_etatTransaction_GeneralData);

            // Get Extended Transaction Data on Pressing the Floating Action Button
            //holder.fabGetExtendedTransactionsData = view.findViewById(R.id.fab_getExtendedTransactionData);
            holder.imgGetExtendedTransaction = view.findViewById(R.id.img_getExtendedTransactionData);
            holder.imgGetTicket = view.findViewById(R.id.img_getTicket);

            // Bankin Section
            holder.textViewBankName = view.findViewById(R.id.txtView_BankName_GeneralData);
            holder.textViewBankOfRequest = view.findViewById(R.id.txtView_BankOfRequest_GeneralData);

            view.setTag(holder);
        } else {
            holder = (ViewTransactionHolder) view.getTag();
        }
        // Set the results into TextViews
        //holder.name.setText(arraylist.get(position).getMovieName());
        afterViewsGeneralTransactionsData(position);

      /*  loadExtendedTransactionsData();

        holder.fabGetExtendedTransactionsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterViewsExtendedTransactionsDATA(position);
            }
        }); */

          holder.imgGetExtendedTransaction.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                      afterViewsExtendedTransactionsDATA(gw_trnsct_generalBindingModel);

              }
          });

          holder.imgGetTicket.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  afterViewsGetTicket(gw_trnsct_generalBindingModel);
              }
          });

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void afterViewsGeneralTransactionsData(int position) {

        // Info Section
        holder.textViewIdTransaction.setText("ID Transaction : " + List_trnsct_generalHolders.get(position).getIdTransaction());
        holder.textViewIdMerchant.setText("ID Merchant : " + List_trnsct_generalHolders.get(position).getIdTerminalMerchant());
        holder.textViewIdTerminalMerchant.setText("ID Terminal Merchant : " + List_trnsct_generalHolders.get(position).getIdTerminalMerchant());
        holder.textViewIdHost.setText("ID Host : " + List_trnsct_generalHolders.get(position).getIdHost());

        // Price Section
        holder.textViewAmountAuthorisedNumeric.setText("Amount : " + String.valueOf(List_trnsct_generalHolders.get(position).getAmount()) + " Dinars");
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

        // Banking Section
        holder.textViewBankName.setText("Bank Name : "+List_trnsct_generalHolders.get(position).getBankName_GateWay());
        holder.textViewBankOfRequest.setText("Bank Of Request : "+List_trnsct_generalHolders.get(position).getBankOfRequest());

        // Set Image Of Bank Request
        // int imageID = context.getResources().getIdentifier("attijari_bank", "drawable", context.getPackageName());
        // imageViewBankId.setImageResource(imageID);
        //get the image associated with this property

       /*
        if (List_trnsct_generalHolders.get(position).getBankOfRequest().equals("ATTIJARI")) {
            int imageID = mContext.getResources().getIdentifier("attijari_bank", "drawable", mContext.getPackageName());
            holder.imageViewBankId.setImageResource(imageID);
        } else {
            int imageID_Default = mContext.getResources().getIdentifier("mss_logo_3", "drawable", mContext.getPackageName());
            holder.imageViewBankId.setImageResource(imageID_Default);
        }
        */

        String bankIdValue = List_trnsct_generalHolders.get(position).getBankId_GateWay();
        int imageID;

        switch (bankIdValue){

            case "AB":
                imageID = mContext.getResources().getIdentifier("amen_bank", "drawable", mContext.getPackageName());
            break;

            case "ABC":
                imageID = mContext.getResources().getIdentifier("abc_bank", "drawable", mContext.getPackageName());
            break;

            case "AlBaraka":
                imageID = mContext.getResources().getIdentifier("albaraka_bank", "drawable", mContext.getPackageName());
            break;

            case "ATB":
                imageID = mContext.getResources().getIdentifier("atb_bank", "drawable", mContext.getPackageName());
            break;

            case "ATTIJARI":
                imageID = mContext.getResources().getIdentifier("attijari_bank", "drawable", mContext.getPackageName());
            break;

            case "BCT":
                imageID = mContext.getResources().getIdentifier("bct_bank", "drawable", mContext.getPackageName());
            break;

            case "BFT":
                imageID = mContext.getResources().getIdentifier("bft_bank", "drawable", mContext.getPackageName());
            break;

            case "BH":
                imageID = mContext.getResources().getIdentifier("habitat_bank", "drawable", mContext.getPackageName());
            break;

            case "BIAT":
                imageID = mContext.getResources().getIdentifier("biat_bank", "drawable", mContext.getPackageName());
            break;

            case "BNA":
                imageID = mContext.getResources().getIdentifier("bna_bank", "drawable", mContext.getPackageName());
            break;

            case "BT":
                imageID = mContext.getResources().getIdentifier("bt_bank", "drawable", mContext.getPackageName());
            break;

            case "BTE":
                imageID = mContext.getResources().getIdentifier("bte_bank", "drawable", mContext.getPackageName());
            break;

            case "BTK":
                imageID = mContext.getResources().getIdentifier("btk_bank", "drawable", mContext.getPackageName());
            break;

            case "BTL":
                imageID = mContext.getResources().getIdentifier("btl_bank", "drawable", mContext.getPackageName());
            break;

            case "NAIB":
                imageID = mContext.getResources().getIdentifier("naib_bank", "drawable", mContext.getPackageName());
            break;

            case "ONP":
                imageID = mContext.getResources().getIdentifier("onp_bank", "drawable", mContext.getPackageName());
            break;

            case "STB":
                imageID = mContext.getResources().getIdentifier("stb_bank", "drawable", mContext.getPackageName());
            break;

            case "Stusid":
                imageID = mContext.getResources().getIdentifier("stusid_bank", "drawable", mContext.getPackageName());
            break;

            case "TIB":
                imageID = mContext.getResources().getIdentifier("tib_bank", "drawable", mContext.getPackageName());
            break;

            case "TQB":
                imageID = mContext.getResources().getIdentifier("tqb_bank", "drawable", mContext.getPackageName());
            break;

            case "UBCI":
                imageID = mContext.getResources().getIdentifier("ubci_bank", "drawable", mContext.getPackageName());
            break;

            case "UIB":
                imageID = mContext.getResources().getIdentifier("uib_bank", "drawable", mContext.getPackageName());
            break;

            case "Zitouna":
                imageID = mContext.getResources().getIdentifier("zitouna_bank", "drawable", mContext.getPackageName());
            break;

            case "---":
                imageID = mContext.getResources().getIdentifier("mss_logo_3", "drawable", mContext.getPackageName());
            break;

            default:
                imageID = mContext.getResources().getIdentifier("mss_logo_3", "drawable", mContext.getPackageName());
             break;
        }

        holder.imageViewBankId.setImageResource(imageID);

    }

    @SuppressLint("SetTextI18n")
    private void afterViewsExtendedTransactionsDATA(gw_trnsct_GeneralBindingModel gw_trnsct_generalBindingModel){

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.transaction_extended_view, null);

        final TextView txtViewEtatCloture = subView.findViewById(R.id.txtView_EtatCloture_ExtendedTrnsData);
        final TextView txtViewCurrentTransactionDate = subView.findViewById(R.id.txtView_CurrentDate_ExtendedTrnsData);
        final TextView txtViewTimeSysTransaction = subView.findViewById(R.id.txtView_TimeSystemTransaction_ExtendedTrnsData);
        final TextView txtViewTransactionType = subView.findViewById(R.id.txtView_Transactiontype_ExtendedTrnsData);
        final TextView txtViewResponseCode = subView.findViewById(R.id.txtView_ResponseCode_ExtendedTrnsData);
        final TextView txtViewApprovalCode = subView.findViewById(R.id.txtView_ApprovalCode_ExtendedTrnsData);
        final TextView txtViewCardMask_Pan = subView.findViewById(R.id.txtView_CardMask_ExtendedTrnsData);

        txtViewEtatCloture.setText("Cloture Status : "+gw_trnsct_generalBindingModel.getEtatCloture());

        if (gw_trnsct_generalBindingModel.getEtatCloture().equals("non_cloturée")){
            txtViewEtatCloture.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            txtViewEtatCloture.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            txtViewEtatCloture.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            txtViewEtatCloture.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        txtViewCurrentTransactionDate.setText("Transaction Date : "+gw_trnsct_generalBindingModel.getCurrentDate());
        txtViewTimeSysTransaction.setText("Time System Transaction : "+gw_trnsct_generalBindingModel.getTimeSystemTransaction());
        txtViewTransactionType.setText("Transaction Type : "+gw_trnsct_generalBindingModel.getTransactiontype());
        txtViewResponseCode.setText("Response Code : "+gw_trnsct_generalBindingModel.getResponseCode());
        txtViewApprovalCode.setText("Approval Code : "+gw_trnsct_generalBindingModel.getFID_F_ApprovalCode());
        txtViewCardMask_Pan.setText("Pan Card Mask : "+gw_trnsct_generalBindingModel.getCardMask());

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Transaction Informations");
        builder.setIcon(mContext.getResources().getDrawable(R.drawable.ic_euro_symbol_black_24dp));
        builder.setView(subView);
        builder.create();
        builder.show();

    }

    @SuppressLint("SetTextI18n")
    private void afterViewsGetTicket(gw_trnsct_GeneralBindingModel gw_trnsct_generalBindingModel){

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.ticket_transaction, null);

        final TextView txtViewCardMask_Pan_Ticket = subView.findViewById(R.id.txtView_CardMask_Ticket);
        final TextView txtViewApprovalCode_Ticket = subView.findViewById(R.id.txtView_ApprovalCode_Ticket);
        final TextView txtViewAmount_Ticket = subView.findViewById(R.id.txtView_Amount_Ticket);
        final TextView txtViewCurrentTransactionDate_Ticket = subView.findViewById(R.id.txtView_CurrentDate_Ticket);
        final TextView txtView_APPID_Ticket = subView.findViewById(R.id.txtView_APPID_Ticket);
        final TextView txtViewSignAppCryp_Ticket = subView.findViewById(R.id.txtView_App_Cryp_Sign_Ticket);
        final TextView txtViewTVR_Ticket = subView.findViewById(R.id.txtView_Term_Verif_Res_Ticket);
        final TextView txtViewTSI_Ticket = subView.findViewById(R.id.txtView_Trans_Status_Info_Ticket);
        final TextView txtViewCard4Payemnt_Ticket = subView.findViewById(R.id.txtView_Card_4_Payemnt_Ticket);
        final TextView txtViewTransactionType_Ticket = subView.findViewById(R.id.txtView_Transaction_Type_Ticket);

        txtViewCardMask_Pan_Ticket.setText("Pan : "+gw_trnsct_generalBindingModel.getCardMask());
        txtViewApprovalCode_Ticket.setText("Approval Code : "+gw_trnsct_generalBindingModel.getFID_F_ApprovalCode());
        txtViewAmount_Ticket.setText("Amount : "+String.valueOf(gw_trnsct_generalBindingModel.getAmount())+" TND");
        txtViewCurrentTransactionDate_Ticket.setText("Transaction Date : "+gw_trnsct_generalBindingModel.getCurrentDate());
        txtView_APPID_Ticket.setText("APPID : "+gw_trnsct_generalBindingModel.getApplicationIdentifierCard());
        txtViewSignAppCryp_Ticket.setText("Cryptogram Sign : "+gw_trnsct_generalBindingModel.getApplicationCryptogram());
        txtViewTVR_Ticket.setText("TVR : "+gw_trnsct_generalBindingModel.getTerminalVerificationResults());
        txtViewTSI_Ticket.setText("TSI : "+gw_trnsct_generalBindingModel.getTransactionStatusInformation());
        txtViewCard4Payemnt_Ticket.setText("Payement Method : "+gw_trnsct_generalBindingModel.getCardUsedForPayement());
        txtViewTransactionType_Ticket.setText("Transaction Type : "+gw_trnsct_generalBindingModel.getTransactiontype());

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Ticket De Caisse");
        builder.setIcon(mContext.getResources().getDrawable(R.drawable.ic_euro_symbol_black_24dp));
        builder.setView(subView);
        builder.create();
        builder.show();

    }
}
