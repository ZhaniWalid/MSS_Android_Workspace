package com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Gestures.OnSwipeTouchListener;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MoneyCurrencyFixerIoBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TheEconomistArticleBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TheEconomistGoogleNewsBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.NewsFeed.HeadingViewNewsFeed;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.NewsFeed.InfoViewNewsFeed;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.NewsFeed.MoneyCurrencyConvertionNewsFeed;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApi;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.WebApiRestfulWS.SampleApiFactory;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    private OnFragmentInteractionListener mListener;

    public static String HOME_FRAGMENT_TAG = "HOME FRAGMENT TAG";

    //private MyGestureFilter detector;

    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;

    private SampleApi api = SampleApiFactory.getInstance();
    private TheEconomistGoogleNewsBindingModel valuesEconomistGoogleNewsData;
    private MoneyCurrencyFixerIoBindingModel valueOfMoneyCurrency;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Detect touched area
        //detector = new MyGestureFilter(getActivity(), this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        view.setOnTouchListener(new OnSwipeTouchListener(HomeFragment.this.getContext()) {
            @Override
            public void onSwipeDown() {
                Toast.makeText(HomeFragment.this.getContext(), "You Have Swiped => Down", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(HomeFragment.this.getContext(), "You Have Swiped => Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeUp() {
                Toast.makeText(HomeFragment.this.getContext(), "You Have Swiped => Up", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(HomeFragment.this.getContext(), "You Have Swiped => Right", Toast.LENGTH_SHORT).show();
            }
        });

        // Part Of Expandable News Feed
        mContext = HomeFragment.this.getContext();
        mExpandableView = view.findViewById(R.id.expandableView);
        //noinspection ConstantConditions
        // the original one of the test
        /*for(NewsFeedTest feed : NewsFeedJsonLoaderTest.loadFeeds(HomeFragment.this.getContext())){
            mExpandableView.addView(new HeadingViewNewsFeed(mContext, feed.getHeading()));
            for(InfoFeedTest info : feed.getInfoList()){
                mExpandableView.addView(new InfoViewNewsFeed(mContext, info));
            }
        }*/

        GetEonomistNewsFromGoogleNews();
        GetRealTimeMoneyCurrencyFromFixerIO();

        return view;
    }

    private void LoadGoogleNews(List<TheEconomistArticleBindingModel> Articles,String HeadingArticle){

        if (HeadingArticle.equals("the-economist")){
            HeadingArticle = "Economist News";
        }

        mExpandableView.addView(new HeadingViewNewsFeed(mContext, HeadingArticle));

        for(TheEconomistArticleBindingModel article : Articles) {
            mExpandableView.addView(new InfoViewNewsFeed(mContext, article));
        }

       /* for(TheEconomistArticleBindingModel article : Articles){

            String HeadingArticle = article.getSource().getId();
            if (HeadingArticle.equals("the-economist")){
                HeadingArticle = "Economist News";
            }
            mExpandableView.addView(new HeadingViewNewsFeed(mContext, HeadingArticle));
            //mExpandableView.addView(new InfoViewNewsFeed(mContext, article));
        }*/

    }

    private void LoadRealTimeMoneyCurrencyConversion(List<MoneyCurrencyFixerIoBindingModel> listaaaa){

        String HeadingMoneyCurrencyConversion = "Real Time Currency Conversion";
        mExpandableView.addView(new HeadingViewNewsFeed(mContext,HeadingMoneyCurrencyConversion));

         for (MoneyCurrencyFixerIoBindingModel model : listaaaa){
             mExpandableView.addView(new MoneyCurrencyConvertionNewsFeed(mContext,model));
         }
             //MoneyCurrencyFixerIoBindingModel model = new MoneyCurrencyFixerIoBindingModel();
             //model.setD(d);
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

    /*
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        setHasOptionsMenu(false);
        super.setHasOptionsMenu(hasMenu);
    }*/

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

    // Gesture Swipe Listener Part

    /*@Override
    public void onSwipe(int direction) {
        //Detect the swipe gestures and display toast
        String showToastMessage = "";

        switch (direction) {

            case MyGestureFilter.SWIPE_RIGHT:
                showToastMessage = "You have Swiped Right.";
                break;
            case MyGestureFilter.SWIPE_LEFT:
                showToastMessage = "You have Swiped Left.";
                break;
            case MyGestureFilter.SWIPE_DOWN:
                showToastMessage = "You have Swiped Down.";
                break;
            case MyGestureFilter.SWIPE_UP:
                showToastMessage = "You have Swiped Up.";
                break;

        }
        Toast.makeText(HomeFragment.this.getContext(), showToastMessage, Toast.LENGTH_SHORT).show();
    }

    //Toast shown when double tapped on screen
    @Override
    public void onDoubleTap() {
        Toast.makeText(HomeFragment.this.getContext(), "You have Double Tapped.", Toast.LENGTH_SHORT).show();
    }*/


    /*@SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // Call onTouchEvent of SimpleGestureFilter class
        boolean touching = dispatchTouchEvent(motionEvent);
        if(touching){
            Toast.makeText(HomeFragment.this.getContext(),"Touch Work Ok !",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(HomeFragment.this.getContext(),"Touch Did Not Work !",Toast.LENGTH_LONG).show();
        }

        return false;
    }*/


    /*public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return dispatchTouchEvent(me);
    }*/

    private void GetEonomistNewsFromGoogleNews(){
        System.err.println("Getting Json Data => Eonomist News From Google News : \n");
        new EconomistNewsFromGoogleNewsTask().execute();
    }

    /// Début : Parties des : Classes AsyncTask
    /// Class : EconomistNewsFromGoogleNewsTask
    class EconomistNewsFromGoogleNewsTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                valuesEconomistGoogleNewsData = api.GetEconomistNewsFromGoogleNews().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {

             List<TheEconomistArticleBindingModel> listOfArticles = valuesEconomistGoogleNewsData.getListOfArticles();
             Collections.reverse(listOfArticles); // Used to Reverse order of list of articles to show it from the recent news to the oldest news

             String HeadingArticle = "";
             for (int i = 0; i< listOfArticles.size() ; i++){

                System.err.println("************ Start Of Economist news from google news api : \n");
                System.err.println("Source is : "+ listOfArticles.get(i).getSource().getId()+"\n");
                System.err.println("Author : "+ listOfArticles.get(i).getAuthor()+"\n");
                System.err.println("Title : "+ listOfArticles.get(i).getTitle()+"\n");
                System.err.println("Description : "+ listOfArticles.get(i).getDescription()+"\n");
                System.err.println("Url To News : "+ listOfArticles.get(i).getUrlToNews()+"\n");
                System.err.println("Url To Image : "+ listOfArticles.get(i).getUrlToImage()+"\n");
                System.err.println("Publishing Time : "+ listOfArticles.get(i).getPublishedAt()+"\n");
                System.err.println("************************************************************* \n");

                HeadingArticle = listOfArticles.get(i).getSource().getId();
            }
            System.err.println("************ End Of Economist news from google news api. \n");
            System.err.println("Size Of Economist news from google news api is : "+ listOfArticles.size());

            LoadGoogleNews(listOfArticles,HeadingArticle);

            super.onPostExecute(string);
        }
    }

    private void GetRealTimeMoneyCurrencyFromFixerIO(){
       new MoneyCurrencyFixeIOTask().execute();
    }

    /// Class : MoneyCurrencyFixeIOTask
    class MoneyCurrencyFixeIOTask extends AsyncTask<String,String,MoneyCurrencyFixerIoBindingModel>{

        @Override
        protected MoneyCurrencyFixerIoBindingModel doInBackground(String... strings) {

            try {
                valueOfMoneyCurrency = api.GetMoneyCurrencyRealTimeFromFixerIO().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return valueOfMoneyCurrency;
        }

        @Override
        protected void onPostExecute(MoneyCurrencyFixerIoBindingModel moneyCurrencyFixerIO) {

            String baseCurrency = valueOfMoneyCurrency.getBaseMoneyCurrency();
            String toDayDateOfCurrency = valueOfMoneyCurrency.getDateOfTodayCurrency();
            JsonObject listOfSymbolsCurrency = valueOfMoneyCurrency.getRates();

            Double TND = listOfSymbolsCurrency.get("TND").getAsDouble();
            Double MAD = listOfSymbolsCurrency.get("MAD").getAsDouble();
            Double DZD = listOfSymbolsCurrency.get("DZD").getAsDouble();
            Double LYD = listOfSymbolsCurrency.get("LYD").getAsDouble();
            Double QAR = listOfSymbolsCurrency.get("QAR").getAsDouble();
            Double KWD = listOfSymbolsCurrency.get("KWD").getAsDouble();
            Double SAR = listOfSymbolsCurrency.get("SAR").getAsDouble();
            Double USD = listOfSymbolsCurrency.get("USD").getAsDouble();
            Double GBP = listOfSymbolsCurrency.get("GBP").getAsDouble();
            Double CAD = listOfSymbolsCurrency.get("CAD").getAsDouble();
            Double CHF = listOfSymbolsCurrency.get("CHF").getAsDouble();
            Double AUD = listOfSymbolsCurrency.get("AUD").getAsDouble();
            Double SEK = listOfSymbolsCurrency.get("SEK").getAsDouble();

            System.err.println("***************** Start of => Currency Money Real Time Data from Fixer IO :  ****************** \n");
            System.err.println("Unit = 1 \n");
            System.err.println("Base Currency Is Euro : "+ baseCurrency +" \n");
            System.err.println("Date Of Last Synchronisation : "+ toDayDateOfCurrency + " \n");
            System.err.println("List Below are converted currency from EURO by Unit = 1 \n");

                 System.err.println("Tunisian   Dinar    => TND : "+TND);
                 System.err.println("Moroccan   Dirham   => MAD : "+MAD);
                 System.err.println("Algerian   Dinar    => DZD : "+DZD);
                 System.err.println("Libyan     Dinar    => LYD : "+LYD);
                 System.err.println("Qatarian   Riyal    => QAR : "+QAR);
                 System.err.println("Kuwaiti    Dinar    => KWD : "+KWD);
                 System.err.println("Saudi      Riyal    => SAR : "+SAR);
                 System.err.println("American   Dollar   => USD : "+USD);
                 System.err.println("British    Pound    => GBP : "+GBP);
                 System.err.println("Canadian   Pound    => CAD : "+CAD);
                 System.err.println("Swiss      Franc    => CHF : "+CHF);
                 System.err.println("Australian Dollar   => AUD : "+AUD);
                 System.err.println("Swedish    krona    => SEK : "+SEK);

            System.err.println("***************** Currency is ' Hourly Updated ' ******************\n");
            System.err.println("***************** Size Of list JsonObject Currency Results is : "+listOfSymbolsCurrency.size()+"  ******************\n");
            System.err.println("***************** End of => Currency Money Real Time Data from Fixer IO  ******************");

            Double[]  tabConvertedCurrency = new Double[15];

        for (int i = 0 ; i < tabConvertedCurrency.length ; i++) {

            tabConvertedCurrency[0] = TND;
            tabConvertedCurrency[1] = MAD;
            tabConvertedCurrency[2] = DZD;
            tabConvertedCurrency[3] = LYD;
            tabConvertedCurrency[4] = QAR;
            tabConvertedCurrency[5] = KWD;
            tabConvertedCurrency[6] = SAR;
            tabConvertedCurrency[7] = USD;
            tabConvertedCurrency[8] = GBP;
            tabConvertedCurrency[9] = CAD;
            tabConvertedCurrency[10] = CHF;
            tabConvertedCurrency[11] = AUD;
            tabConvertedCurrency[12] = SEK;
        }

        Integer[] tab_imagesId = new Integer[15];
        String[] tab_descriptionWithImgId = new String[15];
        List<Integer> listImageId = new ArrayList<>();
        List<String> listDesc = new ArrayList<>();

      for (int i =0 ; i < tabConvertedCurrency.length ; i++){
          switch (i){
              case 0:
                  String currencyMsg = "TND (Tunisian Dinar) : " + tabConvertedCurrency[0];
                  int imageID = mContext.getResources().getIdentifier("tunisia_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID);
                  tab_imagesId[0] = imageID;
                  tab_descriptionWithImgId[0] = "Case 0 : "+currencyMsg + " => Image Id : "+tab_imagesId[0];
                  listImageId.add(imageID);
                  listDesc.add(currencyMsg);
                  break;
              case 1:
                  String currencyMsg_1 = "MAD (Moroccan Dirham) : " + tabConvertedCurrency[1];
                  int imageID_1 = mContext.getResources().getIdentifier("morroco_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_1);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_1);
                  tab_imagesId[1] = imageID_1;
                  tab_descriptionWithImgId[1] = "Case 1 : "+currencyMsg_1 + " => Image Id : "+tab_imagesId[1];
                  listImageId.add(imageID_1);
                  listDesc.add(currencyMsg_1);
                  break;
              case 2:
                  String currencyMsg_2 = "DZD (Algerian Dinar) : " + tabConvertedCurrency[2];
                  int imageID_2 = mContext.getResources().getIdentifier("algeria_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_2);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_2);
                  tab_imagesId[2] = imageID_2;
                  tab_descriptionWithImgId[2] = "Case 2 : "+currencyMsg_2 + " => Image Id : "+tab_imagesId[2];
                  listImageId.add(imageID_2);
                  listDesc.add(currencyMsg_2);
                  break;
              case 3:
                  String currencyMsg_3 = "LYD  (Libyan Dinar) : " + tabConvertedCurrency[3];
                  int imageID_3 = mContext.getResources().getIdentifier("libya_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_3);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_3);
                  tab_imagesId[3] = imageID_3;
                  tab_descriptionWithImgId[3] = "Case 3 : "+currencyMsg_3 + " => Image Id : "+tab_imagesId[3];
                  listImageId.add(imageID_3);
                  listDesc.add(currencyMsg_3);
                  break;
              case 4:
                  String currencyMsg_4 = "QAR  (Qatarian Riyal) : " + tabConvertedCurrency[4];
                  int imageID_4 = mContext.getResources().getIdentifier("qatar_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_4);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_4);
                  tab_imagesId[4] = imageID_4;
                  tab_descriptionWithImgId[4] = "Case 4 : "+currencyMsg_4 + " => Image Id : "+tab_imagesId[4];
                  listImageId.add(imageID_4);
                  listDesc.add(currencyMsg_4);
                  break;
              case 5:
                  String currencyMsg_5 = "KWD  (Kuwaiti Dinar) : " + tabConvertedCurrency[5];
                  int imageID_5 = mContext.getResources().getIdentifier("kuwait_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_5);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_5);
                  tab_imagesId[5] = imageID_5;
                  tab_descriptionWithImgId[5] = "Case 5 : "+currencyMsg_5+" => Image Id : "+tab_imagesId[5];
                  listImageId.add(imageID_5);
                  listDesc.add(currencyMsg_5);
                  break;
              case 6:
                  String currencyMsg_6 = "SAR  (Saudi Riyal) : " + tabConvertedCurrency[6];
                  int imageID_6 = mContext.getResources().getIdentifier("saudia_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_6);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_6);
                  tab_imagesId[6] = imageID_6;
                  tab_descriptionWithImgId[6] = "Case 6 : "+currencyMsg_6+" => Image Id : "+tab_imagesId[6];
                  listImageId.add(imageID_6);
                  listDesc.add(currencyMsg_6);
                  break;
              case 7:
                  String currencyMsg_7 = "USD (American Dollar) : " + tabConvertedCurrency[7];
                  int imageID_7 = mContext.getResources().getIdentifier("usa_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_7);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_7);
                  tab_imagesId[7] = imageID_7;
                  tab_descriptionWithImgId[7] = "Case 7 : "+currencyMsg_7+" => Image Id : "+tab_imagesId[7];
                  listImageId.add(imageID_7);
                  listDesc.add(currencyMsg_7);
                  break;
              case 8:
                  String currencyMsg_8 = "GBP (British Pound) : " + tabConvertedCurrency[8];
                  int imageID_8 = mContext.getResources().getIdentifier("uk_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_8);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_8);
                  tab_imagesId[8] = imageID_8;
                  tab_descriptionWithImgId[8] = "Case 8 : "+currencyMsg_8 + " => Image Id : "+tab_imagesId[8];
                  listImageId.add(imageID_8);
                  listDesc.add(currencyMsg_8);
                  break;
              case 9:
                  String currencyMsg_9 = "CAD (Canadian Pound) : " + tabConvertedCurrency[9];
                  int imageID_9 = mContext.getResources().getIdentifier("canada_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_9);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_9);
                  tab_imagesId[9] = imageID_9;
                  tab_descriptionWithImgId[9] = "Case 9 : "+currencyMsg_9 + " => Image Id : "+tab_imagesId[9];
                  listImageId.add(imageID_9);
                  listDesc.add(currencyMsg_9);
                  break;
              case 10:
                  String currencyMsg_10 = "CHF (Swiss Franc) : " + tabConvertedCurrency[10];
                  int imageID_10 = mContext.getResources().getIdentifier("switzerland_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_10);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_10);
                  tab_imagesId[10] = imageID_10;
                  tab_descriptionWithImgId[10] = "Case 10 : "+currencyMsg_10 + " => Image Id : "+tab_imagesId[10];
                  listImageId.add(imageID_10);
                  listDesc.add(currencyMsg_10);
                  break;
              case 11:
                  String currencyMsg_11 = "AUD (Australian Dollar): " + tabConvertedCurrency[11];
                  int imageID_11 = mContext.getResources().getIdentifier("australia_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_11);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_11);
                  tab_imagesId[11] = imageID_11;
                  tab_descriptionWithImgId[11] = "Case 11 : "+currencyMsg_11 + " => Image Id : "+tab_imagesId[11];
                  listImageId.add(imageID_11);
                  listDesc.add(currencyMsg_11);
                  break;
              case 12:
                  String currencyMsg_12 = "SEK (Swedish krona) : " + tabConvertedCurrency[12];
                  int imageID_12 = mContext.getResources().getIdentifier("sweden_flag", "drawable", mContext.getPackageName());
                  valueOfMoneyCurrency.setDescriptionWithCurrencyValue(currencyMsg_12);
                  valueOfMoneyCurrency.setImageIdOfCountry(imageID_12);
                  tab_imagesId[12] = imageID_12;
                  tab_descriptionWithImgId[12] = "Case 12 : "+currencyMsg_12 + " => Image Id : "+tab_imagesId[12];
                  listImageId.add(imageID_12);
                  listDesc.add(currencyMsg_12);
                  break;
          }
      }

            System.err.println("Size of listImageId => "+listImageId.size()+"\n");
            System.err.println("Size of listDesc => "+listDesc.size()+"\n");

            System.err.println("**********************************************************************");

            System.err.println("Description Of Currency With Image Id Test : \n");
            for (int i=0 ; i < tab_descriptionWithImgId.length ; i++){
                System.err.println(tab_descriptionWithImgId[i]);
            }

            System.err.println("**********************************************************************");

            List<MoneyCurrencyFixerIoBindingModel> listOfMoneyCurrency = new ArrayList<>();

            // I reversed the 2 list because when getting data Tunisia will be the last 1
            // so i inversed these 2 lists to make tunisia show at the first element because it's my country <3
            Collections.reverse(listImageId);
            Collections.reverse(listDesc);

            int commonSize;
            if (listImageId.size() == listDesc.size()){
                commonSize = listImageId.size(); // or : commonSize = listDesc.size() => same result
            }else {
                commonSize = 0;
            }

            for (int i=0 ; i < commonSize ; i++){
                    MoneyCurrencyFixerIoBindingModel m = new MoneyCurrencyFixerIoBindingModel();
                    m.setBaseMoneyCurrency(baseCurrency);
                    m.setDateOfTodayCurrency(toDayDateOfCurrency);
                    m.setImageIdOfCountry(listImageId.get(i));
                    m.setDescriptionWithCurrencyValue(listDesc.get(i));
                    listOfMoneyCurrency.add(m);
            }

            LoadRealTimeMoneyCurrencyConversion(listOfMoneyCurrency);

            super.onPostExecute(moneyCurrencyFixerIO);
        }
    }
}
