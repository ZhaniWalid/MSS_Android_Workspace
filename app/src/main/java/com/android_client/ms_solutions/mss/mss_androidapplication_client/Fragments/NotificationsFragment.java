package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Adapters.RejectedTransactionNotificationListAdapter;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.gw_StatusCodeBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SampleApi api = SampleApiFactory.getInstance();

    private EditText editText_SearchRejectedTransactions_Notif;
    private TextView textView_TotalRejTrans;

    List<gw_StatusCodeBindingModel> List_valuesRejectedTransNotif; // used for boath ListView and RecyclerView
    //List<gw_trnsct_GeneralBindingModel> List_valuesGeneralTransactionsData_ForFiltring; // Used for filtring in RecyclerView

    //private int size_listTrnscExtended = 0;

    //ArrayAdapter<gw_trnsct_GeneralBindingModel> adapter = null; // used for ListView
    public static ListView listView_RejectedTransNotif; // used for ListView
    RejectedTransactionNotificationListAdapter Adapter_RejectedTransactionNotificationList;

    int textlength = 0;


    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        listView_RejectedTransNotif = view.findViewById(R.id.ListView_rejectedTransactionsNotif);
        editText_SearchRejectedTransactions_Notif = view.findViewById(R.id.editText_search_rejectedTransactionsNotif);
        textView_TotalRejTrans = view.findViewById(R.id.txtView_totalSizeOfNotications);

        GetRejectedTransactionNotificationValues();

       /* listView_RejectedTransNotif.setLongClickable(true); // in the listView in the XML Layout we should add also: android:longClickable="true"
        listView_RejectedTransNotif.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(NotificationsFragment.this.getContext(),"No Extra Information Yet For : "+position,Toast.LENGTH_LONG).show();
                return true;
            }
        });*/

        return view;
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


    private void GetRejectedTransactionNotificationValues(){
        new RejectedTransactionNotificationTask().execute();
    }

    //// Début : Parties des : Classes AsyncTask
    /// Class : RejectedTransactionNotificationTask
    class RejectedTransactionNotificationTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                List_valuesRejectedTransNotif = api.GetOnlyRejectedStatusCodeWithDesc().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String string) {

            Adapter_RejectedTransactionNotificationList = new RejectedTransactionNotificationListAdapter(NotificationsFragment.this.getContext(),List_valuesRejectedTransNotif);
            listView_RejectedTransNotif.setAdapter(Adapter_RejectedTransactionNotificationList);

            textView_TotalRejTrans.setText("Total Rejected Transactions : "+List_valuesRejectedTransNotif.size());

            super.onPostExecute(string);
        }
    }
}
