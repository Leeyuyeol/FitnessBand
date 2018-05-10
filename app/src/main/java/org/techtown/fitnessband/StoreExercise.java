package org.techtown.fitnessband;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by e1-572g on 2018-05-09.
 */

public class StoreExercise extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private TextView timer;
    private Button non_store;


    private String name;
    private String count = "Exercise_Counting";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_exercise);

        timer = (TextView)findViewById(R.id.time_text);
        non_store = (Button)findViewById(R.id.non_store);

        getProviderData();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(name);

        databaseReference.child(count).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserINFO user = dataSnapshot.getValue(UserINFO.class);
                String message = user.user_exercise_counting;
                timer.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        non_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            };

        }

    }

}
