package org.techtown.fitnessband;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;

public class UserprofileActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private EditText editText_userName, editText_userAge, editText_userWeight;
    private Button userInfo_button;
    private String name;


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

        getProviderData();

        userInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserINFO user = new UserINFO();
                user.user_name = editText_userName.getText().toString();
                user.user_age = editText_userAge.getText().toString();
                user.user_weight = editText_userWeight.getText().toString();
                user.user_email = auth.getCurrentUser().getEmail();

                database.getReference().child(name).setValue(user);

                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
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
