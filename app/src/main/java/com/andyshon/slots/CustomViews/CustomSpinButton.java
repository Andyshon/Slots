package com.andyshon.slots.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.andyshon.slots.Global.GlobalConstants;
import com.andyshon.slots.R;

/**
 * Created by andyshon on 03.07.18.
 */

public class CustomSpinButton extends android.support.v7.widget.AppCompatButton  {

    mButtonListener mListener;

    public interface mButtonListener{
        void onSpin();
    }

    public CustomSpinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListener = (mButtonListener)context;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(R.drawable.button_big_pressed);
                return true;

            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.drawable.button_big);
                if (isEnabled()) {
                    if (GlobalConstants._UserCoins >= GlobalConstants._BetAmount) {
                        mListener.onSpin();
                    }
                    else {
                        Toast.makeText(getContext(), "NOT ENOUGH COINS!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return false;
    }
}
