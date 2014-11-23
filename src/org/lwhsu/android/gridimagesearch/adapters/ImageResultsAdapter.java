package org.lwhsu.android.gridimagesearch.adapters;

import java.util.List;

import org.lwhsu.android.gridimagesearch.R;
import org.lwhsu.android.gridimagesearch.models.ImageResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultsAdapter(final Context context, final List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ImageResult imageInfo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        final ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        // Clear out image from last time
        ivImage.setImageResource(0);
        // Populate title and remote download image url
        tvTitle.setText(imageInfo.title);
        // Remotely download the image data in the background (with Picasso)
        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
        // Return the completed view to be displayed
        return convertView;
    }
}
