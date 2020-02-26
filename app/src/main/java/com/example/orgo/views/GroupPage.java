package com.example.orgo.views;

import java.util.ArrayList;
import java.util.List;

public class GroupPage {
    private String groupName;
    private String description;
    private String creator;
    private String admins;
    private String members;
    private boolean privateGroup;

    void setGroupName(String groupNameInput) {
        groupName = groupNameInput;
    }

    void setGroupCreator(String creatorInput) {
        creator = creatorInput;
    }

    void setDescription(String descriptionInput) {
        description = descriptionInput;
    }

    void setAdmin(String adminInput) { admins = adminInput; }

    void setMember(String memberInput) { members = memberInput; }

    void setPrivateGroup(Boolean privateGroupInput) { privateGroup = privateGroupInput; }

    public String getGroupName() { return groupName; }

    public String getDescription() { return description; }

    public String getAdmins() { return admins; }

    public String getMembers() { return members; }

    public Boolean getPrivateGroup() { return privateGroup; }

    public String getCreator() { return creator; }


}
