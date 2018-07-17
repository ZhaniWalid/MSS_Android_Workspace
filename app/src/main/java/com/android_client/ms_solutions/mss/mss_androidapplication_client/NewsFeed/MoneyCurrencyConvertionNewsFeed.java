package com.android_client.ms_solutions.mss.mss_androidapplication_client.NewsFeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.HomeFragment;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.MoneyCurrencyFixerIoBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 12/07/2018.
 */

@Layout(R.layout.money_converter_fixe_io)
public class MoneyCurrencyConvertionNewsFeed {

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.unitOfCurrencyTxt)
    private TextView unitOfCurrencyTxt;

    @View(R.id.baseEuroCurrencyTxt)
    private TextView baseEuroCurrencyTxt;

    @View(R.id.convertedCurrencyFromEuroTxt)
    private TextView convertedCurrencyFromEuroTxt;

    @View(R.id.lastDateSynchronisationCurrencyTxt)
    private TextView lastDateSynchronisationCurrencyTxt;

    @View(R.id.imageViewCountryFlag)
    private ImageView imageViewCountryFlag;

    private Context mContext;
    private MoneyCurrencyFixerIoBindingModel moneyCurrencyFixerIoBindingModel;


    public MoneyCurrencyConvertionNewsFeed(Context context, MoneyCurrencyFixerIoBindingModel moneyCurrency) {
        this.mContext = context;
        this.moneyCurrencyFixerIoBindingModel = moneyCurrency;
    }

    @SuppressLint("SetTextI18n")
    @Resolve
    private void onResolved() {

        String unitOfCurrency = "Unit = 1";
        unitOfCurrencyTxt.setText(unitOfCurrency);
        String baseCurr = "Base Currency Is Euro : ";
        baseEuroCurrencyTxt.setText(baseCurr + moneyCurrencyFixerIoBindingModel.getBaseMoneyCurrency());
        lastDateSynchronisationCurrencyTxt.setText(moneyCurrencyFixerIoBindingModel.getDateOfTodayCurrency() + " (Currency Is Hourly Updated) ");
        convertedCurrencyFromEuroTxt.setText(moneyCurrencyFixerIoBindingModel.getDescriptionWithCurrencyValue());
        Glide.with(mContext).load(moneyCurrencyFixerIoBindingModel.getImageIdOfCountry()).into(imageViewCountryFlag);

    }
}


