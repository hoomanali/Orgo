package com.example.orgo.firebase;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface OnLoadAllGroupsReady {
    public void onReady(List<LocalGroup> allGroups);
}
