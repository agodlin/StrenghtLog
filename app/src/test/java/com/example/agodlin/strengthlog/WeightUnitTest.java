package com.example.agodlin.strengthlog;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
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
        assertEquals(item.date, itemParsed.date);
    }
    @Test
    public void jsonList() throws Exception {
        List<Set> list = new ArrayList<>();
        list.add(new Set(5, 100));
        list.add(new Set(4, 102));
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        assertNotNull(jsonString);
        Type listType = new TypeToken<ArrayList<Set>>(){}.getType();
        List<Set> list2 = new Gson().fromJson(jsonString, listType);
        assertArrayEquals(list.toArray(), list2.toArray());
    }
}
