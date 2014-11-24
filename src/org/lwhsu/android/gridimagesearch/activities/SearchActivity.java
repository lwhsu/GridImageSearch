package org.lwhsu.android.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwhsu.android.gridimagesearch.R;
import org.lwhsu.android.gridimagesearch.adapters.ImageResultsAdapter;
import org.lwhsu.android.gridimagesearch.models.ImageResult;
import org.lwhsu.android.gridimagesearch.models.ImageSearchSetting;

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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private ImageSearchSetting searchSetting;

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

    private static String genSearchUrl(final String query, final ImageSearchSetting setting, final int page) {
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + page * 8;
        if (setting != null) {
            if (setting.size != null || setting.size != "") {
                url += "&imgsz=" + setting.size;
            }
            if (setting.color != null || setting.color != "") {
                url += "&imgcolor=" + setting.color;
            }
            if (setting.type != null || setting.type != "") {
                url += "&imgtype=" + setting.type;
            }
            if (setting.siteFilter != null || setting.siteFilter != "") {
                url += "&as_sitesearch=" + setting.siteFilter;
            }
        }
        return url;
    }

    protected void customLoadMoreDataFromApi(final int page) {
        if (page > 7) {
            return; // maximum results count is 64
        }
        final String query = etQuery.getText().toString();
        final AsyncHttpClient client = new AsyncHttpClient();
        final String searchUrl = genSearchUrl(query, searchSetting, page);
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    // When you make to the adapter, it does modify the underlying data
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResultsJson.toString());
            }
        });
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
        gvResults.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public void onLoadMore(final int page, final int totalItemsCount) {
                customLoadMoreDataFromApi(page);
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
        final String searchUrl = genSearchUrl(query, searchSetting, 0);
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

    private final int REQUEST_CODE = 20;

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            final Intent i = new Intent(this, SettingActivity.class);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            searchSetting = (ImageSearchSetting) data.getSerializableExtra("searchSetting");
            Toast.makeText(this, searchSetting.toToastString(), Toast.LENGTH_SHORT).show();
        }
    }
}
