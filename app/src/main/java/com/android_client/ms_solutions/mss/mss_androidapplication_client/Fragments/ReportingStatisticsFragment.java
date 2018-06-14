package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.ListViewReportingAdapter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportingStatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportingStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportingStatisticsFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<String> List_ReportingTypeNames;
    private ListViewReportingAdapter adapter;
    private ListView listView_ReportingTypeNames;

    // Transactions Status Reports Choices
    private RadioButton radioBtnTrnsStatChoice,radioBtnMerchantTypeChoice,radioBtnCardBinChoice,radioBtnBankNameChoice;
    private RadioButton radioBtnPieChartTrnsStat,radioBtnBarChartTrnsStat;
    private RadioButton radioBtnPieChartMerchantType,radioBtnBarChartMerchantType;
    private RadioButton radioBtnPieChartCardBin,radioBtnBarChartCardBin;
    private RadioButton radioBtnPieChartBankName,radioBtnBarChartBankName;
    private RadioGroup radioGrpTrnsStat,radioGrpMerchantType,radioGrpCardBin,radioGrpBankName;
    //private Button btnDisplayReportTrnsStat;
    private int selected_radioBtnTrnsStat_Id = 0,selected_radioBtnMerchantType_Id = 0,selected_radioBtnCardBin_Id = 0,selected_radioBtnBankName_Id = 0;

    public ReportingStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportingStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportingStatisticsFragment newInstance(String param1, String param2) {
        ReportingStatisticsFragment fragment = new ReportingStatisticsFragment();
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
        View view=  inflater.inflate(R.layout.fragment_reportings_statistics, container, false);

        listView_ReportingTypeNames = view.findViewById(R.id.ListView_reporting);

        // initialize the utilities
        Utils.init(ReportingStatisticsFragment.this.getContext());

        List_ReportingTypeNames = new ArrayList<>();
        List_ReportingTypeNames.add("Statistics Reports of Transactions Status");
        List_ReportingTypeNames.add("Statistics Reports of Merchant Types");
        List_ReportingTypeNames.add("Statistics Reports of Card Bins");
        List_ReportingTypeNames.add("Statistics Reports of Bank Names For Payment");

        adapter = new ListViewReportingAdapter(ReportingStatisticsFragment.this.getContext(),List_ReportingTypeNames);
        listView_ReportingTypeNames.setAdapter(adapter);
        listView_ReportingTypeNames.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        setHasOptionsMenu(true);
        super.setHasOptionsMenu(hasMenu);
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
   /*
      if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getPieChartTranStatusFrag(){
        Fragment frag = new PieChartTransactionStatusFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getBarChartTranStatusFrag(){
        Fragment frag = new BarChartTransactionStatusFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getPieChartMerchantTypeFrag(){
        Fragment frag = new PieChartMerchantTypeFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getBarChartMerchantTypeFrag(){
        Fragment frag = new BarChartMerchantTypeFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getPieChartCardBinFrag(){
        Fragment frag = new PieChartCardBinLabelsFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getHorizontalBarChartCardBinFrag(){
        Fragment frag = new HorizontalBarChartCardBinLabelsFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getPieChartBankNamesOfPaymentFrag(){
        Fragment frag = new PieChartBankNamesFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void getBubbleChartBankNamesOfPaymentFrag(){
        Fragment frag = new BarChartBankNamesFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentReportsContainer, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        //ft.hide(this);
        //ft.show(frag);
        //ft.commit();

    }

    private void makeTransactionsStatusChoice(){

        //boolean checked = ((RadioButton) view).isChecked();
        //String str="";
        if (radioBtnPieChartTrnsStat.isChecked()){
            String msg1 = "Going To Pie Chart Report Transactions Status";
            Toast.makeText(ReportingStatisticsFragment.this.getContext(), msg1, Toast.LENGTH_SHORT).show();
            getPieChartTranStatusFrag();
        }else if(radioBtnBarChartTrnsStat.isChecked()){
            String msg2 = "Going To Bar Chart Report Transactions Status";
            Toast.makeText(ReportingStatisticsFragment.this.getContext(), msg2, Toast.LENGTH_SHORT).show();
            getBarChartTranStatusFrag();
        }else{
            String msg3 = "No Choice";
            Toast.makeText(ReportingStatisticsFragment.this.getContext(), msg3, Toast.LENGTH_SHORT).show();
        }
    }

    private void makeMerchantTypeChoice(){

        if(radioBtnPieChartMerchantType.isChecked()){
            getPieChartMerchantTypeFrag();
            Toast.makeText(ReportingStatisticsFragment.this.getContext(), "Going To Pie Chart Report Merchant Types", Toast.LENGTH_SHORT).show();
        }else if (radioBtnBarChartMerchantType.isChecked()){
            getBarChartMerchantTypeFrag();
            Toast.makeText(ReportingStatisticsFragment.this.getContext(), "Going To Bar Chart Report Merchant Types", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeCardBinChoice(){

        if (radioBtnPieChartCardBin.isChecked()){
            getPieChartCardBinFrag();
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Going To Pie Chart Report Card Bin", Toast.LENGTH_SHORT).show();
        }else if (radioBtnBarChartCardBin.isChecked()){
            getHorizontalBarChartCardBinFrag();
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Going To Horizontal Bar Chart Report Card Bin", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeBankNamesOfPaymentChoice(){

        if (radioBtnPieChartBankName.isChecked()){
            getPieChartBankNamesOfPaymentFrag();
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Going To Pie Chart Report Bank Names Of Payment", Toast.LENGTH_SHORT).show();
        }else if (radioBtnBarChartBankName.isChecked()){
            getBubbleChartBankNamesOfPaymentFrag();
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Going To Bubble Chart Report Bank Names Of Payment", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice", Toast.LENGTH_SHORT).show();
        }
    }

    private void TransactionsStatusChoices(){
        LayoutInflater inflater = LayoutInflater.from(ReportingStatisticsFragment.this.getContext());
        View subView = inflater.inflate(R.layout.transactions_status_charts_choices, null);

        radioGrpTrnsStat = subView.findViewById(R.id.radioGrpChartsTrnsStatChoices);
        radioBtnPieChartTrnsStat = subView.findViewById(R.id.radioPieChartTrnsStat);
        radioBtnBarChartTrnsStat = subView.findViewById(R.id.radioBarChartTrnsStat);

        //btnDisplayReportTrnsStat = subView.findViewById(R.id.btnDisplayReportTrnsStat);
        selected_radioBtnTrnsStat_Id = radioGrpTrnsStat.getCheckedRadioButtonId();
        radioBtnTrnsStatChoice = subView.findViewById(selected_radioBtnTrnsStat_Id);

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportingStatisticsFragment.this.getContext());
        builder.setTitle("Transactions Status Charts Choice");
        builder.setIcon(ReportingStatisticsFragment.this.getContext().getResources().getDrawable(R.drawable.ic_question_primary_48dp));
        builder.setView(subView);
        builder.setPositiveButton("Show Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeTransactionsStatusChoice();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice Maked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void MerchantTypesChoices(){

        LayoutInflater inflater = LayoutInflater.from(ReportingStatisticsFragment.this.getContext());
        View subView = inflater.inflate(R.layout.merchants_type_charts_choices, null);

        radioGrpMerchantType = subView.findViewById(R.id.radioGrpChartsMerchantTypeChoices);
        radioBtnPieChartMerchantType = subView.findViewById(R.id.radioPieChartMerchantType);
        radioBtnBarChartMerchantType = subView.findViewById(R.id.radioBarChartMerchantType);

        //btnDisplayReportTrnsStat = subView.findViewById(R.id.btnDisplayReportTrnsStat);
        selected_radioBtnMerchantType_Id = radioGrpMerchantType.getCheckedRadioButtonId();
        radioBtnMerchantTypeChoice = subView.findViewById(selected_radioBtnMerchantType_Id);

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportingStatisticsFragment.this.getContext());
        builder.setTitle("Merchant Type Charts Choice");
        builder.setIcon(ReportingStatisticsFragment.this.getContext().getResources().getDrawable(R.drawable.ic_question_primary_48dp));
        builder.setView(subView);
        builder.setPositiveButton("Show Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeMerchantTypeChoice();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice Maked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void CardBinChoices(){

        LayoutInflater inflater = LayoutInflater.from(ReportingStatisticsFragment.this.getContext());
        View subView = inflater.inflate(R.layout.card_bins_charts_choices, null);

        radioGrpCardBin = subView.findViewById(R.id.radioGrpChartsCardBinChoices);
        radioBtnPieChartCardBin = subView.findViewById(R.id.radioPieChartCardBin);
        radioBtnBarChartCardBin = subView.findViewById(R.id.radioBarChartCardBin);

        //btnDisplayReportTrnsStat = subView.findViewById(R.id.btnDisplayReportTrnsStat);
        selected_radioBtnCardBin_Id = radioGrpCardBin.getCheckedRadioButtonId();
        radioBtnCardBinChoice = subView.findViewById(selected_radioBtnCardBin_Id);

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportingStatisticsFragment.this.getContext());
        builder.setTitle("Card Bin Charts Choice");
        builder.setIcon(ReportingStatisticsFragment.this.getContext().getResources().getDrawable(R.drawable.ic_question_primary_48dp));
        builder.setView(subView);
        builder.setPositiveButton("Show Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeCardBinChoice();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice Maked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void BankNamesOfPayementChoices(){

        LayoutInflater inflater = LayoutInflater.from(ReportingStatisticsFragment.this.getContext());
        View subView = inflater.inflate(R.layout.bank_names_payement_chart_choices, null);

        radioGrpBankName = subView.findViewById(R.id.radioGrpChartsBankNameChoices);
        radioBtnPieChartBankName = subView.findViewById(R.id.radioPieChartBankName);
        radioBtnBarChartBankName = subView.findViewById(R.id.radioBarChartBankName);

        //btnDisplayReportTrnsStat = subView.findViewById(R.id.btnDisplayReportTrnsStat);
        selected_radioBtnBankName_Id = radioGrpBankName.getCheckedRadioButtonId();
        radioBtnBankNameChoice = subView.findViewById(selected_radioBtnBankName_Id);

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportingStatisticsFragment.this.getContext());
        builder.setTitle("Bank Names Of Payment Charts Choice");
        builder.setIcon(ReportingStatisticsFragment.this.getContext().getResources().getDrawable(R.drawable.ic_question_primary_48dp));
        builder.setView(subView);
        builder.setPositiveButton("Show Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeBankNamesOfPaymentChoice();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"No Choice Maked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

        switch (pos){
            case 0:
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Report Stat 1 : Transactions Status",Toast.LENGTH_LONG).show();
                TransactionsStatusChoices();
                //getMultiChartsTranStatusFrag();
                break;
            case 1:
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Report Stat 2 : Merchant Types",Toast.LENGTH_LONG).show();
                MerchantTypesChoices();
                break;
            case 2:
                CardBinChoices();
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Report Stat 3 : Bin Cards",Toast.LENGTH_LONG).show();
                break;
            case 3:
                BankNamesOfPayementChoices();
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Report Stat 3 : Bank Names Of Payement",Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Report Stat 1 : Transactions Status",Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(ReportingStatisticsFragment.this.getContext(),"Report Stat 2 : Amounts",Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
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
}
