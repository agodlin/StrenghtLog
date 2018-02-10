package com.example.agodlin.strengthlog.ui.workout;

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
import android.widget.EditText;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;
import com.example.agodlin.strengthlog.ui.workout.dummy.DummyContent;
import com.example.agodlin.strengthlog.ui.workout.dummy.DummyContent.DummyItem;

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
public class WorkoutsFragment extends Fragment {
    private static final String TAG = "WorkoutsFragment";
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    List<Date> mItems;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorkoutsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WorkoutsFragment newInstance(int columnCount) {
        WorkoutsFragment fragment = new WorkoutsFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mItems = new ArrayList<Date>(DataManager.workouts.keySet());
        Comparator<Date> c = new Comparator<Date>()
        {
            public int compare(Date u1, Date u2)
            {
                return u1.compareTo(u2);
            }
        };
        Collections.sort(mItems, c);
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        Date date = new Date(10,12,2017);
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(mItems, mListener));

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
                                int month = myDatePicker.getMonth() + 1;
                                int day = myDatePicker.getDayOfMonth();
                                int year = myDatePicker.getYear();
                                Date date = new Date(day, month, year);
                                DataManager.addNewWorkout(date);

                                Comparator<Date> c = new Comparator<Date>()
                                {
                                    public int compare(Date u1, Date u2)
                                    {
                                        return u1.compareTo(u2);
                                    }
                                };
                                int position = Collections.binarySearch(mItems, date, c);
                                if (position < 0) {
                                    Log.d(TAG, "Date insert position : " + position + " value : " + date.toString());
                                    position = position * -1 - 1;
                                    mItems.add(position, date);
                                    recyclerView.getAdapter().notifyItemInserted(position);
                                }

                                Log.d(TAG, "date value : " + date.toString());
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
        void onListFragmentInteraction(Date item);
    }
}