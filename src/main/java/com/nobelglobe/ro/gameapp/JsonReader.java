package com.nobelglobe.ro.gameapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class JsonReader {
    private JSONArray borders1;
    private JSONArray borders2;

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            char as = (char) cp;
            sb.append(as);
        }
        return sb.toString();
    }

    public JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray jsonArray = new JSONArray(jsonText);
            return jsonArray;
        } finally {
            is.close();
        }
    }

    public List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            list.add(value);
        }
        return list;
    }


    public List<String> call(String origin, String destination) throws IOException {
        JSONArray jsonArray = readJsonFromUrl("https://raw.githubusercontent.com/mledoze/countries/master/countries.json");
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        ArrayList<Object> listdata = (ArrayList<Object>) toList(jsonArray);


        for (int i = 0; i < listdata.size(); i++) {
            JSONObject jsonObject = (JSONObject) listdata.get(i);

            if (jsonObject.getString("cca3").equals(origin.toUpperCase(Locale.ROOT))) {
                borders1 = jsonObject.getJSONArray("borders");
                for (int ii = 0; ii < borders1.length(); ii++) {
                    list1.add(borders1.get(ii).toString());
                }
            }

            if (jsonObject.getString("cca3").equals(destination.toUpperCase(Locale.ROOT))) {
                borders2 = jsonObject.getJSONArray("borders");
                for (int j = 0; j < borders2.length(); j++) {
                    list2.add(borders2.get(j).toString());
                }
            }

        }

        System.out.println("list 1" + list1);
        System.out.println("list 2 " + list2);
        System.out.println(getIntersectOfLists2(list1, list2));

        return getIntersectOfLists2(list1, list2);

    }

    private List<String> getIntersectOfLists2(List<String> list1, List<String> list2) {
        list1.retainAll(list2);

        return list1;
    }


}