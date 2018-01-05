package com.example.agodlin.strengthlog.ui.weight.dummy;

import com.example.agodlin.strengthlog.common.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class BodyWeightContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<BodyWeightItem> ITEMS = new ArrayList<BodyWeightItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, BodyWeightItem> ITEM_MAP = new HashMap<String, BodyWeightItem>();

    /**
     * A dummy item representing a piece of content.
     */
    public static class BodyWeightItem {
        public final Date id;
        public final String content;
        public final String details;

        public BodyWeightItem(Date id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
