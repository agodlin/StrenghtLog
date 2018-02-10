package com.example.agodlin.strengthlog.ui.exercises;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Exercise;

public class ExercisesActivity extends AppCompatActivity implements ExercisesFragment.OnListFragmentInteractionListener,
ExerciseFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_name);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ExercisesFragment firstFragment = new ExercisesFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(String item) {
        Fragment firstFragment = new ExerciseFragment();
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        getIntent().putExtra(ExerciseFragment.ARG_EXERCISE_NAME, item);
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, firstFragment).addToBackStack(null).commit();
    }

    @Override
    public void onListFragmentInteraction(Exercise item) {

    }
}
