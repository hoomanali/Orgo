package com.example.orgo.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.orgo.firebase.DbContract.*;


/**
 * FirestoreHandler class for performing relevant Firebase Firestore operations.
 *
 * Instantiate an object of this class and use its member functions to interact with
 * the Firestore database.
 *
 * See orgo.view.FirebaseTestActivity for examples on how to use these methods.
 */
public class FirestoreHandler {

    private static final String TAG = "[FirestoreHandler]";
    private FirebaseFirestore db;

    /**
     * FirestoreHandler constructor.
     *
     * Instantiate a reference to the database and initialize the success flag to false.
     */
    public FirestoreHandler() {
        db = FirebaseFirestore.getInstance();
    }

    /*
     * Methods for managing groups in Firestore.
     */
    /**
     * Add a new group to the database, if the group already exists, the information will be
     * over written.
     * Groups are stored in the database as Documents, the hierarchy is:
     *      Firestore > groups > groupName
     *
     * @param groupName The name of the group (String)
     * @param groupDescription The description of the group (String)
     * @param admins A list of group admins (List)
     * @param creator Creator of the group's email address (userid). (String)
     * @param members A list of group members (List)
     * @param privateGroup Flag for marking the group public (false) or private (true). (boolean)
     */
    public void addGroup(final String groupName, String groupDescription, List<String> admins,
                            String creator, List<String> members, boolean privateGroup) {

        // Build Hash Map to store group fields from parameters.
        Map<String, Object> group = new HashMap<>();
        group.put(GROUP_NAME, groupName);
        group.put(GROUP_DESCRIPTION, groupDescription);
        group.put(GROUP_ADMINS, admins);
        group.put(GROUP_CREATOR, creator);
        group.put(GROUP_MEMBERS, members);
        group.put(GROUP_PRIVATE, privateGroup);

        // Open group using group name
        db.collection(COLLECTION_GROUPS).document(groupName)
                .set(group)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String logMsg = groupName + " successfully added.";
                        Log.d(TAG, logMsg);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String logMsg = groupName + " not added.";
                        Log.d(TAG, logMsg, e);
                    }
                });
    }

    /**
     * Loads a locally stored LocalGroup object holding data queried from Firestore.
     * This method runs asynchronously. In order to be used, the using class or activity must
     * implement OnLocalGroupReady along with an OnReady(LocalGroup group) method.
     *
     * See orgo.view.FirebaseTestActivity for an example and further explanation on how
     * to use loadGroup().
     *
     * @param groupName The name of the group to be loaded.
     * @param onReady OnLocalGroupReady interface for loading async data.
     */
    public void loadGroup(String groupName, final OnLocalGroupReady onReady) {
        final LocalGroup localGroup = new LocalGroup();

        DocumentReference docRef = db.collection(COLLECTION_GROUPS).document(groupName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        localGroup.setAdminsList((ArrayList<String>)document.get(GROUP_ADMINS));
                        localGroup.setMembersList((ArrayList<String>)document.get(GROUP_MEMBERS));
                        localGroup.setCreator((String)document.get(GROUP_CREATOR));
                        localGroup.setName((String)document.get(GROUP_NAME));
                        localGroup.setDescription((String)document.get(GROUP_DESCRIPTION));
                        localGroup.setPrivateFlag((boolean)document.get(GROUP_PRIVATE));
                        onReady.onReady(localGroup);
                    } else {
                        Log.d(TAG, "No such group");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * Asynchronously gets a list of all groups from firestore database.
     *
     * See orgo.view.FirebaseTestActivity for an example and further explanation on how
     * to use loadGroup().
     *
     * @param onReady The calling class that implements the OnLoadAllGroupsReady interface.
     */
    public void loadAllGroups(final OnLoadAllGroupsReady onReady) {
        final List<LocalGroup> allGroups = new ArrayList<>();

        db.collection(COLLECTION_GROUPS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                LocalGroup group = new LocalGroup();
                                group.setPrivateFlag((boolean)document.get(GROUP_PRIVATE));
                                group.setDescription((String)document.get(GROUP_DESCRIPTION));
                                group.setCreator((String)document.get(GROUP_CREATOR));
                                group.setAdminsList((ArrayList<String>)document.get(GROUP_ADMINS));
                                group.setMembersList((ArrayList<String>)document.get(GROUP_MEMBERS));
                                group.setName((String)document.get(GROUP_NAME));
                                allGroups.add(group);
                            }
                            onReady.onReady(allGroups);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Deletes a group from the Firestore.
     *
     * @param groupName Name of the group to be deleted.
     */
    public void deleteGroup(String groupName) {
        db.collection(COLLECTION_GROUPS).document(groupName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    /**
     * Update the description field for an individual group document.
     *
     * NOT TESTED YET
     *
     * @param groupName Name of the group (Document) to be updated.
     * @param groupDescription New description for group.
     */
    public void updateGroupDescription(final String groupName, final String groupDescription) {
        updateGroupField(GROUP_DESCRIPTION, groupName, groupDescription);
    }

    /**
     * Update the list of admins for a group document, full list of admins must be included
     * each time updateGroupAdmins() is called as full list will be replaced.
     *
     * NOT TESTED YET
     *
     * @param groupName Name of the group (Document) to be updated.
     * @param fullListOfAdmins New List of admins.
     */
    public void updateGroupAdmins(final String groupName, final List<String> fullListOfAdmins) {
        updateGroupField(GROUP_ADMINS, groupName, fullListOfAdmins);
    }

    /**
     * Update the creator for a group.
     *
     * NOT TESTED YET
     *
     * @param groupName Name of the group (Document) to be updated.
     * @param groupCreator New creator of group, lol.
     */
    public void updateGroupCreator(final String groupName, final String groupCreator) {
        updateGroupField(GROUP_CREATOR, groupName, groupCreator);
    }

    /**
     * Update the list of members for a group document, full list of members must be included
     * each time updateGroupMembers() is called as full list will be replaced.
     *
     * NOT TESTED YET
     *
     * @param groupName Name of the group (Document) to be updated.
     * @param fullListOfMembers New List of members.
     */
    public void updateGroupMembers(final String groupName, final List<String> fullListOfMembers) {
        updateGroupField(GROUP_MEMBERS, groupName, fullListOfMembers);

    }

    /**
     * Update the private flag of a group. True if group is private, false if group is public.
     *
     * NOT TESTED YET
     *
     * @param groupName Name of the group (Document) to be updated.
     * @param privateGroup Boolean value of new private group flag.
     */
    public void updateGroupPrivate(final String groupName, final boolean privateGroup) {
        updateGroupField(GROUP_PRIVATE, groupName, privateGroup);
    }

    /**
     * Generic private method to handle updating individual fields of a Group document.
     *
     * NOT TESTED YET
     *
     * @param groupName Name of the group (Document).
     * @param fieldName Name of the field to be updated.
     * @param value Value to be set to the field.
     */
    private void updateGroupField(final String fieldName, final String groupName, final Object value) {
        db.collection(COLLECTION_GROUPS).document(groupName)
                .update(fieldName, value).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String logMsg = groupName + " " + fieldName + " set to " + value + ".";
                        Log.d(TAG, logMsg);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String logMsg = groupName + " " + fieldName + " not set to " + value + ".";
                        Log.d(TAG, logMsg, e);
                    }
                });
    }

    /**
     * Takes the group name and new user email then adds the new email to the group's list of
     * members.
     * @param groupName The name of the group to be updated.
     * @param newMemberEmail The email address of the group's new member.
     */
    public void addGroupMember(final String groupName, final String newMemberEmail) {
        final DocumentReference docRef = db.collection(COLLECTION_GROUPS).document(groupName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        LocalGroup localGroup = new LocalGroup((String)document.get(GROUP_NAME),
                                (String)document.get(GROUP_DESCRIPTION),
                                (ArrayList<String>)document.get(GROUP_ADMINS),
                                (String)document.get(GROUP_CREATOR),
                                (ArrayList<String>)document.get(GROUP_MEMBERS),
                                (Boolean)document.get(GROUP_PRIVATE));
                        Log.d(TAG, "Old members list: " + localGroup.getMembersList());
                        if(!localGroup.getMembersList().contains(newMemberEmail)) {
                            localGroup.addMember(newMemberEmail);
                            updateGroupMembers(groupName, localGroup.getMembersList());
                            Log.d(TAG, "New members list: " + localGroup.getMembersList());
                        }
                        else {
                            Log.d(TAG,"You are in the group already");
                        }
                    } else {
                        Log.d(TAG, "No such group");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /*
     * Methods for managing users in Firestore.
     */
    /**
     * Add a new user to the Firestore database as a new Document. The document name will be the
     * user's email address. If the user already exists, all data will be replaced with the
     * new user's data.
     *
     * @param localUser The LocalUser object containing the new user's information.
     */
    public void addUser(final LocalUser localUser) {

        // Build Hash Map to store user fields from parameters.
        Map<String, Object> user = new HashMap<>();
        user.put(USER_BIO, localUser.getBiography());
        user.put(USER_EMAIL, localUser.getEmail());
        user.put(USER_GROUPS, localUser.getGroupsList());
        user.put(USER_NAME_FIRST, localUser.getNameFirst());
        user.put(USER_NAME_LAST, localUser.getNameLast());
        user.put(USER_UID, localUser.getUID());
        //user.put(USER_PHOTO, localUser.getPhoto());

        // Open user using user name and add fields.
        db.collection(COLLECTION_USERS).document(localUser.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String logMsg = localUser.getEmail() + " successfully added.";
                        Log.d(TAG, logMsg);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String logMsg = localUser.getEmail() + " not added.";
                        Log.d(TAG, logMsg, e);
                    }
                });
    }

    /**
     * Loads a locally stored LocalUser object holding data queried from Firestore.
     * This method runs asynchronously. In order to be used, the using class or activity must
     * implement OnLocalUserReady along with an OnReady(LocalUser user) method.
     *
     * See orgo.view.ui.ViewGroupActivity.java for an example and further explanation on how
     * to use loadGroup(), loadUser() uses a similar implement and onReady().
     *
     * @param userEmail The email address of the user to be loaded.
     * @param caller The calling class.
     */
    public void loadUser(String userEmail, final OnLocalUserReady caller) {

        DocumentReference docRef = db.collection(COLLECTION_USERS).document(userEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        LocalUser localUser = new LocalUser();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        localUser.setBiography((String)document.get(USER_BIO));
                        localUser.setEmail((String)document.get(USER_EMAIL));
                        localUser.setGroups((ArrayList<String>)document.get(USER_GROUPS));
                        localUser.setNameFirst((String)document.get(USER_NAME_FIRST));
                        localUser.setNameFirst((String)document.get(USER_NAME_LAST));
                        localUser.setUID((String)document.get(USER_UID));
                        //localUser.setPhoto(document.get(USER_PHOTO));
                        caller.onReady(localUser);
                    } else {
                        Log.d(TAG, "No such user");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * Deletes a user from the Firestore.
     *
     * @param userEmail Email address of the user to be deleted.
     */
    public void deleteUser(String userEmail) {
        db.collection(COLLECTION_USERS).document(userEmail)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    /**
     * Adds a new discussion post to the current discussion.
     *
     * @param groupName The name of the group the discussion belongs to.
     * @param discussionPost The content of the discussion post.
     */
    public void addDiscussionPost(final String groupName, final DiscussionPost discussionPost) {
        DocumentReference docRef = db.collection(COLLECTION_DISCUSSIONS).document(groupName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        List<DiscussionPost> discussion = new ArrayList<>();
                        discussion = (ArrayList<DiscussionPost>)document.get(DISCUSSION_GROUP);
                        discussionPost.setPostId(discussion.size());
                        discussion.add(discussionPost);
                        db.collection(COLLECTION_DISCUSSIONS).document(groupName).update(DISCUSSION_GROUP, discussion);
                    } else {
                        Log.d(TAG, "No such user");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void loadDiscussion(final String groupName, final OnDiscussionReady caller) {

        DocumentReference docRef = db.collection(COLLECTION_DISCUSSIONS).document(groupName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        ArrayList<DiscussionPost> discussion = new ArrayList<>();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        discussion = (ArrayList<DiscussionPost>)document.get(DISCUSSION_GROUP);
                        caller.onReady(discussion);
                    } else {
                        Log.d(TAG, "No such discussion");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}
