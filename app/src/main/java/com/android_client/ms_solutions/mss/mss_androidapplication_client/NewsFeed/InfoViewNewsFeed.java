package com.android_client.ms_solutions.mss.mss_androidapplication_client.NewsFeed;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.InfoFeedTest;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.Models.TheEconomistArticleBindingModel;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.List;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 09/07/2018.
 */
@Layout(R.layout.news_feed_item)
public class InfoViewNewsFeed {

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.captionTxt)
    private TextView captionTxt;

    @View(R.id.urlToNewsTxt)
    private TextView urlToNewsTxt;

    @View(R.id.timeTxt)
    private TextView timeTxt;

    @View(R.id.imageViewNewsFeed)
    private ImageView imageView;

    private InfoFeedTest mInfo;
    private Context mContext;

    private TheEconomistArticleBindingModel mArticle;

    public InfoViewNewsFeed(Context context, InfoFeedTest info) {
        mContext = context;
        mInfo = info;
    }

    public InfoViewNewsFeed(Context context, TheEconomistArticleBindingModel article) {
        mContext = context;
        mArticle = article;
    }

    @Resolve
    private void onResolved() {
        // The original one of the test
        /*
        titleTxt.setText(mInfo.getTitle());
        captionTxt.setText(mInfo.getCaption());
        timeTxt.setText(mInfo.getTime());
        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
        */
        String publishTimeOfNews = mArticle.getPublishedAt();

        String publishTimeOfNews_Right = publishTimeOfNews.substring(0,10); // de 0 -> 10 : 2018-07-05 (a)
        String publishTimeOfNews_Left  = publishTimeOfNews.substring(11,19); // de 11 -> 19 : Full Time (exmple : 15:00:17 )
        String publishTimeOfNews_Exact = publishTimeOfNews_Right + " " + publishTimeOfNews_Left; // Result : (a) + (b) =  Sun Apr 23 2017 (c'est un exemple pour comprendre)

        titleTxt.setText(mArticle.getTitle());
        captionTxt.setText(mArticle.getDescription());
        timeTxt.setText(publishTimeOfNews_Exact);
        Glide.with(mContext).load(mArticle.getUrlToImage()).into(imageView);

        String UrlWebSiteOfNews = "<html><a href="+mArticle.getUrlToNews()+">Click here to read the news in the official website</a></html>";

        urlToNewsTxt.setText(Html.fromHtml(UrlWebSiteOfNews));
        urlToNewsTxt.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
