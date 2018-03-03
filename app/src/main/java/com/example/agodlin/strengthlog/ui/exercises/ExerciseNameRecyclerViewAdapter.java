package com.example.agodlin.strengthlog.ui.exercises;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.ui.common.SwipeDeleteAdapter;
import com.example.agodlin.strengthlog.ui.exercises.ExercisesFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link String} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
    * https://github.com/nemanja-kovacevic/recycler-view-swipe-to-delete/
    * https://github.com/ashrithks/SwipeRecyclerView
 */
public class ExerciseNameRecyclerViewAdapter extends SwipeDeleteAdapter<String> {

    public ExerciseNameRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener, int layoutId) {
        super(items, listener, layoutId);
    }
}
