package com.example.orgo.firebase;

/**
 * Stores constants for Firestore database, collections, and documents.
 *
 * This class is package private to restrict access to the FireBase package.
 */
public class DbContract {

    /*
     * Collection for storing group names and  information.
     */
    public static final String COLLECTION_GROUPS = "groups";

    /*
     * Individual document fields for groups collection.
     */
    // Group Name
    public static final String GROUP_NAME = "groupName";
    // Description
    public static final String GROUP_DESCRIPTION = "description";
    // Admins
    public static final String GROUP_ADMINS = "admins";
    // Creator
    public static final String GROUP_CREATOR = "creator";
    // Members
    public static final String GROUP_MEMBERS = "members";
    // Private Group
    public static final String GROUP_PRIVATE = "privateGroup";

    /*
     * Collection for storing user names and information.
     */
    public static final String COLLECTION_USERS = "users";

    /*
     * Individual user fields for users collections.
     */
    // First Name
    public static final String USER_NAME_FIRST = "nameFirst";
    // Last Name
    public static final String USER_NAME_LAST = "nameLast";
    // Email
    public static final String USER_EMAIL = "email";
    // Biography
    public static final String USER_BIO = "biography";
    // UID
    public static final String USER_UID = "uid";
    // Groups
    public static final String USER_GROUPS = "groups";
    // Photo
    public static final String USER_PHOTO = "photo";

    /*
     * Collection for storing discussion posts.
     */
    public static final String COLLECTION_DISCUSSIONS = "discussions";

    // Group discussion
    public static final String DISCUSSION_GROUP = "discussionGroup";

}
