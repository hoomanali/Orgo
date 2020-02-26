package com.example.orgo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.orgo.R;
import com.example.orgo.firebase.FirestoreHandler;
import com.example.orgo.views.ui.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {

    EditText groupNameInput;
    EditText descriptionInput;
    EditText adminInput;
    EditText memberInput;
    boolean privateGroupInput;

    private FirebaseFirestore mFirestore;
    private FirebaseUser currentFirebaseUser;
    private FirestoreHandler firestoreHandler;

    public static final String prefs = "examplePrefs";
    public static final String prefs2 = "examplePrefs2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        mFirestore = FirebaseFirestore.getInstance();
        firestoreHandler = new FirestoreHandler();

        Switch toggle = findViewById(R.id.privateGroup);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    privateGroupInput = true;
                } else {
                    privateGroupInput = false;
                }
            } });

        Button save = findViewById(R.id.saveGroup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupNameInput = findViewById(R.id.groupName);
                descriptionInput = findViewById(R.id.groupDescription);
                adminInput = findViewById(R.id.emailsOfAdmins);
                memberInput = findViewById(R.id.emailsOfMembers);

                if(!isFilled()){
                    Toast.makeText(getApplicationContext(), "Please fill group name and description", Toast.LENGTH_SHORT).show();
                }
                if(isFilled()) {
                    final GroupPage group = new GroupPage();
                    group.setGroupName(groupNameInput.getText().toString());

                    group.setDescription(descriptionInput.getText().toString());

                    group.setAdmin(adminInput.getText().toString());

                    group.setMember(memberInput.getText().toString());

                    group.setPrivateGroup(privateGroupInput);

                    currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                    if (currentFirebaseUser == null) {
                        group.setGroupCreator("Not logged in");
                    } else {
                        group.setGroupCreator(currentFirebaseUser.getUid());
                    }

                    // Generate lists for admins and members.
                    List<String> adminList = new ArrayList<String>();
                    adminList.add(group.getAdmins());
                    List<String> memberList = new ArrayList<String>();
                    memberList.add(group.getMembers());

                    // Add group to Firestore.
                    firestoreHandler.addGroup(group.getGroupName(), group.getDescription(),
                            adminList, group.getCreator(), memberList, group.getPrivateGroup());

                    Intent intent = new Intent(CreateGroupActivity.this, ViewUserGroups.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean isFilled() {
        String groupName = groupNameInput.getText().toString();
        if (groupName.matches("")) {
            return false;
        }
        String description = descriptionInput.getText().toString();
        if (description.matches("")) {
            return false;
        }
        return true;
    }

    public void addGroupNametoUser(GroupPage group) {
        User user = new User();
        user.setGroupName(group.getGroupName());
        user.setDescription(group.getDescription());
        user.setUid(group.getCreator());
        mFirestore.collection(group.getCreator()).add(user);
    }
}