package org.techtown.fitnessband;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


// 푸쉬업 카운팅 화면
public class PushUpActivity extends AppCompatActivity {

    private Handler mHandler;
    private TextView fitnessTime;
    private Button start, stop;

    private int mainTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);

        fitnessTime = (TextView)findViewById(R.id.fitness_time);
        start = (Button)findViewById(R.id.start_button);
        stop = (Button)findViewById(R.id.stop_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(1);
                start.setEnabled(false);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                start.setEnabled(true);
            }
        });



        // 타이머
        mHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        int div = msg.what; // sendEmptyMessage 의 파라미터로 넘어간 값을 가져옴

                        int min = mainTime / 60;
                        int sec = mainTime % 60;
                        String strTime = String.format("%02d : %02d", min , sec);

                        this.sendEmptyMessageDelayed(0, 1000);
                        fitnessTime.setText(strTime);
                fitnessTime.invalidate();
                mainTime++;
            }
        };

    }




}
