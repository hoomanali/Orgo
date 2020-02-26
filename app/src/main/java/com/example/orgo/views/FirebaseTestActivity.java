package com.example.orgo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.orgo.R;
import com.example.orgo.firebase.FirestoreHandler;
import com.example.orgo.firebase.LocalGroup;
import com.example.orgo.firebase.LocalUser;
import com.example.orgo.firebase.OnLoadAllGroupsReady;
import com.example.orgo.firebase.OnLocalGroupReady;
import com.example.orgo.firebase.OnLocalUserReady;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity contains a few buttons for testing FirebaseHandler functionality.
 *
 * OnLocalGroupReady must be implemented for loadGroup() to function.
 */
public class FirebaseTestActivity extends AppCompatActivity
        implements OnLocalGroupReady, OnLocalUserReady, OnLoadAllGroupsReady {

    private static final String TAG = "[FirebaseTestActivity]";
    private FirestoreHandler fsHandler;
    public LocalGroup group; // Group that is loaded when calling loadGroup().
    private LocalGroup testGroup;   // Test group with dummy information.
    public LocalUser user; // User that is loaded when calling loadUser().
    private LocalUser testUser;   // Test user with dummy information.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        Log.d(TAG, "Creating Firestore Handler.");
        fsHandler = new FirestoreHandler();

        // Initialize some dummy info for testGroup.
        Log.d(TAG, "Initializing testGroup.");
        ArrayList<String> admins = new ArrayList<>();
        admins.add("email@hello.com");
        admins.add("hello@goodbye.com");
        admins.add("user@email.com");
        ArrayList<String> members = new ArrayList<>();
        members.add("member1@mem.com");
        members.add("member2@mem.com");
        members.add("member3@mem.com");
        testGroup = new LocalGroup("Firebase Test", "This is a Firebase test", admins,
                "firebasetest@orgo.com", members, false);

        // Initialize some dummy info for testUser.
        Log.d(TAG, "Initializing testUser.");
        ArrayList<String> groups = new ArrayList<>();
        groups.add("group01");
        groups.add("group02");
        groups.add("group03");
        groups.add("group04");
        testUser = new LocalUser("Test", "User", "testuser@test.com",
                "someUID", "I am a test user", groups);
    }

    /**
     * onClick event for testing addGroup() method.
     * @param view The current view.
     */
    public void onAddGroupClick(View view) {
        Log.d(TAG, "onAddGroupClick() start");
        Log.d(TAG, testGroup.getName());
        fsHandler.addGroup(testGroup.getName(), testGroup.getDescription(), testGroup.getAdminsList(),
                testGroup.getCreator(), testGroup.getMembersList(), testGroup.getPrivateFlag());
        Log.d(TAG, "onAddGroupClick() finish");
    }

    /**
     * onClick event for testing loadGroup() method.
     * @param view The current view.
     */
    public void onLoadGroupClick(View view) {
        Log.d(TAG, "onLoadGroupClick() start");
        fsHandler.loadGroup(testGroup.getName(), this);
        Log.d(TAG, "onLoadGroupClick() finish");
    }

    /**
     * onClick event for testing loadAllGroups() method.
     * @param view The current view.
     */
    public void onLoadAllGroupsClick(View view) {
        Log.d(TAG, "onLoadAllGroupsClick() start");
        fsHandler.loadGroup(testGroup.getName(), this);
        fsHandler.loadAllGroups(this);
        Log.d(TAG, "onLoadAllGroupsClick() finish");
    }

    /**
     * onClick event for testing deleteGroup() method.
     * @param view The current view.
     */
    public void onDeleteGroupClick(View view) {
        Log.d(TAG, "onDeleteGroupClick() start");
        fsHandler.deleteGroup(testGroup.getName());
        Log.d(TAG, "Trying to load the deleted group....");
        fsHandler.loadGroup(testGroup.getName(), this);
        Log.d(TAG, "onDeleteGroupClick() finish");
    }

    /**
     * onClick for testing updateGroup() method.
     * Creates a group, adds it, updates its fields, then loads the group.
     * @param view The current view.
     */
    public void onUpdateGroupClick(View view) {
        Log.d(TAG, "onUpdateGroupClick() start");
        this.group = null;

        // Add the test group to Firebase.
        fsHandler.addGroup(testGroup.getName(), testGroup.getDescription(), testGroup.getAdminsList(),
                testGroup.getCreator(), testGroup.getMembersList(), testGroup.getPrivateFlag());
        // Load the group from Firebase.
        fsHandler.loadGroup(testGroup.getName(), this);

        // Modify the loaded local group with new information.
        Log.d(TAG, "Group name: " + this.testGroup.getName());
        this.testGroup.setAdminsList(this.testGroup.addAdmin("newadmin@new.com"));
        this.testGroup.setCreator("newcreator@orgo.com");
        this.testGroup.setDescription("This description has been changed.");
        this.testGroup.setMembersList(this.testGroup.addMember("newmember@new.com"));
        this.testGroup.setPrivateFlag(!this.testGroup.getPrivateFlag());

        // Update Firebase with the new information.
        fsHandler.updateGroupAdmins(this.testGroup.getName(), this.testGroup.getAdminsList());
        fsHandler.updateGroupCreator(this.testGroup.getName(), this.testGroup.getCreator());
        fsHandler.updateGroupDescription(this.testGroup.getName(), this.testGroup.getDescription());
        fsHandler.updateGroupMembers(this.testGroup.getName(), this.testGroup.getMembersList());
        fsHandler.updateGroupPrivate(this.testGroup.getName(), this.testGroup.getPrivateFlag());

        // Add another member to the list
        fsHandler.addGroupMember(this.testGroup.getName(), "addGroupMember@o.com");

        // Load the group once again to ensure it updated.
        fsHandler.loadGroup(testGroup.getName(), this);

        // Delete the test group.
        fsHandler.deleteGroup(testGroup.getName());

        Log.d(TAG, "onUpdateGroupClick() finish");
    }

    /**
     * onClick for testing addGroupMember() method.
     * Creates a group, adds it, adds a new group member, then loads the group.
     * @param view The current view.
     */
    public void onAddGroupMemberClick(View view) {
        Log.d(TAG, "onUpdateGroupClick() start");
        this.group = null;

        // Add the test group to Firebase.
        fsHandler.addGroup(testGroup.getName(), testGroup.getDescription(), testGroup.getAdminsList(),
                testGroup.getCreator(), testGroup.getMembersList(), testGroup.getPrivateFlag());
        // Load the group from Firebase.
        fsHandler.loadGroup(testGroup.getName(), this);

        // Modify the loaded local group with new information.
        Log.d(TAG, "Group name: " + this.testGroup.getName());

        // Add another member to the list
        fsHandler.addGroupMember(this.testGroup.getName(), "addGroupMember@o.com");

        // Load the group once again to ensure it updated.
        fsHandler.loadGroup(testGroup.getName(), this);

        Log.d(TAG, "onUpdateGroupClick() finish");
    }

    /**
     * onClick event for testing addGroup() method.
     * @param view The current view.
     */
    public void onAddUserClick(View view) {
        Log.d(TAG, "onAddUserClick() start");
        Log.d(TAG, testUser.getEmail());
        fsHandler.addUser(testUser);
        Log.d(TAG, "onAddUserClick() finish");
    }

    /**
     * onClick event for testing loadUser() method.
     * @param view The current view.
     */
    public void onLoadUserClick(View view) {
        Log.d(TAG, "onLoadUserClick() start");
        fsHandler.loadUser(testUser.getEmail(), this);
        Log.d(TAG, "onLoadUserClick() finish");
    }

    /**
     * onClick event for testing deleteUser() method.
     *
     * @param view The current view.
     */
    public void onDeleteUserClick(View view) {
        Log.d(TAG, "onDeleteUserClick() start");
        fsHandler.deleteUser(testUser.getEmail());
        Log.d(TAG, "Trying to load the deleted group....");
        fsHandler.loadUser(testUser.getEmail(), this);
        Log.d(TAG, "onDeleteGroupClick() finish");
    }

    /**
     * onReady method for loadGroup(). This method waits for loadGroup() to finish
     * loading group information from Firebase to LocalGroup group, then performs its actions.
     *
     * @param group A group object created by FirebaseHandler.loadGroup().
     */
    @Override
    public void onReady(LocalGroup group) {
        // Print all the loaded information to the debug log.
        Log.d(TAG, "onReady(LocalGroup group) start");
        Log.d(TAG, "Name: " + group.getName());
        Log.d(TAG, "Creator: " + group.getCreator());
        Log.d(TAG, "Description: " + group.getDescription());
        Log.d(TAG, "Admins ArrayList: " + group.getAdminsArrayList().toString());
        Log.d(TAG, "Admins List: " + group.getAdminsList().toString());
        Log.d(TAG, "Members ArrayList: " + group.getMembersArrayList().toString());
        Log.d(TAG, "Members List: " + group.getMembersList().toString());
        Log.d(TAG, "Private: " + group.getPrivateFlag());
        Log.d(TAG, "onReady(LocalGroup group) finish");
    }

    /**
     * onReady method for loadUser(). This method waits for loadUser() to finish
     * loading user information from Firebase to LocalUser user, then performs its actions.
     *
     * @param user A user object created by FirebaseHandler.loadUser().
     */
    @Override
    public void onReady(LocalUser user) {
        // Print all the loaded information to the debug log.
        Log.d(TAG, "onReady(LocalUser user) Start");
        Log.d(TAG, "Email: " + user.getEmail());
        Log.d(TAG, "Bio: " + user.getBiography());
        Log.d(TAG, "First Name: " + user.getNameFirst());
        Log.d(TAG, "Last Name: " + user.getNameLast());
        Log.d(TAG, "Full Name: " + user.getNameFull());
        Log.d(TAG, "UID: " + user.getUID());
        Log.d(TAG, "Member of groups: " + user.getGroupsList().toString());
        Log.d(TAG, "onReady(LocalUser user) Finish");
    }

    /**
     * onReady method for loadAllGroups(). This method wait for loadAllGroups() to finish
     * loading user information from Firebase to a list containing LocalGroup objects for each
     * group, then performs its actions.
     *
     * @param allGroups A list containing LocalGroup objects for all groups.
     */
    @Override
    public void onReady(List<LocalGroup> allGroups) {
        Log.d(TAG, "onReady(List<LocalGroup> allGroups) Start");
        for(LocalGroup group : allGroups) {
            Log.d(TAG, group.getName());
        }
        Log.d(TAG, "onReady(List<LocalGroup> allGroups) Finish");
    }
}
