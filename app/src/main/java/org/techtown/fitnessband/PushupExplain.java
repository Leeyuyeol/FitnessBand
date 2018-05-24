package org.techtown.fitnessband;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class PushupExplain extends AppCompatActivity {

    private ImageButton exit_button, start_button, plus_button, minus_button;
    private RelativeLayout touch;
    public static EditText set_edit, count_edit, rest_edit;

    public static Integer plus_minus_count =0;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushup_explain);

        Toolbar toolbar = (Toolbar) findViewById(R.id.pushup_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        exit_button = (ImageButton) findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start_button = (ImageButton) findViewById(R.id.start_button);

        // 재생버튼 클릭 시 다이얼로그 운동세팅 설정 화면이 뜸.
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PushupExplain.this); //다이얼로그 생성
                dialog.setContentView(R.layout.exercise_setting); //다이얼로그 레이아웃 등록


                plus_button = (ImageButton)dialog.findViewById(R.id.plus_button);
                minus_button = (ImageButton)dialog.findViewById(R.id.minus_button);
                set_edit = (EditText)dialog.findViewById(R.id.set_edit);
                count_edit = (EditText)dialog.findViewById(R.id.count_edit);
                rest_edit = (EditText)dialog.findViewById(R.id.rest_edit);
                touch = (RelativeLayout) dialog.findViewById(R.id.touch_layout) ;

                Button exercise_start = (Button) dialog.findViewById(R.id.exercise_start);
                exercise_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(set_edit.getText().toString().equals("")) {
                          Toast.makeText(getApplicationContext(),"세트값을 설정해주세요.",Toast.LENGTH_SHORT).show();
                        } else if(count_edit.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "운동개수를 설정 해주세요.", Toast.LENGTH_SHORT).show();
                        } else if(rest_edit.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "휴식시간을 설정 해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), PushupCounting.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                plus_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        plus_minus_count++;
                        set_edit.setText(plus_minus_count.toString());
                    }
                });

                minus_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(plus_minus_count == 0) {
                            return;
                        } else {
                            plus_minus_count--;
                        }

                        set_edit.setText(plus_minus_count.toString());
                    }
                });

                // 엔터키 입력 제한
                count_edit.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(keyCode == event.KEYCODE_ENTER) {
                            return true;
                        }
                        return false;
                    }
                });

                rest_edit.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(keyCode == event.KEYCODE_ENTER) {
                            return true;
                        }
                        return false;
                    }
                });

                // 레이아웃 클릭시 키보드가 내려가고 editText클릭시 키보드가 올라옴
                touch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(set_edit.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(count_edit.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(rest_edit.getWindowToken(), 0);
                    }
                });


                dialog.show();

            }
        });


    }

}

