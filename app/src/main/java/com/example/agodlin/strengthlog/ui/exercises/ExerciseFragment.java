package com.example.agodlin.strengthlog.ui.exercises;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.ui.common.SwipeDeleteFragmnet;
import com.example.agodlin.strengthlog.ui.exercise.ExerciseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExerciseFragment extends Fragment {
    static String TAG = "ExerciseFragment";
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_EXERCISE_NAME = "exercise-name";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private String exerciseName;
    List<Exercise> mValues;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExerciseFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExerciseFragment newInstance(int columnCount) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            exerciseName = getArguments().getString(ARG_EXERCISE_NAME);
        }
        getActivity().setTitle(exerciseName);
        mValues = DataManager.exercises.get(exerciseName);
        Comparator<Exercise> c = new Comparator<Exercise>()
        {
            public int compare(Exercise u1, Exercise u2)
            {
                return u1.date.compareTo(u2.date);
            }
        };
        Collections.sort(mValues, c);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new ExerciseRecyclerViewAdapter(mValues, 1));
        SwipeDeleteFragmnet swipeDeleteFragmnet = new SwipeDeleteFragmnet(getActivity(), recyclerView);
        swipeDeleteFragmnet.setUpItemTouchHelper();
        swipeDeleteFragmnet.setUpAnimationDecoratorHelper();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_exercise_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.date_picker, null, false);
                final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);

                // so that the calendar view won't appear
                myDatePicker.setCalendarViewShown(false);
                // the alert dialog
                new AlertDialog.Builder(getActivity()).setView(view)
                        .setTitle("Set Date")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int month = myDatePicker.getMonth();
                                int day = myDatePicker.getDayOfMonth();
                                int year = myDatePicker.getYear();
                                Date date = new Date(day, month, year);
                                Exercise exercise = new Exercise(-1, exerciseName, date, new ArrayList<Set>());
                                Comparator<Exercise> c = new Comparator<Exercise>()
                                {
                                    public int compare(Exercise u1, Exercise u2)
                                    {
                                        return u1.date.compareTo(u2.date);
                                    }
                                };
                                int position = Collections.binarySearch(mValues, exercise, c);
                                if (position < 0) {
                                    Log.d(TAG, "Date insert position : " + position + " value : " + date.toString());
                                    position = position * -1 - 1;
                                    mValues.add(position, exercise);
                                    recyclerView.getAdapter().notifyItemInserted(position);
                                }
                                Log.d(TAG, "Text set To : " + exerciseName);
                                dialog.cancel();
                            }

                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Exercise item);
    }
}
