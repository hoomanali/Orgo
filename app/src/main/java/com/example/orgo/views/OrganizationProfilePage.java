package com.example.orgo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orgo.R;
import com.example.orgo.firebase.FirestoreHandler;
import com.example.orgo.firebase.LocalGroup;
import com.example.orgo.firebase.OnLocalGroupReady;
import com.example.orgo.homepage.Tile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrganizationProfilePage extends AppCompatActivity implements OnLocalGroupReady {

    private TextView t;
    private TextView d;
    private TextView m;
    private Button b;

    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String UserId;
    String userEmail;
    final FirestoreHandler firestoreHandler = new FirestoreHandler();
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_profile_page);

        t = (TextView) findViewById(R.id.groupName);
        d = (TextView) findViewById(R.id.groupDescription);
        m = (TextView) findViewById(R.id.memberNames);
        b = (Button) findViewById(R.id.joinGroup);

        UserId = mAuth.getCurrentUser().getEmail();

        groupName = getIntent().getStringExtra("groupName");

        firestoreHandler.loadGroup(groupName, this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firestoreHandler.addGroupMember(groupName,UserId);
                // Switching this to add by email.
                firestoreHandler.addGroupMember(groupName,userEmail);

                Intent intent = new Intent(OrganizationProfilePage.this, MainNavActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onReady(LocalGroup group) {
        t.setText(group.getName());
        d.setText(group.getDescription());
        m.setText(group.getMembersArrayList().toString());

    }
}
