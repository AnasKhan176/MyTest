package com.uae.tambolaapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;

public class MainActivity extends BaseActivity {
    TextToSpeech tts;
    boolean speechStatus = false;
    private ImageView iv_img;
    private String myText = "HI I AM ANAS KHAN AND I AM GOING TO ANDROID SYSTEM ROUND.";
    private Button btn_start, btn_stop;
    private Handler mHandler = new Handler();
    private String[] myTextArr = {"QW", "TH", "O", "L", "EE", "R", "U", "B", "M", "P", "F", "V", "A", "E", "I", "S"};
    private int[] myTextImg = {R.drawable.eight, R.drawable.eleven, R.drawable.first, R.drawable.five, R.drawable.four,
            R.drawable.seven, R.drawable.tweb, R.drawable.seocnd, R.drawable.seocnd, R.drawable.seocnd, R.drawable.six, R.drawable.six,
            R.drawable.third, R.drawable.third, R.drawable.third, R.drawable.nine
    };
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        speechStatus = true;
                    }
                } else
                    Log.e("error", "Initialization Failed!");
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Start Handle*/
                mToastRunnable.run();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Remove a handler*/
                mHandler.removeCallbacks(mToastRunnable);
            }
        });
    }

    private void initView() {
        setActivityTitle("My Interview");
        iv_img = (ImageView) findViewById(R.id.iv_img);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (index < myTextArr.length) {
                    if (myText.contains(myTextArr[index])) {
                        textToSpeech(myTextArr[index], index);
                    }
                    index++;
                } else {
                    index = 0;
                }
                mHandler.postDelayed(this, 2000);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        //Close the Text to Speech Library
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            Log.d("TAG", "TTS Destroyed");
        }
        /*Remove a handler*/
        mHandler.removeCallbacks(mToastRunnable);
        super.onDestroy();
    }

    public void textToSpeech(String myText, int myIndex) {

        if (speechStatus) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(myText, TextToSpeech.QUEUE_FLUSH, null, null);
                    iv_img.setBackgroundResource(myTextImg[myIndex]);
                } else {
                    tts.speak(myText, TextToSpeech.QUEUE_FLUSH, null);
                    iv_img.setBackgroundResource(myTextImg[myIndex]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
