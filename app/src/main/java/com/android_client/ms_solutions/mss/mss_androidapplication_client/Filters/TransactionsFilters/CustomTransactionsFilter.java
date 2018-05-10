package com.android_client.ms_solutions.mss.mss_androidapplication_client.Filters.TransactionsFilters;

import android.os.AsyncTask;
import android.widget.Filter;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.GeneralTransactionsRecyclerAdpater;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.TransactionsFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_trnsct_GeneralBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 08/05/2018.
 */

public class CustomTransactionsFilter extends Filter {

    FilterGeneralTransactionsRecyclerAdpater filterGeneralTransactionsRecyclerAdpater;
    List<gw_trnsct_GeneralBindingModel> filterListTransactions;

    SampleApi api = SampleApiFactory.getInstance();
    public int filterListTransactions_Size = 0;

    public CustomTransactionsFilter(FilterGeneralTransactionsRecyclerAdpater filterGeneralTransactionsRecyclerAdpater, List<gw_trnsct_GeneralBindingModel> filterListTransactions) {
        this.filterGeneralTransactionsRecyclerAdpater = filterGeneralTransactionsRecyclerAdpater;
        this.filterListTransactions = filterListTransactions;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(charSequence != null && charSequence.length() > 0)
        {
            //CHANGE TO UPPER
            charSequence=charSequence.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<gw_trnsct_GeneralBindingModel> filteredTransactions = new ArrayList<>();


            for (int i=0;i<filterListTransactions_Size;i++)
            {
                //CHECK
                if( filterListTransactions.get(i).getIdTransaction().toUpperCase().contains(charSequence))
                {
                    //ADD PRODUCT TO FILTERED PRODUCTS
                    filteredTransactions.add(filterListTransactions.get(i));
                }
            }

            results.count=filteredTransactions.size();
            results.values=filteredTransactions;
        }else
        {
            results.count=filterListTransactions_Size;
            results.values=filterListTransactions;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        filterGeneralTransactionsRecyclerAdpater.list_trnsct_general = (List<gw_trnsct_GeneralBindingModel>) filterResults.values;
        //REFRESH
        loadData();
        filterGeneralTransactionsRecyclerAdpater.notifyDataSetChanged();
    }

    private void loadData(){
        new TransactionsGeneralDataForFiltringTask().execute();
    }

    // Class AsyncTask
    // Class : TransactionsGeneralDataForFiltringTask
    class TransactionsGeneralDataForFiltringTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            // Used for filtring in RecyclerView
            try {
                filterListTransactions = api.GetGeneralTransactionsData().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            filterListTransactions_Size = filterListTransactions.size();
            super.onPostExecute(s);
        }
    }
}
