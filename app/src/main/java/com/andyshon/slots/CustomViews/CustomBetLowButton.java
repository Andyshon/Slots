package com.andyshon.slots.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.andyshon.slots.R;

/**
 * Created by andyshon on 03.07.18.
 */

public class CustomBetLowButton extends android.support.v7.widget.AppCompatButton  {

    mButtonListener mListener;

    public interface mButtonListener{
        void onBetLow();
    }

    public CustomBetLowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListener = (mButtonListener)context;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(R.drawable.button_small_presssed);
                return true;

            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.drawable.button_small);
                mListener.onBetLow();
                return true;
        }
        return false;
    }
}
