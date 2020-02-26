package com.example.orgo.homepage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.orgo.R;

import java.util.ArrayList;

public class DiscussionTileAdapter extends ArrayAdapter<DiscussionTile> {

    public DiscussionTileAdapter(Activity context, ArrayList<DiscussionTile> tiles) {
        super(context, 0, tiles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        DiscussionTile tile = getItem(position);

        listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.list_tile_group, parent, false);

        TextView name = listItemView.findViewById(R.id.tileDiscussionAuthor);
        TextView content = listItemView.findViewById(R.id.tileDiscussionContent);

        name.setText(tile.getUserEmail());
        content.setText(tile.getContent());
        return listItemView;
    }
}
