package org.techtown.fitnessband;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by e1-572g on 2018-04-20.
 */

// 운동 프래그먼트
public class ExerciseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.exercise_fragment , container, false);

        Button pushUp_button = (Button) view.findViewById(R.id.push_up);
        Button squat_button = (Button)view.findViewById(R.id.squat);

        pushUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PushUpActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        squat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SquatActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });




        return view;
    }
}
