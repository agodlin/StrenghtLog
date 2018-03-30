package com.example.agodlin.strengthlog.ui.exercise;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.ui.common.RecyclerItemTouchHelper;
import com.example.agodlin.strengthlog.ui.common.SwipeViewHolder;
import com.example.agodlin.strengthlog.ui.exercises.ExerciseFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Exercise} and makes a call to the
 * specified {@link ExerciseFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ExerciseAdapter";
    private List<Exercise> mItems;

    int viewType;
    final RelativeLayout relativeLayout;
    Activity activity;
    public ExerciseRecyclerViewAdapter(Activity activity, List<Exercise> exercises, int viewType, RelativeLayout relativeLayout) {
        this.viewType = viewType;
        mItems = exercises;
        this.relativeLayout = relativeLayout;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ExerciseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_swipe_delete, parent, false);
        return new ViewHolder(view, parent.getContext(), viewType);
    }

    @Override
    public void onBindViewHolder(final ExerciseRecyclerViewAdapter.ViewHolder holder, int position) {
        final Exercise exerciseDay = mItems.get(position);
        String header = holder.viewType == 0 ? exerciseDay.name : exerciseDay.date.toString();

        holder.header.setText(header);
        holder.footer.setText("comment");
        final ExerciseCardRecyclerViewAdapter adapter = new ExerciseCardRecyclerViewAdapter(exerciseDay.sets);
        holder.recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT,
                new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
                    {
                        if (viewHolder instanceof ExerciseCardRecyclerViewAdapter.ViewHolder) {
                            // get the removed item name to display it in snack bar
                            String name = exerciseDay.sets.get(viewHolder.getAdapterPosition()).toString();

                            // backup of removed item for undo purpose
                            final Set deletedItem = exerciseDay.sets.get(viewHolder.getAdapterPosition());
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
                                    DataManager.updateExercise(exerciseDay);

                                }
                            });
                            DataManager.updateExercise(exerciseDay);
                            snackbar.setActionTextColor(Color.YELLOW);
                            snackbar.show();
                        }
                    }
                });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(holder.recyclerView);

        holder.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)v;
                Log.i(TAG, "header pressed " + textView.getText());
            }
        });

        holder.footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)v;
                Log.i(TAG, "footer pressed " + textView.getText());
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "ImageButton pressed ");
                LayoutInflater inflater = LayoutInflater.from(holder.context);
                View view = inflater.inflate(R.layout.add_set_layout, null);
                final EditText reps = (EditText)view.findViewById(R.id.reps);
                final EditText weights = (EditText)view.findViewById(R.id.weight);
                final ToggleButton sw = (ToggleButton) view.findViewById(R.id.switch1);
                final EditText sets = (EditText)view.findViewById(R.id.set);
                sets.setText("1");
                if (exerciseDay.sets.size() > 0)
                {
                    int lastIndex = exerciseDay.sets.size() - 1;
                    reps.setText(String.valueOf(exerciseDay.sets.get(lastIndex).reps));
                }
                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            sw.setTextOn("lbs");
                        } else {
                            sw.setTextOff("kg");
                        }
                    }
                });
                new AlertDialog.Builder(holder.context).setTitle("Add Set").setView(view)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String repsValue = reps.getText().toString();
                            String weightsValue = weights.getText().toString();
                            String setsValue = sets.getText().toString();
                            if (repsValue.isEmpty() || weightsValue.isEmpty() || setsValue.isEmpty()) {
                                dialog.cancel();
                                return;
                            }
                            boolean useLBS = sw.isChecked();
                            Log.i(TAG, "reps " + repsValue);
                            Log.i(TAG, "weight " + weightsValue);
                            double weigthKgs = useLBS ? Double.parseDouble(weightsValue)*0.453592 : Double.parseDouble(weightsValue);
                            List<Integer> reps = new ArrayList<>();
                            if (repsValue.contains("-"))
//                                reps = Arrays.asList(repsValue.split("-")).stream()
//                                        .map(s -> Integer.parseInt(s))
//                                        .collect(Collectors.toList());
                            {
                                for(String v :repsValue.split("-"))
                                    reps.add(Integer.parseInt(v));
                            }
                            if (repsValue.contains(","))
//                                reps = Arrays.asList(repsValue.split(",")).stream()
//                                        .map(s -> Integer.parseInt(s))
//                                        .collect(Collectors.toList());
                            {
                                for(String v :repsValue.split(","))
                                    reps.add(Integer.parseInt(v));
                            }
                            if (!reps.isEmpty())
                            {
                                for(int i = 0 ; i < reps.size(); i++) {
                                    exerciseDay.sets.add(new Set(reps.get(i), weigthKgs));
                                    holder.recyclerView.getAdapter().notifyItemInserted(exerciseDay.sets.size() - 1);
                                }
                            }
                            else {
                                for(int i = 0 ; i < Integer.parseInt(setsValue); i++) {
                                    exerciseDay.sets.add(new Set(Integer.parseInt(repsValue), weigthKgs));
                                    holder.recyclerView.getAdapter().notifyItemInserted(exerciseDay.sets.size() - 1);
                                }
                            }

                            DataManager.updateExercise(exerciseDay);
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
    }

    public void removeItem(int position) {
        mItems.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Exercise item, int position) {
        mItems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class ViewHolder extends SwipeViewHolder {
        public final RecyclerView recyclerView;
        public final TextView header;
        public final TextView footer;
        public final ImageButton imageButton;
        public final Context context;
        public final int viewType;

        public ViewHolder(View view, Context context, int viewType) {
            super(view, R.layout.fragment_exercise);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.list);
            header = (TextView)itemView.findViewById(R.id.card_header);
            footer = (TextView)itemView.findViewById(R.id.card_footer);
            imageButton = (ImageButton) itemView.findViewById(R.id.card_menu);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.context = context;
            this.viewType = viewType;
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}
