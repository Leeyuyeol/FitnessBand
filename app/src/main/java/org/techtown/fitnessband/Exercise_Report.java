package org.techtown.fitnessband;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by e1-572g on 2018-05-20.
 */

public class Exercise_Report extends AppCompatActivity {

    private ImageButton exit_button;
    private LinearLayout calendar_button;
    private TextView text_day1, text_day2, text_day3, text_day4, text_day5, text_day6, text_day7;
    private TextView total_exercise , total_kcal, total_sec;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getProviderData();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(name);

        text_day1 = (TextView) findViewById(R.id.text_day1);
        text_day2 = (TextView) findViewById(R.id.text_day2);
        text_day3 = (TextView) findViewById(R.id.text_day3);
        text_day4 = (TextView) findViewById(R.id.text_day4);
        text_day5 = (TextView) findViewById(R.id.text_day5);
        text_day6 = (TextView) findViewById(R.id.text_day6);
        text_day7 = (TextView) findViewById(R.id.text_day7);

        total_exercise = (TextView) findViewById(R.id.total_exercise);
        total_kcal = (TextView) findViewById(R.id.total_kcal);
        total_sec = (TextView) findViewById(R.id.total_second);

        calendar_button = (LinearLayout) findViewById(R.id.calendar_button);
        exit_button = (ImageButton) findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Material_Calendar.class);
                startActivity(intent);
                finish();
            }
        });

        total_exercise.setText(UserINFO.user_exercise_total.toString());
        total_kcal.setText(UserINFO.user_exercise_kcal.toString());
        total_sec.setText(UserINFO.getUser_exercise_time.toString()+ "s");

        String weekDay;

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime()); // weekDay는 현재 요일을 반환함

        switch (weekDay) {
            case "일요일" :
                Integer day1 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day1.setText(day1.toString());
                text_day1.setTextColor(getResources().getColor(R.color.light_blue));
                break;

            case "월요일" :
                Integer day2 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day2.setText(day2.toString());
                text_day2.setTextColor(getResources().getColor(R.color.light_blue));
                break;

            case "화요일" :
                Integer day3 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day3.setText(day3.toString());
                text_day3.setTextColor(getResources().getColor(R.color.light_blue));
                break;

            case "수요일" :
                Integer day4 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day4.setText(day4.toString());
                text_day4.setTextColor(getResources().getColor(R.color.light_blue));
                break;

            case "목요일" :
                Integer day5 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day5.setText(day5.toString());
                text_day5.setTextColor(getResources().getColor(R.color.light_blue));
                break;

            case "금요일" :
                Integer day6 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day6.setText(day6.toString());
                text_day6.setTextColor(getResources().getColor(R.color.light_blue));
                break;

            case "토요일" :
                Integer day7 = calendar.get(Calendar.DAY_OF_MONTH);
                text_day7.setText(day7.toString());
                text_day7.setTextColor(getResources().getColor(R.color.light_blue));
                break;

        }

        Integer day1 = calendar.get(Calendar.DAY_OF_MONTH);
        Integer day2= calendar.get(Calendar.DAY_OF_MONTH)+ 1;
        Integer day3 = calendar.get(Calendar.DAY_OF_MONTH)+ 2;
        Integer day4 = calendar.get(Calendar.DAY_OF_MONTH)+ 3;
        Integer day5 = calendar.get(Calendar.DAY_OF_MONTH)+ 4;
        Integer day6 = calendar.get(Calendar.DAY_OF_MONTH)+ 5;
        Integer day7 = calendar.get(Calendar.DAY_OF_MONTH)+ 6;


        text_day1.setText(day1.toString());
        text_day1.setTextColor(getResources().getColor(R.color.light_blue)); // 현재 요일 값 파랑색 표시
        text_day2.setText(day2.toString());
        text_day3.setText(day3.toString());
        text_day4.setText(day4.toString());
        text_day5.setText(day5.toString());
        text_day6.setText(day6.toString());
        text_day7.setText(day7.toString());




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
