package org.lwhsu.android.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwhsu.android.gridimagesearch.R;
import org.lwhsu.android.gridimagesearch.adapters.ImageResultsAdapter;
import org.lwhsu.android.gridimagesearch.models.ImageResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        // Creates the data source
        imageResults = new ArrayList<ImageResult>();
        // Atta hes the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        // Link the adapter to the adapterview (gridview)
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                // Launch the image display activity
                // Create an intent
                final Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // Get the image result to display
                final ImageResult result = imageResults.get(position);
                // Pass image result into the intent
                i.putExtra("url", result.fullUrl);
                // Launch the new activity
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    // Fired whenever the button is pressed (android:onclick property)
    public void onImageSearch(final View v) {
        final String query = etQuery.getText().toString();

        final AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
        final String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    // When you make to the adapter, it does modify the underlying data
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResultsJson.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
