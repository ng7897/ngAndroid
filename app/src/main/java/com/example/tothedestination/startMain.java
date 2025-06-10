package com.example.tothedestination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class startMain extends AppCompatActivity {

    LottieAnimationView lottie;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        lottie = findViewById(R.id.lottie);
        //להזיז את האנימציה
        //translationX- מזיז את האנימציה 2000 פיקסלים על ציר X
        //setDuration-משך תנועת ההזזה תיהיה 2 שניות
        //etStartDelay- לפני תחילת ההזזה צריך לחכות 2.9 שניות(2900 מילישניות)
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        //Handler- כלי המאפשר לתזמן קוד שיתבצע בעתיד
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(getApplicationContext(), finalstartMain.class);
                startActivity(intent1);
            }

            //מעבר למסך הבא אחרי 5000 מילישניות, 5 שניות
        }, 5000);


    }

}