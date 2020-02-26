package com.example.orgo.views.ui.discover;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.orgo.R;
import com.example.orgo.firebase.FirestoreHandler;
import com.example.orgo.firebase.LocalGroup;
import com.example.orgo.firebase.OnLoadAllGroupsReady;
import com.example.orgo.homepage.Tile;
import com.example.orgo.homepage.TileAdapter;
import com.example.orgo.views.OrganizationProfilePage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.orgo.firebase.DbContract.GROUP_NAME;


public class DiscoverFragment extends Fragment implements OnLoadAllGroupsReady {

    private ListView listView;
    private ArrayList<Tile> tileList = new ArrayList<>();
    private TileAdapter tileAdapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String UserId;
    String userEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiscoverViewModel discoverViewModel =
                ViewModelProviders.of(this).get(DiscoverViewModel.class);

        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        // Initialize FirestoreHandler.
        FirestoreHandler firestoreHandler = new FirestoreHandler();

        // Start pulling list of groups from Firebase, asynchronously.
        firestoreHandler.loadAllGroups(this);

        UserId = mAuth.getCurrentUser().getEmail();

        // Initialize TileAdapter and attach it to tileList.
        tileAdapter = new TileAdapter(getActivity(), tileList);

        // Initialize ListView and make it clickable.
        listView = root.findViewById(R.id.discoverListView);
        listView.setClickable(true);

        // Add a click event for each item in the list which opens that group's view page.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), OrganizationProfilePage.class);
                Tile tile = tileList.get(position);
                intent.putExtra(GROUP_NAME, tile.getGroupName());
                startActivity(intent);
            }
        });

        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Override the onReady callback for loadAllGroups(). Do all work
     * @param allGroups A list of LocalGroup objects containing all groups from database.
     */
    @Override
    public void onReady(List<LocalGroup> allGroups) {
        // For each group in the list of all groups
        for(LocalGroup group : allGroups) {
            // Build a tile for that group
            Tile tile = new Tile(group.getName(), group.getDescription(), false);
            // Add the tile to tileList.

            if(!group.getMembersList().contains(UserId) || !group.getAdminsList().contains(UserId)) {
                tileList.add(tile);
            }
        }

        // Make sure the tileAdapter updates.
        tileAdapter.notifyDataSetChanged();

        // Attach tileAdapter to the list view.
        listView.setAdapter(tileAdapter);
    }
}
