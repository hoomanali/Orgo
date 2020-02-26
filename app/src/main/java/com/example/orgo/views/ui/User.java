package com.example.orgo.views.ui;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String groupName;
    private String description;
    private String uid;

    public void setGroupName(String groupNameInput) {
        groupName = groupNameInput;
    }

    public void setDescription(String descriptionInput) {
        description = descriptionInput;
    }

    public void setUid(String uidInput) {
        uid = uidInput;
    }

    public String getGroupName() { return groupName; }

    public String getDescription() { return description; }
}
