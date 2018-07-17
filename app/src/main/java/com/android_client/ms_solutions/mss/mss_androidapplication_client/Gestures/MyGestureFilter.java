package com.android_client.ms_solutions.mss.mss_androidapplication_client.Gestures;

import android.app.Activity;
import android.app.Fragment;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.Fragments.HomeFragment;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 06/07/2018.
 */

// Complete tuto of Gesture in this link :
// http://www.androhub.com/android-swipe-gesture/
public class MyGestureFilter extends GestureDetector.SimpleOnGestureListener {

    // Swipe gestures type
    public final static int SWIPE_UP = 1;
    public final static int SWIPE_DOWN = 2;
    public final static int SWIPE_LEFT = 3;
    public final static int SWIPE_RIGHT = 4;

    public final static int MODE_TRANSPARENT = 0;
    public final static int MODE_SOLID = 1;
    public final static int MODE_DYNAMIC = 2;

    private final static int ACTION_FAKE = -13; // just an unlikely number

    // Swipe distances
    private int swipe_Min_Distance = 100;
    private int swipe_Max_Distance = 350;
    private int swipe_Min_Velocity = 100;

    private int mode = MODE_DYNAMIC;
    private boolean running = true;
    private boolean tapIndicator = false;

    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;

    private HomeFragment fragmentContext;

    public MyGestureFilter(Activity context, SimpleGestureListener simpleGestureListener) {
        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = simpleGestureListener;
    }

    /*public MyGestureFilter(HomeFragment fragmentContext, SimpleGestureListener simpleGestureListener) {
        this.fragmentContext = fragmentContext;
        this.detector = new GestureDetector(fragmentContext.getActivity(), this);
        this.listener = simpleGestureListener;
    }*/

    public void onTouchEvent(MotionEvent event) {

        if (!this.running)
            return;

        boolean result = this.detector.onTouchEvent(event);
        // Get the gesture
        if (this.mode == MODE_SOLID)
            event.setAction(MotionEvent.ACTION_CANCEL);
        else if (this.mode == MODE_DYNAMIC) {

            if (event.getAction() == ACTION_FAKE)
                event.setAction(MotionEvent.ACTION_UP);
            else if (result)
                event.setAction(MotionEvent.ACTION_CANCEL);
            else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }

        }
        // else just do nothing, it's Transparent
    }

    public void setEnabled(boolean status) {
        this.running = status;
    }

    public int getSwipe_Min_Distance() {
        return swipe_Min_Distance;
    }

    public void setSwipe_Min_Distance(int swipe_Min_Distance) {
        this.swipe_Min_Distance = swipe_Min_Distance;
    }

    public int getSwipe_Max_Distance() {
        return swipe_Max_Distance;
    }

    public void setSwipe_Max_Distance(int swipe_Max_Distance) {
        this.swipe_Max_Distance = swipe_Max_Distance;
    }

    public int getSwipe_Min_Velocity() {
        return swipe_Min_Velocity;
    }

    public void setSwipe_Min_Velocity(int swipe_Min_Velocity) {
        this.swipe_Min_Velocity = swipe_Min_Velocity;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());

        if (xDistance > this.swipe_Max_Distance
                || yDistance > this.swipe_Max_Distance)
            return false;

        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);
        boolean result = false;

        if (velocityX > this.swipe_Min_Velocity
                && xDistance > this.swipe_Min_Distance) {
            if (e1.getX() > e2.getX()) // right to left
                this.listener.onSwipe(SWIPE_LEFT);
            else
                this.listener.onSwipe(SWIPE_RIGHT);

            result = true;
        } else if (velocityY > this.swipe_Min_Velocity
                && yDistance > this.swipe_Min_Distance) {
            if (e1.getY() > e2.getY()) // bottom to up
                this.listener.onSwipe(SWIPE_UP);
            else
                this.listener.onSwipe(SWIPE_DOWN);

            result = true;
        }

        return result;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent arg) {
        this.listener.onDoubleTap();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent arg) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg) {

        if (this.mode == MODE_DYNAMIC) { // we owe an ACTION_UP, so we fake an
            arg.setAction(ACTION_FAKE); // action which will be converted to an
            // ACTION_UP later.
            //this.context.dispatchTouchEvent(arg);
            this.fragmentContext.getActivity().dispatchTouchEvent(arg);
        }

        return false;
    }

}
