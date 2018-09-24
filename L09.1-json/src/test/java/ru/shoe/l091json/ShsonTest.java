package ru.shoe.l091json;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;


public class ShsonTest {

    @Test
    public void toJson() {
        MyObject obj = new MyObject();
        Gson gson = new Gson();
        Shson shson = new Shson();
        String jsonGson = gson.toJson(obj);
        String jsonShson = shson.toJson(obj);
        assertEquals(jsonGson,jsonShson);
    }
}