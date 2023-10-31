package uk.ac.gre.wm50.contactdatabase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class AvatarAdapter extends ArrayAdapter<Integer> {
    private Integer[] imageResources;
    private int imageSize;

    public AvatarAdapter(Context context, int resource, Integer[] objects, int imageSize) {
        super(context, resource, objects);
        this.imageResources = objects;
        this.imageSize = imageSize;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    private View getImageForPosition(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(imageSize, imageSize));
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageResources[position]);
        return imageView;
    }
}
