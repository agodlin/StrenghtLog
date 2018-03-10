package com.example.agodlin.strengthlog.ui.weight;

import com.example.agodlin.strengthlog.common.Date;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class BodyWeightItem {
    public final Date date;
    public final float weight;
    public final String comment;

    public BodyWeightItem(BodyWeightItem rhs) {
        date = rhs.date;
        weight = rhs.weight;
        comment = rhs.comment;
    }

    public BodyWeightItem(Date date, float weight, String comment) {
        this.date = date;
        this.weight = weight;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return date.toString() + "\t" + weight;
    }
}
