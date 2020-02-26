package com.example.orgo.firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class to manage and pass around locally stored group information.
 */
public class LocalGroup {

    private String name;
    private String description;
    private ArrayList<String> adminsList;
    private String creator;
    private ArrayList<String> membersList;
    private boolean privateFlag;

    /**
     * Constructs a locally stored group object for storing group document information.
     *
     * @param name Name of the group (Document)
     * @param description Description of the group.
     * @param adminsList Full arraylist of group admins (emails).
     * @param creator Email of  group creator.
     * @param membersList Full arraylist of group members (emails).
     * @param privateFlag Boolean flag, true of group is private.
     */
    public LocalGroup(String name, String description, ArrayList<String> adminsList, String creator,
                 ArrayList<String> membersList, boolean privateFlag) {
        this.name = name;
        this.description = description;
        this.adminsList = adminsList;
        this.creator = creator;
        this.membersList = membersList;
        this.privateFlag = privateFlag;
    }

    public LocalGroup() {
        name = "";
        description = "";
        adminsList = new ArrayList<>();
        creator = "";
        membersList = new ArrayList<>();
        privateFlag = false;
    }

    /**
     * Returns the name of the group.
     *
     * @return Group name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of the group.
     *
     * @return Description of group.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns full list of group admins as an ArrayList.
     *
     * @return Full list of group admins.
     */
    public ArrayList<String> getAdminsArrayList() {
        return this.adminsList;
    }

    /**
     * Returns full list of group admins as a List.
     *
     * @return Full list of group admins.
     */
    public List<String> getAdminsList() {
        List<String> admins = this.adminsList;
        return admins;
    }

    /**
     * Returns the email address of the group's creator.
     *
     * @return Group creator's email.
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * Returns full list of group members as an ArrayList.
     * @return
     */
    public ArrayList<String> getMembersArrayList() {
        return this.membersList;
    }

    /**
     * Returns full list of group members as a List.
     * @return
     */
    public List<String> getMembersList() {
        List<String> members = this.membersList;
        return members;
    }

    /**
     * Returns a boolean flag for group visibility.
     *
     * @return True if group is private, false if group is public.
     */
    public boolean getPrivateFlag() {
        return this.privateFlag;
    }

    /**
     * Sets the group name.
     *
     * @param name New name of the group.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the group description.
     *
     * @param description The new description of the group.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the full list of group admins.
     *
     * @param adminsList List of admins.
     */
    public void setAdminsList(ArrayList<String> adminsList) {
        this.adminsList = adminsList;
    }

    /**
     * Sets the creator of the group.
     *
     * @param creator Email address of the creator.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Sets the full list of group members.
     *
     * @param membersList List of members.
     */
    public void setMembersList(ArrayList<String> membersList) {
        this.membersList = membersList;
    }

    /**
     * Sets the private flag for group visibility.
     *
     * @param privateFlag True if group is private, false if group is public.
     */
    public void setPrivateFlag(boolean privateFlag) {
        this.privateFlag = privateFlag;
    }

    /**
     * Add an admin to the group's admin list.
     * @param adminEmail Email address of the admin to add.
     * @return The new list of admins with the new admin included.
     */
    public ArrayList<String> addAdmin(String adminEmail) {
        this.adminsList.add(adminEmail);
        return this.adminsList;
    }

    /**
     * Add a member to the group's member list.
     * @param memberEmail
     * @return The new list of members with the new member included.
     */
    public ArrayList<String> addMember(String memberEmail) {
        this.membersList.add(memberEmail);
        return this.membersList;
    }

}
