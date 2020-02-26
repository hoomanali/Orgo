package com.example.orgo.homepage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.orgo.R;

import java.util.ArrayList;

/**
 * Adapter for list of home page tiles to attach to a ListView.
 */
public class TileAdapter extends ArrayAdapter<Tile> {

    /**
     * Creates a new Tile Adapter for creating ListView tiles, list must be sorted.
     * @param context Current activity context.
     * @param tiles ArrayList of Tile objects.
     */
    public TileAdapter(Activity context, ArrayList<Tile> tiles) {
        super(context, 0, tiles);
    }

    /**
     * Provides a view for the TileAdapter.
     * @param position Position in the list of data to be viewed.
     * @param convertView The recycled view to populate.
     * @param parent The parent view group used for inflation.
     * @return The view for the position in the adapter view.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        Tile tile = getItem(position);
        switch(tile.getType()) {
            case DISCUSSION: {
                /*
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_tile_discussion, parent, false);

                TextView name = listItemView.findViewById(R.id.tileDiscussionAuthor);
                TextView subject = listItemView.findViewById(R.id.tileDiscussionContent);

                name.setText(tile.get);
                subject.setText(tile.getDiscussionSubject());
                 */
            }
            case EVENT: {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_tile_event, parent, false);

                TextView time = listItemView.findViewById(R.id.tileEventTime);
                TextView name = listItemView.findViewById(R.id.tileEventName);
                TextView description = listItemView.findViewById(R.id.tileEventDescription);
                TextView location = listItemView.findViewById(R.id.tileEventLocation);

                time.setText(tile.getTimestampLocal().toString());
                name.setText(tile.getEventName());
                description.setText(tile.getEventDescription());
                //String loc = tile.getEventLocationString();
                //location.setText(loc);
            }
            case GROUP: {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_tile_group, parent, false);

                TextView time = listItemView.findViewById(R.id.tileGroupTime);
                TextView name = listItemView.findViewById(R.id.tileGroupName);
                TextView message = listItemView.findViewById(R.id.tileGroupMessage);
                TextView member = listItemView.findViewById(R.id.tileGroupMember);

                time.setText(tile.getTimestampLocal().toString());
                name.setText(tile.getGroupName());
                message.setText(tile.getGroupMessage());
                if(tile.getGroupMember()) {
                    String isMember = "You are a member of this group.";
                    member.setText(isMember);
                } else {
                    String notMember = "You are not a member of this group.";
                    member.setText(notMember);
                }
            }
        }
        return listItemView;
    }
}
