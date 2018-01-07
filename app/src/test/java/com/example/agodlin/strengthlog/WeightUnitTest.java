package com.example.agodlin.strengthlog;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by agodlin on 1/6/2018.
 */

public class WeightUnitTest {
    @Test
    public void json() throws Exception {
        Date date = new Date(1,1,2018);
        BodyWeightContent.BodyWeightItem item = new BodyWeightContent.BodyWeightItem(date, "test1", "test2");
        Gson gson = new Gson();
        String jsonString = gson.toJson(item);
        assertNotNull(jsonString);
        BodyWeightContent.BodyWeightItem itemParsed = gson.fromJson(jsonString, BodyWeightContent.BodyWeightItem.class);
        assertNotNull(itemParsed);
        assertEquals(item.id, itemParsed.id);
    }
}
