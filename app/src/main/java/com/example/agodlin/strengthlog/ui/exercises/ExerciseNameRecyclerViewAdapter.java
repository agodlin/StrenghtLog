package com.example.agodlin.strengthlog.ui.exercises;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
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
public class ExerciseNameRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private final OnListFragmentInteractionListener mListener;
    List<String> mItems;
    List<String> itemsPendingRemoval;
    int lastInsertedIndex; // so we can add some more mItems for testing purposes
    boolean undoOn; // is undo on, you can turn it on from the toolbar menu

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of mItems to pending runnables, so we can cancel a removal if need be

    public ExerciseNameRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener) {
        mItems = items;
        itemsPendingRemoval = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_delete, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TestViewHolder viewHolder = (TestViewHolder)holder;
        final String item = mItems.get(position);
        viewHolder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(item);
                }
            }
        });
        if (itemsPendingRemoval.contains(item)) {
            // we need to show the "undo" state of the row
            viewHolder.itemView.setBackgroundColor(Color.RED);
            viewHolder.titleTextView.setVisibility(View.GONE);
            viewHolder.undoButton.setVisibility(View.VISIBLE);
            viewHolder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // user wants to undo the removal, let's cancel the pending task
                    Runnable pendingRemovalRunnable = pendingRunnables.get(item);
                    pendingRunnables.remove(item);
                    if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
                    itemsPendingRemoval.remove(item);
                    // this will rebind the row in "normal" state
                    notifyItemChanged(mItems.indexOf(item));
                }
            });
        } else {
            // we need to show the "normal" state
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
            viewHolder.titleTextView.setVisibility(View.VISIBLE);
            viewHolder.titleTextView.setText(item);
            viewHolder.undoButton.setVisibility(View.GONE);
            viewHolder.undoButton.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     *  Utility method to add some rows for testing purposes. You can add rows from the toolbar menu.
     */
    public void addItems(int howMany){
        if (howMany > 0) {
            for (int i = lastInsertedIndex + 1; i <= lastInsertedIndex + howMany; i++) {
                //mItems.add("Item " + i);
                notifyItemInserted(mItems.size() - 1);
            }
            lastInsertedIndex = lastInsertedIndex + howMany;
        }
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public void pendingRemoval(int position) {
        final String item = mItems.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(mItems.indexOf(item));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        String item = mItems.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
        }
        if (mItems.contains(item)) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        String item = mItems.get(position);
        return itemsPendingRemoval.contains(item);
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        Button undoButton;

        public TestViewHolder(View parent) {
            super(parent);
            undoButton = (Button) parent.findViewById(R.id.undo_button);
            ViewStub stub = (ViewStub) parent.findViewById(R.id.stub_import);
            stub.setLayoutResource(R.layout.row_view);
            stub.inflate(); // inflate 1st layout
            titleTextView = (TextView)parent.findViewById(R.id.title_text_view);
        }
    }
}
