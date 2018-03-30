package com.example.agodlin.strengthlog.ui.workout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.ui.common.RecyclerItemTouchHelper;
import com.example.agodlin.strengthlog.ui.exercise.ExerciseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WorkoutFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    String TAG = "WorkoutFragment";
    public static final String ARG_WORKOUT_DATE = "workout-date";
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    Date mDate;
    List<Exercise> mValues;
    List<String> mNames;
    ExerciseRecyclerViewAdapter adapter;
    RelativeLayout relativeLayout;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorkoutFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WorkoutFragment newInstance(int columnCount) {
        WorkoutFragment fragment = new WorkoutFragment();
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
            mDate = (Date)getArguments().getSerializable(ARG_WORKOUT_DATE);
        }
        getActivity().setTitle(mDate.toString());
        mValues = DataManager.read(mDate);
        mNames = new ArrayList<>();
        for(Exercise e : mValues)
            mNames.add(e.name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        relativeLayout = (RelativeLayout)view.findViewById(R.id.fragment_exercise_layout);
        adapter = new ExerciseRecyclerViewAdapter(getActivity(), mValues, 0, relativeLayout);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_exercise_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Exercise");
                List<String> subset = DataManager.getNames();
                subset.removeAll(mNames);
                final String[] exercisesArr = new String[subset.size()];
                subset.toArray(exercisesArr);
                builder.setItems(exercisesArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Click on " + exercisesArr[which]);
                        String name = exercisesArr[which];
                        if (mNames.contains(name)) return;
                        Exercise exercise = new Exercise(name, mDate, new ArrayList<Set>());
                        mValues.add(0, exercise);
                        mNames.add(name);
                        DataManager.add(exercise);
                        recyclerView.getAdapter().notifyItemInserted(0);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ExerciseRecyclerViewAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = mValues.get(viewHolder.getAdapterPosition()).toString();

            // backup of removed item for undo purpose
            final Exercise deletedItem = mValues.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                    DataManager.add(deletedItem);
                    mNames.add(deletedItem.name);

                }
            });
            mNames.remove(deletedItem.name);
            DataManager.delete(deletedItem);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
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
