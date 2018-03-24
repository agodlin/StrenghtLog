package com.example.agodlin.strengthlog.db;

import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agodlin on 3/24/2018.
 */

public class DataCache {
    private List<BodyWeightItem> bodyWeightItems = new ArrayList<>();


    public void setBodyWeightItems(List<BodyWeightItem> bodyWeightItems)
    {
        this.bodyWeightItems = bodyWeightItems;
    }

    public List<BodyWeightItem> getBodyWeightItems()
    {
        return bodyWeightItems;
    }
}
