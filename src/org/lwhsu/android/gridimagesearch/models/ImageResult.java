package org.lwhsu.android.gridimagesearch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult(final JSONObject json) {
        // new ImageResult(...raw item json...)
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (final JSONException e) {
            e.printStackTrace();
        }
    }

    // Take an array of json images and return arraylist of image results;
    // ImageResult.fromJSONArray([...])
    public static ArrayList<ImageResult> fromJSONArray (final JSONArray array) {
        final ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (final JSONException e){
                e.printStackTrace();
            }

        }
        return results;
    }
}
