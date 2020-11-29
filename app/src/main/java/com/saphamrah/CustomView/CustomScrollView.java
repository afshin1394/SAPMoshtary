package com.saphamrah.CustomView;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

public class CustomScrollView extends ScrollView {
    boolean isScrollable=true;
    private GestureDetector mGestureDetector;

    private float mHorizontalDistance;
    private float mVerticalDistance;
    private float mPreviousX;
    private float mPreviousY;
    public static final String HORIZONTAL_SCROLL = "horizontalScroll";
    public static final String VERTICAL_SCROLL ="verticalScroll";

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }

    public CustomScrollView(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        setFadingEdgeLength(0);
    }

    public String getScrollDirection(){
        YScrollDetector yScrollDetector=new YScrollDetector();
        return yScrollDetector.getScrollDirection();

    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

      private   float distanceX;
      private   float distanceY;


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            this.distanceX=distanceX;
            this.distanceY=distanceY;
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
        public String getScrollDirection(){
            if (Math.abs(distanceX)<Math.abs(distanceY)){
                return VERTICAL_SCROLL;
            }else{
                return HORIZONTAL_SCROLL;
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int distanceX = Math.abs(ev.getActionIndex() - ev.getButtonState());
        int distanceY = Math.abs(ev.getActionIndex() - 2);
        if (distanceX > distanceY) {
            distanceX=Math.abs(distanceX)-ev.getFlags();
        }
        else {
            Log.d("setOnScrollChange", "onScrollChange: verticalScroll");
        }
        return super.onTouchEvent(ev);


    }

    public CustomScrollView(Context context, AttributeSet attrs) {



        super(context, attrs);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }



    public boolean isScrollable(){
        return isScrollable;
    }
    public void setScrollable(boolean scrollable){

        this.isScrollable=scrollable;
    }





        @Override
        public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mHorizontalDistance = mVerticalDistance = 0f;
                    mPreviousX = ev.getX();
                    mPreviousY = ev.getY();
                    mGestureDetector.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();
                    mHorizontalDistance += Math.abs(curX - mPreviousX);
                    mVerticalDistance += Math.abs(curY - mPreviousY);
                    mPreviousX = curX;
                    mPreviousY = curY;
                    if (mHorizontalDistance > mVerticalDistance) {
                        mGestureDetector.onTouchEvent(ev);
                        return false;
                    }
            }

            return super.onInterceptTouchEvent(ev);
        }


}
