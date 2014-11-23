package org.lwhsu.android.gridimagesearch.adapters;

import java.util.List;

import org.lwhsu.android.gridimagesearch.models.ImageResult;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultsAdapter(final Context context, final List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
