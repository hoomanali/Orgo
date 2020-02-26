package com.example.orgo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.orgo.R;
import com.example.orgo.firebase.FirestoreHandler;
import com.example.orgo.firebase.LocalGroup;
import com.example.orgo.firebase.OnLoadAllGroupsReady;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.orgo.firebase.DbContract.*;

public class ViewUserGroups extends AppCompatActivity implements OnLoadAllGroupsReady {

    ListView listView;
    //private List<String> groupInfo = new ArrayList<>();
    private List<String> groupNames = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayAdapter<String> adapter;
    private FirestoreHandler firestoreHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_groups);

        firestoreHandler = new FirestoreHandler();

        listView = (ListView) findViewById(R.id.lv);
        listView.setClickable(true);

        // Load all groups asynchronously, get information in onReady(List<LocalGroup> allGroups)
        firestoreHandler.loadAllGroups(this);

        // Add a click event for each item in the list which opens that group's view page.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewGroupActivity.class);
                intent.putExtra(GROUP_NAME, listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

    }

    /**
     * onReady override for loadAllGroups().
     * @param allGroups A list containing a LocalGroup object for each group in the database.
     */
    @Override
    public void onReady(List<LocalGroup> allGroups) {
        // Get the names of each group.
        for(LocalGroup group : allGroups) {
            groupNames.add(group.getName());
        }

        // Create a new list adapter.
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_selectable_list_item, groupNames);

        // Notify adapter has been updated and set listView to use the adapter.
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
