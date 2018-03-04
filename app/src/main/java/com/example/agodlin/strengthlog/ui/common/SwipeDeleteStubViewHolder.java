package com.example.agodlin.strengthlog.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;

/**
 * Created by agodlin on 3/4/2018.
 */

public class SwipeDeleteStubViewHolder extends RecyclerView.ViewHolder {

    public ViewStub stub;
    public Button undoButton;

    public SwipeDeleteStubViewHolder(View parent, int layoutId) {
        super(parent);
        undoButton = (Button) parent.findViewById(R.id.undo_button);
        stub = (ViewStub) parent.findViewById(R.id.stub_import);
        stub.setLayoutResource(layoutId);
        stub.inflate(); // inflate 1st layout
    }
}
