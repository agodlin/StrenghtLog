package com.example.agodlin.strengthlog.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.example.agodlin.strengthlog.R;

/**
 * Created by agodlin on 3/9/2018.
 */

public class SwipeViewHolder extends RecyclerView.ViewHolder {
    public final RelativeLayout viewBackground, viewForeground;

    public SwipeViewHolder(View itemView, int layout) {

        super(itemView);
        viewBackground = (RelativeLayout)itemView.findViewById(R.id.view_background);
        viewForeground = (RelativeLayout)itemView.findViewById(R.id.view_foreground);
        ViewStub stub = (ViewStub) itemView.findViewById(R.id.stub_import);
        stub.setLayoutResource(layout);
        stub.inflate(); // inflate 1st layout
    }
}
