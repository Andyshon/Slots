package com.andyshon.slots.Activities;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.andyshon.slots.Adapter.ListAdapter;
import com.andyshon.slots.CustomViews.CustomBetHighButton;
import com.andyshon.slots.CustomViews.CustomBetLowButton;
import com.andyshon.slots.CustomViews.CustomSettingsButton;
import com.andyshon.slots.CustomViews.CustomSpinButton;
import com.andyshon.slots.Global.GlobalConstants;
import com.andyshon.slots.R;

import java.util.Collections;
import java.util.List;


public class MainActivity extends BaseActivity implements CustomBetHighButton.mButtonListener, CustomBetLowButton.mButtonListener, CustomSpinButton.mButtonListener,
                                                                CustomSettingsButton.mButtonListener {


    private Button btnBet, btnUserCoins, btnJackpot;
    private ListView list1, list2, list3;
    private CustomSpinButton customSpinButton;
    private CustomSettingsButton customSettingsButton;
    private CustomBetHighButton customBetHighButton;
    private CustomBetLowButton customBetLowButton;
    private ImageView background_trans;
    private PopupWindow mPopupWindow;



    /*
    * todo: Создаем активити
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalConstants.loadUserPreferences(this);
        initUI();

        setAdapters(true);
    }


    /*
    * todo: Инициализация основных UI компонентов
    * */

    private void initUI() {


        background_trans = findViewById(R.id.background_trans);
        background_trans.setVisibility(View.GONE);
        background_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                background_trans.setVisibility(View.GONE);
            }
        });

        list1 = (ListView) findViewById(R.id.list1);
        list2 = (ListView) findViewById(R.id.list2);
        list3 = (ListView) findViewById(R.id.list3);
        btnBet = (Button) findViewById(R.id.btnBet);
        btnBet.setText(String.valueOf(GlobalConstants._BetAmount));
        btnUserCoins = findViewById(R.id.userCoins);
        btnJackpot = findViewById(R.id.Jackpot);

        customSpinButton = findViewById(R.id.btnSpin);
        customSettingsButton = findViewById(R.id.btnSetting);
        customBetHighButton = findViewById(R.id.btnBetHigh);
        customBetLowButton = findViewById(R.id.btnBetLow);

        updateUserCoins();
        updateJackpot();


        /*
        * todo: Запретить прокрутку listView. По хорошему желательно делать через кастомный listView
        * */

        list1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        list2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        list3.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
    }


    /*
    * todo: Перетасовываем и устанавливаем комбинации адаптерам
    * */

    private void setAdapters(boolean isFastMode){

        GlobalConstants.shuffleMainLine();

        if (isFastMode) {
            setAdapterForList1();
            setAdapterForList2();
            setAdapterForList3();
        }
    }


    /*
    * todo: Установка адаптеров для listViews
    * */

    private void setAdapterForList1 () {
        Collections.shuffle(GlobalConstants.combinations);

        GlobalConstants.combinations.set(GlobalConstants.combinations.size() - 2, GlobalConstants.expectedCombination_1);
        List<Integer> l1 = GlobalConstants.getShuffledImages(GlobalConstants.combinations);
        l1.set(GlobalConstants.combinations.size() - 2, GlobalConstants.drawable_1);
        ListAdapter adapter1 = new ListAdapter(MainActivity.this, l1);
        list1.setAdapter(adapter1);
    }

    private void setAdapterForList2 () {
        Collections.shuffle(GlobalConstants.combinations);

        GlobalConstants.combinations.set(GlobalConstants.combinations.size() - 2, GlobalConstants.expectedCombination_2);
        List<Integer> l2 = GlobalConstants.getShuffledImages(GlobalConstants.combinations);
        l2.set(GlobalConstants.combinations.size() - 2, GlobalConstants.drawable_2);
        final ListAdapter adapter2 = new ListAdapter(MainActivity.this, l2);
        list2.setAdapter(adapter2);
    }

    private void setAdapterForList3 () {
        Collections.shuffle(GlobalConstants.combinations);

        GlobalConstants.combinations.set(GlobalConstants.combinations.size() - 2, GlobalConstants.expectedCombination_3);
        List<Integer> l3 = GlobalConstants.getShuffledImages(GlobalConstants.combinations);
        l3.set(GlobalConstants.combinations.size() - 2, GlobalConstants.drawable_3);
        ListAdapter adapter3 = new ListAdapter(MainActivity.this, l3);
        list3.setAdapter(adapter3);
    }


    /*
    * todo: Показываем всплывающее окно после выигрыша
    * */

    private void showWinnerPopUp (final int prize) {

        RelativeLayout MainLayout = (RelativeLayout) findViewById(R.id.MainLayout);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View customView = inflater.inflate(R.layout.popup_winner, null);
        Button btnCoins = customView.findViewById(R.id.btnCoins);
        String text = String.valueOf(prize) + "\ncoins";
        btnCoins.setText(text);

        mPopupWindow = new PopupWindow(customView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.showAtLocation(MainLayout, Gravity.CENTER, 0, 0);

        new CountDownTimer(1500, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                mPopupWindow.dismiss();
                customSpinButton.setEnabled(true);
                customSettingsButton.setEnabled(true);
                customBetHighButton.setClickable(true);
                customBetLowButton.setClickable(true);
            }
        }.start();
    }


    /*
    * todo: Показываем всплывающее окно настроек
    * */

    private void showPopUpSettings () {

        background_trans.setVisibility(View.VISIBLE);

        RelativeLayout MainLayout = (RelativeLayout) findViewById(R.id.MainLayout);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_settings, null);

        Switch switch_mode_fast = customView.findViewById(R.id.switch_mode_fast);
        switch_mode_fast.setChecked(GlobalConstants._IsFastMode);
        switch_mode_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                GlobalConstants._IsFastMode = b;
            }
        });

        mPopupWindow = new PopupWindow(customView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.showAtLocation(MainLayout, Gravity.CENTER, 0, 0);
    }


    /*
    * todo: Определяем сколько монет выиграл игрок: если 0 -> увеличиваем джэкпот, иначе -> увеличиваем монеты игрока и показываем всплывающее окно
    * */

    private void givePrizeToPlayer() {
        int prize = GlobalConstants.getPrize() * GlobalConstants._Rat;
        if (prize == 0) {
            GlobalConstants._Jackpot += GlobalConstants._BetAmount;
            GlobalConstants._UserCoins -= GlobalConstants._BetAmount;
            updateJackpot();
            updateUserCoins();
            customSpinButton.setEnabled(true);
            customSettingsButton.setEnabled(true);
            customBetHighButton.setClickable(true);
            customBetLowButton.setClickable(true);
        }
        else {
            GlobalConstants._UserCoins += prize - GlobalConstants._BetAmount;
            updateUserCoins();
            if (mPopupWindow != null)
                mPopupWindow.dismiss();
            background_trans.setVisibility(View.GONE);
            showWinnerPopUp(prize);
        }
    }


    /*
    * todo: Форматируем текст (запятая как разделитель) и обновляем джэкпот (всегда >= 100,000)
    * */

    private void updateJackpot() {

        String text = String.valueOf(GlobalConstants._Jackpot);
        String p1, p2;
        p1 = text.substring(0, 3);
        p2 = text.substring(3, text.length());
        text = p1 + "," + p2;

        btnJackpot.setText(text);
    }


    /*
    * todo: Обновляет монеты игрока
    * */

    private void updateUserCoins() {
        btnUserCoins.setText(String.valueOf(GlobalConstants._UserCoins));
    }


    /*
    * todo: Поднимаем ставку на 5 монет
    * */

    @Override
    public void onBetHigh() {
        if (customBetHighButton.isClickable()) {
            if (GlobalConstants._BetAmount <= 95) {
                GlobalConstants._BetAmount += 5;
                btnBet.setText(String.valueOf(GlobalConstants._BetAmount));
            }
        }
    }


    /*
    * todo: Понижаем ставку на 5 монет
    * */

    @Override
    public void onBetLow() {
        if (customBetLowButton.isClickable()) {
            if (GlobalConstants._BetAmount > 5) {
                GlobalConstants._BetAmount -= 5;
                btnBet.setText(String.valueOf(GlobalConstants._BetAmount));
            }
        }
    }


    /*
    * todo: Обновляем коэффициент ставки
    * */

    private void updateRat() {
        GlobalConstants._Rat = GlobalConstants._BetAmount / 5;
    }


    /*
    * todo: Вращаем слоты в быстром режиме
    * */

    private void SpinFast () {

        final Handler handler = new Handler();

        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int listViewSize = list1.getAdapter().getCount();

                for (int index = 0; index < listViewSize; index++) {
                    list1.smoothScrollToPositionFromTop(list1.getLastVisiblePosition() + 100, 0, 500);
                    list2.smoothScrollToPositionFromTop(list2.getLastVisiblePosition() + 100, 0, 500);
                    list3.smoothScrollToPositionFromTop(list2.getLastVisiblePosition() + 100, 0, 500);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                    }
                }
                // todo: для плавного выравнивания слотов
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list1.smoothScrollToPosition(GlobalConstants.combinations.size()-1);
                        list2.smoothScrollToPosition(GlobalConstants.combinations.size()-1);
                        list3.smoothScrollToPosition(GlobalConstants.combinations.size()-1);
                    }
                });
            }
        });
        thread1.start();

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread1.join();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            givePrizeToPlayer();
                        }
                    }, 500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
    }


    /*
    * todo: Вращаем слоты по очереди (в медленном режиме). Три потока (слота) начинают по очереди вращаться.
    * todo: Четвертый поток ждёт пока третий слот закончит вращаться и вызывает метод givePrizeToPlayer()
    * */

    private void SpinSlow () {

        final Handler handler = new Handler();

        final Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int listViewSize = list1.getAdapter().getCount();

                for (int index = 0; index < listViewSize; index++) {
                    list3.smoothScrollToPositionFromTop(list2.getLastVisiblePosition() + 100, 0, 500);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                    }
                }
                // todo: для плавного выравнивания слотов
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list3.smoothScrollToPosition(GlobalConstants.combinations.size()-1);
                    }
                });
            }
        });

        final Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread3.join();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            givePrizeToPlayer();
                        }
                    }, 500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int listViewSize = list1.getAdapter().getCount();

                for (int index = 0; index < listViewSize; index++) {
                    list2.smoothScrollToPositionFromTop(list2.getLastVisiblePosition() + 100, 0, 500);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                    }
                }
                // todo: для плавного выравнивания слотов
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list2.smoothScrollToPosition(GlobalConstants.combinations.size()-1);

                        setAdapterForList3();

                        thread3.start();
                        thread4.start();
                    }
                });
            }
        });

        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int listViewSize = list1.getAdapter().getCount();

                for (int index = 0; index < listViewSize; index++) {
                    list1.smoothScrollToPositionFromTop(list1.getLastVisiblePosition() + 100, 0, 500);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                    }
                }
                // todo: для плавного выравнивания слотов
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list1.smoothScrollToPosition(GlobalConstants.combinations.size()-1);

                        setAdapterForList2();

                        thread2.start();
                    }
                });
            }
        });

        setAdapterForList1();

        thread1.start();
    }


    /*
    * todo: Перетасовываем и плавно вращаем слоты
    * */

    @Override
    public void onSpin() {
        updateRat();

        customSpinButton.setEnabled(false);
        customSettingsButton.setEnabled(false);
        customBetHighButton.setClickable(false);
        customBetLowButton.setClickable(false);
        setAdapters(GlobalConstants._IsFastMode);

        if (GlobalConstants._IsFastMode)
            SpinFast();
        else
            SpinSlow();
    }


    /*
    * todo: Показываем всплывающее окно настроек
    * */

    @Override
    public void onShowSettings() {
        showPopUpSettings();
    }


    /*
    * todo: При уходе из активити сохранить данные игрока
    * */

    @Override
    protected void onStop() {
        super.onStop();

        GlobalConstants.saveUserPreferences(this);
    }
}
