package com.example.agodlin.strengthlog.ui.exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.nfc.Tag;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.ui.exercise.ExerciseFragment.OnListFragmentInteractionListener;
import com.example.agodlin.strengthlog.ui.exercise.dummy.DummyContent;
import com.example.agodlin.strengthlog.ui.exercise.dummy.DummyContent.DummyItem;
import com.example.agodlin.strengthlog.ui.exercise_name.ExerciseNameContent;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ExerciseAdapter";

    private final OnListFragmentInteractionListener mListener;
    private String exercise;
    List<Exercise> tmp;
    public ExerciseRecyclerViewAdapter(List<Exercise> exercises, OnListFragmentInteractionListener listener) {
        mListener = listener;
        this.tmp = exercises;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_exercise, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Exercise exerciseDay = tmp.get(position);
        holder.header.setText(exerciseDay.date.toString());
        holder.footer.setText("comment");
        holder.recyclerView.setAdapter(new ExerciseCardRecyclerViewAdapter(exerciseDay.sets, mListener));
        holder.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.context);
                final EditText input = new EditText(holder.context);
                builder.setView(input);
                builder.setTitle(R.string.insert_exercise_name)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = input.getText().toString();
                                if (name.isEmpty())
                                {
                                    Log.d(TAG, "Text Empty, do nothing");
                                    return;
                                }
                                Log.d(TAG, "Text set To : " + name);
                                Exercise exercise = new Exercise(name, new Date(1,1,1), new ArrayList<Set>());
                                DataManager.exercises.get(holder.header.getText()).add(exercise);
                                holder.recyclerView.getAdapter().notifyItemInserted(DataManager.exercises.get(holder.header.getText()).size()-1);
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
    }

    @Override
    public int getItemCount() {
        return tmp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final RecyclerView recyclerView;
        public final TextView header;
        public final TextView footer;
        public final ImageButton imageButton;
        public final Context context;
        public ViewHolder(View view, Context context) {
            super(view);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.list);
            header = (TextView)itemView.findViewById(R.id.card_header);
            footer = (TextView)itemView.findViewById(R.id.card_footer);
            imageButton = (ImageButton) itemView.findViewById(R.id.card_menu);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.context = context;
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}
