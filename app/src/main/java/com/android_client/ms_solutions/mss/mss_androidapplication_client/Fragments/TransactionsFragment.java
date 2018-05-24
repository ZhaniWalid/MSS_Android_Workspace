package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.GeneralTransactionsDataAdapter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.GeneralTransactionsRecyclerAdpater;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.ListViewTransactionsDataAdapter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Filters.TransactionsFilters.FilterGeneralTransactionsRecyclerAdpater;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_ExtendedBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 19/04/2018.
 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   /* private FrameLayout frameLayout_transactions;
    private RecyclerView recyclerView_transactions;
    private SearchView searchView_transactions;
    private LinearLayoutManager linearLayoutManager; */

    SampleApi api = SampleApiFactory.getInstance();

    private EditText editText_SearchTransactions;

    List<gw_trnsct_GeneralBindingModel> List_valuesGeneralTransactionsData; // used for boath ListView and RecyclerView
    List<gw_trnsct_GeneralBindingModel> List_valuesGeneralTransactionsData_ForFiltring; // Used for filtring in RecyclerView
    //List<gw_trnsct_GeneralBindingModel> List_TransactionsData;
    //List<gw_trnsct_ExtendedBindingModel> List_valuesExtendedTransactionsData;
    //ListViewTransactionsDataAdapter listViewTransactionsDataAdapter_2;

    //private int size_listTrnscExtended = 0;

    //ArrayAdapter<gw_trnsct_GeneralBindingModel> adapter = null; // used for ListView
    ListView listView_GeneralTransactionsData; // used for ListView
    ListViewTransactionsDataAdapter listViewTransactionsDataAdapter;

    //GeneralTransactionsRecyclerAdpater generalTransactionsRecyclerAdpater; //  used for RecyclerView
    //RecyclerView listRecyledView_GeneralTransactionsData; // used for RecyclerView

 //   public static String idTransaction_static = "", idMerchant_static = "", idTerminalMerchant_static = "" , idHost_static = "";
  //  public static String amountAuthorisedNumeric_static = "", etatTransaction_static = "", bankOfRequest_static = "";

   // public static int listTransactions_Size = 0 , Filter_listTransactions_Size = 0;

    int textlength = 0;

    private OnFragmentInteractionListener mListener;

    public TransactionsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mParam1 Parameter 1.
     * @param mParam2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsFragment newInstance(String mParam1, String mParam2) {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mParam1);
        args.putString(ARG_PARAM2, mParam2);
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
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        listView_GeneralTransactionsData = view.findViewById(R.id.ListView_transactionsGeneralData); // sa marche
        editText_SearchTransactions = view.findViewById(R.id.editText_search_transactions);

        //List_TransactionsData = new ArrayList<>();
        List_valuesGeneralTransactionsData_ForFiltring = new ArrayList<>();
      /*  frameLayout_transactions = view.findViewById(R.id.frame_layout_generalTransactions);
        recyclerView_transactions = view.findViewById(R.id.ListView_transactionsGeneralData);
        searchView_transactions = view.findViewById(R.id.action_search_transactions);
        linearLayoutManager = new LinearLayoutManager(TransactionsFragment.this.getContext());

        recyclerView_transactions.setLayoutManager(linearLayoutManager);
        recyclerView_transactions.setHasFixedSize(true);*/

        //listRecyledView_GeneralTransactionsData = view.findViewById(R.id.ListView_transactionsGeneralData);
        //recyclerView = view.findViewById(R.id.recycler_view_transactions);
      //  linearLayoutManager = view.findViewById(R.id.linearLayout1);
     //   linearLayoutManager = new LinearLayoutManager(TransactionsFragment.this.getActivity());
     //   recyclerView.setLayoutManager(linearLayoutManager);

        loadingGeneralTransactionsData();
        //onSearching();
        //Filter();
        //loadExtendedTransactionsData();

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

    public void FilterTransactionsData(){
       /* for (int i=0;i<listTransactions_Size;i++) {
            gw_trnsct_GeneralBindingModel model = new gw_trnsct_GeneralBindingModel();

            model.setIdTransaction(List_valuesGeneralTransactionsData.get(i).getIdTransaction());
            model.setIdMerchant(List_valuesGeneralTransactionsData.get(i).getIdMerchant());
            model.setIdTerminalMerchant(List_valuesGeneralTransactionsData.get(i).getIdTerminalMerchant());
            model.setIdHost(List_valuesGeneralTransactionsData.get(i).getIdHost());
            model.setAmountAuthorisedNumeric(List_valuesGeneralTransactionsData.get(i).getAmountAuthorisedNumeric());
            model.setEtatTransaction(List_valuesGeneralTransactionsData.get(i).getEtatTransaction());
            // Binds all strings into an array
            List_TransactionsData.add(model);
            List_valuesGeneralTransactionsData_ForFiltring.add(model);
        }
        listViewTransactionsDataAdapter = new ListViewTransactionsDataAdapter(TransactionsFragment.this.getContext(),List_TransactionsData);
        listView_GeneralTransactionsData.setAdapter(listViewTransactionsDataAdapter);*/

       /*

        listView_GeneralTransactionsData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String msg = "Id Transaction : \n\r";
                Toast.makeText(TransactionsFragment.this.getContext(), msg+List_valuesGeneralTransactionsData_ForFiltring.get(position).getIdTransaction(), Toast.LENGTH_LONG).show();
            }
        });

        */

        editText_SearchTransactions.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                textlength = editText_SearchTransactions.getText().length();
                List_valuesGeneralTransactionsData_ForFiltring.clear();
                //int resultFiltred = 0;

                for (int i=0;i<List_valuesGeneralTransactionsData.size();i++){
                    if(textlength <= List_valuesGeneralTransactionsData.get(i).getIdTransaction().length()){
                        Log.d("Log Id Transaction",List_valuesGeneralTransactionsData.get(i).getIdTransaction().toLowerCase().trim());
                        if (       List_valuesGeneralTransactionsData.get(i).getIdTransaction().toLowerCase().trim().contains(
                                editText_SearchTransactions.getText().toString().toLowerCase().trim())
                                || String.valueOf(List_valuesGeneralTransactionsData.get(i).getAmount()).toLowerCase().trim().contains(
                                editText_SearchTransactions.getText().toString().toLowerCase().trim())
                                || List_valuesGeneralTransactionsData.get(i).getEtatTransaction().toLowerCase().trim().contains(
                                editText_SearchTransactions.getText().toString().toLowerCase().trim())
                                || List_valuesGeneralTransactionsData.get(i).getIdHost().toLowerCase().trim().contains(
                                editText_SearchTransactions.getText().toString().toLowerCase().trim())
                                || List_valuesGeneralTransactionsData.get(i).getBankName_GateWay().toLowerCase().trim().contains(
                                editText_SearchTransactions.getText().toString().toLowerCase().trim())
                                || List_valuesGeneralTransactionsData.get(i).getBankOfRequest().toLowerCase().trim().contains(
                                editText_SearchTransactions.getText().toString().toLowerCase().trim())  ) {

                            List_valuesGeneralTransactionsData_ForFiltring.add(List_valuesGeneralTransactionsData.get(i));

                         //   resultFiltred = +1;
                          //  String msgCountResult = resultFiltred + " results that match to your input search";
                          //  Toast.makeText(TransactionsFragment.this.getContext(),msgCountResult,Toast.LENGTH_LONG).show();

                        }
                    } /*else{
                        String msgNoResultFound = "Sorry ! No Result Found,that match to your input search";
                        Toast.makeText(TransactionsFragment.this.getContext(),msgNoResultFound,Toast.LENGTH_LONG).show();
                    }*/
                }

                listViewTransactionsDataAdapter = new ListViewTransactionsDataAdapter(TransactionsFragment.this.getContext(),List_valuesGeneralTransactionsData_ForFiltring);
                listView_GeneralTransactionsData.setAdapter(listViewTransactionsDataAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    private void loadingGeneralTransactionsData(){ new TransactionsGeneralDataTask().execute(); }

    //private void loadingGeneralTransactionsData_ForFilter(){ new TransactionsGeneralDataForFiltringTask().execute(); }

    //// Début : Parties des : Classes AsyncTask

    /// Class : TransactionsGeneralDataTask

    class TransactionsGeneralDataTask  extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                List_valuesGeneralTransactionsData = api.GetGeneralTransactionsData().execute().body();
               // List_valuesGeneralTransactionsData_ForFiltring = api.GetGeneralTransactionsData().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            /* Marche 5/5 mais avec ListView et non pas RecyclerView
            adapter = new GeneralTransactionsDataAdapter(TransactionsFragment.this.getContext(),android.R.layout.simple_list_item_1,List_valuesGeneralTransactionsData);
            listView_GeneralTransactionsData.setAdapter(adapter);*/
            listViewTransactionsDataAdapter = new ListViewTransactionsDataAdapter(TransactionsFragment.this.getContext(),List_valuesGeneralTransactionsData);
            listView_GeneralTransactionsData.setAdapter(listViewTransactionsDataAdapter);

             FilterTransactionsData();
            //listTransactions_Size = List_valuesGeneralTransactionsData.size();
            super.onPostExecute(s);
        }
    }

}
