package com.android_client.ms_solutions.mss.mss_androidapplication_client.NewsFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 09/07/2018.
 */

@Parent
@SingleTop
@Layout(R.layout.news_feed_heading)
public class HeadingViewNewsFeed {

    @View(R.id.headingTxt)
    private TextView headingTxt;

    @View(R.id.toggleIcon)
    private ImageView toggleIcon;

    @Toggle(R.id.toggleView)
    private LinearLayout toggleView;

    @ParentPosition
    private int mParentPosition;

    private Context mContext;
    private String mHeading;

    /*private void AfterViews(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.news_feed_heading, null);

        headingTxt = subView.findViewById(R.id.headingTxt);
        toggleIcon = subView.findViewById(R.id.toggleIcon);
        toggleView = subView.findViewById(R.id.toggleView);

    }*/

    public HeadingViewNewsFeed(Context context, String heading) {
        mContext = context;
        mHeading = heading;
    }

    @Resolve
    private void onResolved() {
       // int imageID = mContext.getResources().getIdentifier("baseline_keyboard_arrow_up_white_24px", "drawable", mContext.getPackageName());
       // toggleIcon.setImageResource(imageID);

        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_white_24px));
        headingTxt.setText(mHeading);
    }

    @Expand
    private void onExpand(){
        //int imageID = mContext.getResources().getIdentifier("baseline_keyboard_arrow_down_white_24px", "drawable", mContext.getPackageName());
        //toggleIcon.setImageResource(imageID);
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_white_24px));
    }

    @Collapse
    private void onCollapse(){
        //int imageID = mContext.getResources().getIdentifier("baseline_keyboard_arrow_up_white_24px", "drawable", mContext.getPackageName());
       // toggleIcon.setImageResource(imageID);
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_white_24px));
    }
}
