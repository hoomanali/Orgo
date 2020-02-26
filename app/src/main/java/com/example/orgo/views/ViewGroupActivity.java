package com.example.orgo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orgo.R;
import com.example.orgo.firebase.FirestoreHandler;
import com.example.orgo.firebase.LocalGroup;
import com.example.orgo.firebase.LocalUser;
import com.example.orgo.firebase.OnLocalGroupReady;
import com.example.orgo.firebase.OnLocalUserReady;

/*
 * Displays an individual group's information and allows editing all values (except group name).
 *
 * Activity must implement OnLocalGroupReady interface and an onReady method in order to use
 * FirestoreHandler's getGroup method.
 */
public class ViewGroupActivity extends AppCompatActivity implements OnLocalGroupReady {
    private static final String TAG = "[ViewGroupActivity]";
    public static LocalGroup group;

    FirestoreHandler firestoreHandler;
    String groupName;

    TextView editText_Name;
    TextView editText_Description;
    TextView editText_Admins;
    TextView editText_Creator;
    TextView editText_Members;
    TextView editText_PrivateFlag;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        // Initialize widgets
        editText_Name = findViewById(R.id.viewGroupActivity_groupName);
        editText_Description = findViewById(R.id.viewGroupActivity_groupDescription);
        editText_Admins = findViewById(R.id.viewGroupActivity_groupAdmins);
        editText_Creator = findViewById(R.id.viewGroupActivity_groupCreator);
        editText_Members = findViewById(R.id.viewGroupActivity_groupMembers);
        editText_PrivateFlag = findViewById(R.id.viewGroupActivity_groupPrivateFlag);
        home = findViewById(R.id.homeButton);

        // Create a FirestoreHandler
        firestoreHandler = new FirestoreHandler();

        // Get the group name from the intent extra.
        groupName = getIntent().getStringExtra("groupName");

        /*
         * Load a LocalGroup object once it is retrieved from Firebase.
         * groupName is the string name of the group to be retrieved.
         */
        firestoreHandler.loadGroup(groupName, this);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewGroupActivity.this, MainNavActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * onReady method must be implemented anytime firestoreHandler.getGroup() is used.
     *
     * This method waits for LocalGroup group to be loaded completely from the
     * asynchronous task firestoreHandler.getGroup(), then performs the code block inside
     * onReady, synchronously.
     */
    @Override
    public void onReady(LocalGroup group) {
        editText_Name.setText(group.getName());
        editText_Admins.setText(group.getAdminsArrayList().toString());
        editText_Creator.setText(group.getCreator());
        editText_Description.setText(group.getDescription());
        editText_Members.setText(group.getMembersArrayList().toString());
        editText_PrivateFlag.setText(String.valueOf(group.getPrivateFlag()));

    }
}

