package org.techtown.fitnessband;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
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

        userInfo_button = (Button)findViewById(R.id.userProFile_store_button);
        editText_userName = (EditText)findViewById(R.id.userEditText_name);
        editText_userAge = (EditText)findViewById(R.id.userEditText_age);
        editText_userWeight = (EditText)findViewById(R.id.userEditText_weight);
        touch = (ConstraintLayout)findViewById(R.id.touch);

        getProviderData();

        userInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserINFO userInfo = new UserINFO();
                userInfo.user_name = editText_userName.getText().toString();
                userInfo.user_age = editText_userAge.getText().toString();
                userInfo.user_weight = editText_userWeight.getText().toString();
                userInfo.user_email = auth.getCurrentUser().getEmail();


                if(userInfo.user_age.length() == 0) {
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if(userInfo.user_weight.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요." , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                    database.getReference().child(name).setValue(userInfo);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        // 레이아웃 클릭시 키보드가 내려가고 editText클릭시 키보드가 올라옴
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
            };

        }

    }
}
