package com.example.agodlin.strengthlog.ui.weight;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.utils.FileIO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BodyWeightFragment extends Fragment {
    private static final String TAG = "BodyWeightFragment";
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView mRecyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BodyWeightFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BodyWeightFragment newInstance(int columnCount) {
        BodyWeightFragment fragment = new BodyWeightFragment();
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
        getActivity().setTitle("Body Weight");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bodyweight_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new BodyWeightRecyclerViewAdapter(DataManager.bodyWeightItems, mListener));


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_weight_in_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button pressed add_weight_in_button");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View viewNumeric = inflater.inflate(R.layout.numeric, null, false);
                final EditText input = (EditText) viewNumeric.findViewById(R.id.numeric);
                builder.setView(input);
                builder.setTitle(R.string.insert_weight)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final String weightString = input.getText().toString();
                                if (weightString.isEmpty())
                                {
                                    Log.d(TAG, "Text Empty, do nothing");
                                    return;
                                }
                                Log.d(TAG, "Weight value : " + weightString);
                                LayoutInflater inflater = LayoutInflater.from(getActivity());
                                View view = inflater.inflate(R.layout.date_picker, null, false);
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
                                                Log.d(TAG, "date value : " + date.toString());
                                                float weight = Float.valueOf(weightString);
                                                BodyWeightItem bodyWeightItem = new BodyWeightItem(-1, date,
                                                        weight, "");
                                                Comparator<BodyWeightItem> c = new Comparator<BodyWeightItem>()
                                                {
                                                    public int compare(BodyWeightItem u1, BodyWeightItem u2)
                                                    {
                                                        return u1.date.compareTo(u2.date);
                                                    }
                                                };
                                                int position = Collections.binarySearch(DataManager.bodyWeightItems, bodyWeightItem, c);
                                                if (position < 0) {
                                                    Log.d(TAG, "BodyWeightItem insert position : " + position + " value : " + bodyWeightItem.toString());
                                                    position = position * -1 - 1;
                                                    DataManager.bodyWeightItems.add(position, bodyWeightItem);
                                                    mRecyclerView.getAdapter().notifyItemInserted(position);
                                                }
                                                else {
                                                    //TODO think if i want to replace it, or ask user to edit the existing value
                                                    Log.i(TAG, "Date already exists");
                                                }
                                                dialog.cancel();
                                            }

                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        }).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_export: {
                Gson gson = new Gson();
                String jsonString = gson.toJson(DataManager.bodyWeightItems);
                Log.i(TAG, "BodyWeightContent json value : " + jsonString);
                String filename = "bodyweight.json";
                FileIO.writePrivate(jsonString.getBytes(), getContext(), filename);
            }
            case R.id.action_import: {
                String filename = "bodyweight.json";
                String jsonString = new String(FileIO.readPrivate(getContext(), filename));
                Type listType = new TypeToken<ArrayList<BodyWeightItem>>(){}.getType();
                DataManager.bodyWeightItems = new Gson().fromJson(jsonString, listType);
            }
            default:
                break;
        }
        return false;
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
        void onListFragmentInteraction(BodyWeightItem item);
    }
}
