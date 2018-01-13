package com.example.agodlin.strengthlog.ui.exercise_name;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ExerciseNameContent {

    /**
     * An array of sample (dummy) mItems.
     */
    public static final List<ExerciseItem> ITEMS = new ArrayList<ExerciseItem>();

    /**
     * A dummy item representing a piece of content.
     */
    public static class ExerciseItem {
        public final int id;
        public final String content;
        public final String details;

        public ExerciseItem(int id, String content, String details) {
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
