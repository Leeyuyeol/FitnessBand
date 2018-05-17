package org.techtown.fitnessband;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.common.StringUtils;

// 유저 정보 입력 화면
public class UserprofileActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private EditText editText_userName, editText_userAge, editText_userWeight;
    private Button userInfo_button;
    private String name;
    private ConstraintLayout touch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userInfo_button = (Button) findViewById(R.id.userProFile_store_button);
        editText_userName = (EditText) findViewById(R.id.userEditText_name);
        editText_userAge = (EditText) findViewById(R.id.userEditText_age);
        editText_userWeight = (EditText) findViewById(R.id.userEditText_weight);
        touch = (ConstraintLayout) findViewById(R.id.touch);

        getProviderData();

        userInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserINFO userInfo = new UserINFO();
                userInfo.user_name = editText_userName.getText().toString();
                userInfo.user_age = editText_userAge.getText().toString();
                userInfo.user_weight = editText_userWeight.getText().toString();
                userInfo.user_email = auth.getCurrentUser().getEmail();


                if (userInfo.user_age.equals("") && userInfo.user_weight.equals("")) {
                    Toast.makeText(getApplicationContext(), "나이와 몸무게를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (userInfo.user_weight.equals("")) {
                    Toast.makeText(getApplicationContext(), "몸무게를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(userInfo.user_age.equals("")) {
                    Toast.makeText(getApplicationContext(), "나이를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                    database.getReference().child(name).child("UserProfile").setValue(userInfo);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // 엔터키 입력 제한
        editText_userAge.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        editText_userWeight.setOnKeyListener(new View.OnKeyListener() {
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
                imm.hideSoftInputFromWindow(editText_userAge.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editText_userWeight.getWindowToken(), 0);
            }
        });
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
                editText_userName.setText(name);
                editText_userName.setEnabled(false);
            }
            ;

        }

    }

    // 앱 종료 팝업창
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                String alertTitle = "알림";
                String buttonMessage = "어플을 종료하시겠습니까?";
                String buttonYes = "Yes";
                String buttonNo = "No";

                new AlertDialog.Builder(UserprofileActivity.this)
                        .setTitle(alertTitle)
                        .setMessage(buttonMessage)
                        .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                moveTaskToBack(true);
                                finish();
                            }
                        })
                        .setNegativeButton(buttonNo, null)
                        .show();
        }
        return true;

    }


}

