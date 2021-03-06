package org.techtown.fitnessband;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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

    public static int min, sec;
    private DatabaseReference mDatabase;

    private ListView listView;
    private ListViewAdapter adapter;


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

        // 정지버튼 클릭 시 시간값 파이어베이스에 저장
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

        // 저장버튼 클릭 시 StoreExercise class 로 이동
        store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), StoreExercise.class);
                startActivity(intent);
                finish();
            }
        });

        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);

        Integer set_number = PushupExplain.plus_minus_count;
        String count = PushupExplain.count_edit.getText().toString();

        // 설정한 세트만큼 반복하여 리스트 생성
        for(Integer i=1; i<=set_number; i++) {
            adapter.addItem(i.toString() + " set", "0 / "+ count);
        }



        // 타이머 구현
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


                int div = msg.what; // sendEmptyMessage 의 파라미터로 넘어간 값을 가져옴

                 min = mainTime / 60;
                 sec = mainTime % 60;
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

