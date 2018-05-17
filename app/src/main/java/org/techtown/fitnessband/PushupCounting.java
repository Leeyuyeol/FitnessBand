package org.techtown.fitnessband;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.INVISIBLE;

/**
 * Created by e1-572g on 2018-05-07.
 */

public class PushupCounting extends AppCompatActivity {

    private ImageButton start_button, stop_button, store_button;
    private TextView timer_text;

    private Handler mHandler;
    private int mainTime = 0;
    private String strTime;
    private String name;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushup_counting);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        start_button = (ImageButton) findViewById(R.id.start_button);
        stop_button = (ImageButton) findViewById(R.id.stop_button2);
        store_button = (ImageButton)findViewById(R.id.store_button);
        timer_text = (TextView)findViewById(R.id.timer_text);



        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_button.setVisibility(INVISIBLE);
                stop_button.setVisibility(View.VISIBLE);
                stop_button.setSelected(true);
                store_button.setEnabled(false);

                mHandler.sendEmptyMessage(1);
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_button.setVisibility(INVISIBLE);
                start_button.setVisibility(View.VISIBLE);
                start_button.setSelected(true);
                store_button.setEnabled(true);

                getProviderData();
                UserINFO user = new UserINFO();
                user.user_exercise_counting = strTime;

                mDatabase.child(name).child("Exercise_Counting").setValue(user); // 파이어베이스에 시간 값 쓰기
                mHandler.removeMessages(0);

            }
        });


        store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), StoreExercise.class);
                startActivity(intent);
                finish();
            }
        });


        // 타이머 구현
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


                int div = msg.what; // sendEmptyMessage 의 파라미터로 넘어간 값을 가져옴

                int min = mainTime / 60;
                int sec = mainTime % 60;
                strTime = String.format("%02d : %02d", min , sec);

                this.sendEmptyMessageDelayed(0, 1000);
                timer_text.setText(strTime);
                timer_text.invalidate();
                mainTime++;
            }
        };
    }

    // 공급업체에서 사용자프로필 가져오기.
    private void getProviderData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                // String providerId = profile.getProviderId();

                // UID specific to the provider
                // String uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                // String email = profile.getEmail();
                // Uri photoUrl = profile.getPhotoUrl();

            };

        }

    }
}

