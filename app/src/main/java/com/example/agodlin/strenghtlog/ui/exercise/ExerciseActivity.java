package com.example.agodlin.strenghtlog.ui.exercise;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.agodlin.strenghtlog.R;

public class ExerciseActivity extends AppCompatActivity implements ExerciseFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ExerciseFragment firstFragment = new ExerciseFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(ExerciseContent.ExerciseItem item) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Pressed : " + item.toString(), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
