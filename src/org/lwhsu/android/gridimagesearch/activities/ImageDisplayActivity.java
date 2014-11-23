package org.lwhsu.android.gridimagesearch.activities;

import org.lwhsu.android.gridimagesearch.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        // Remove the actionbar on the image display activity
        getActionBar().hide();
        // Pull out the url from the intent
        final String url = getIntent().getStringExtra("url");
        // Find the image view
        final ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        // Load the image url into the imageview using picasso
        Picasso.with(this).load(url).into(ivImageResult);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
        return true;
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
