package com.andyshon.slots.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.andyshon.slots.R;

public class EntryActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }


    @Override
    protected void onResume() {
        super.onResume();

        moveToGameActivity();
    }


    /*
    * todo: Переход в игровое активити
    * */

    private void moveToGameActivity() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(EntryActivity.this, MainActivity.class));
            }
        }, 3000);
    }
}
