package com.example.orgo.views.ui.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.orgo.homepage.Tile;
import com.example.orgo.homepage.TileAdapter;
import com.example.orgo.views.CreateGroupActivity;
import com.example.orgo.R;
import com.example.orgo.views.ui.home.TileListBuilder;

import java.util.ArrayList;
import com.example.orgo.views.ViewUserGroups;

public class GroupsFragment extends Fragment {

    private GroupsViewModel groupsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupsViewModel =
                ViewModelProviders.of(this).get(GroupsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_groups, container, false);

        Button createGroup = root.findViewById(R.id.button_groupsCreateGroup);
        createGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GroupsFragment.this.getContext(), CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        /*
        ListView listView = root.findViewById(R.id.groupsListView);
        ArrayList<Tile> tileList = TileListBuilder.buildDemoListForGroupsPage();
        TileAdapter tileAdapter = new TileAdapter(getActivity(), tileList);
        listView.setAdapter(tileAdapter);
         */

        Button userGroup = root.findViewById(R.id.view_user_groups);
        userGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GroupsFragment.this.getContext(), ViewUserGroups.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
