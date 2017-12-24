package com.example.agodlin.strengthlog.ui.weight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;

public class WeightActivity extends AppCompatActivity implements BodyWeightFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            BodyWeightFragment firstFragment = new BodyWeightFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(BodyWeightContent.BodyWeightItem item) {

    }
}
