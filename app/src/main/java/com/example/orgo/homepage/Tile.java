package com.example.orgo.homepage;

import android.location.Location;
import android.util.Log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Basic Tile class for home page tiles.
 * Pay close attention to constructor signatures to use correctly.
 */
public class Tile implements Comparable<Tile> {

    /* Tile Fields */
    private Instant utcTimestamp;
    private TileType type;

    /* Group Tile Fields */
    private String groupName = null;
    private String groupMessage = null;
    private Boolean groupMember = false;

    /* Event Tile Fields */
    private String eventName = null;
    private String eventDescription = null;
    private Location eventLocation;

    /* Discussion Tile Fields */
    private String discussionGroup = null;
    private String discussionSubject = null;
    /* Group Tile constructor */
    /**
     * Constructs a Group Tile object with the parameter designated name and message.
     * @param groupName Group name.
     * @param groupMessage Notification message.
     * @param groupMember True if user is a member of group.
     */
    public Tile(String groupName, String groupMessage, Boolean groupMember) {
        this.utcTimestamp = Instant.now();
        this.type = TileType.GROUP;
        this.groupName = groupName;
        this.groupMessage = groupMessage;
        this.groupMember = groupMember;
    }

    /* Event Tile Constructor */
    /**
     * Creates Event Tile object with parameter designated name, location, and description.
     * @param eventName The name of the event.
     * @param lat The latitude of the location of the event.
     * @param lon The longitude of the location of the event.
     * @param eventDescription The description of the event.
     */
    public Tile(String eventName, Double lat, Double lon, String eventDescription) {
        this.utcTimestamp = Instant.now();
        this.type = TileType.EVENT;
        this.eventName = eventName;
        this.eventLocation = new Location("");
        eventLocation.setLatitude(lat);
        eventLocation.setLongitude(lon);
        Log.d("[Tile Constructor]", this.eventLocation.toString());
        this.eventDescription = eventDescription;
    }

    /* Discussion Tile  Constructor */
    /**
     * Creates Discussion Tile object and sets group and subject to desired parameters.
     * @param discussionGroup The group in which this discussion belongs to.
     * @param discussionSubject The subject of the discussion post.
     */
    public Tile(String discussionGroup, String discussionSubject) {
        this.utcTimestamp = Instant.now();
        this.type = TileType.DISCUSSION;
        this.discussionGroup = discussionGroup;
        this.discussionSubject = discussionSubject;
    }

    /**
     * Sets the type of the tile.
     * @param type The tile's type: DISCUSSION, EVENT, or GROUP.
     */
    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Get the current type of the tile.
     * @return The type of the tile.
     */
    public TileType getType() {
        return this.type;
    }

    /**
     * Get the tile's timestamp in UTC.
     * @return The tile's creation time.
     */
    public Instant getTimestampUTC() {
        return this.utcTimestamp;
    }

    /**
     * Gets the tile's creation timestamp in the device's local timezone.
     * @return The local time in LocalDateTime
     */
    public LocalDateTime getTimestampLocal() {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(this.utcTimestamp, zoneId);
    }

    @Override
    public int compareTo(Tile o) {
        Instant oTime = o.getTimestampUTC();
        Instant thisTime = this.getTimestampUTC();
        return thisTime.compareTo(oTime);
    }

    /* Group Tile Methods */

    /**
     * Gets the name of the group.
     * @return Group name.
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * Gets the message for the group notification.
     * @return The notification message.
     */
    public String getGroupMessage() {
        return this.groupMessage;
    }

    /**
     * Set the name of the group.
     * @param name
     */
    public void setGroupName(String name) {
        this.groupName = name;
    }

    /**
     * Set the message of the notification.
     * @param message The notification's message.
     */
    public void setGroupMessage(String message) {
        this.groupMessage = message;
    }

    /**
     * Get if the user is a member of this group.
     * @return True if user is a member.
     */
    public Boolean getGroupMember() {
        return this.groupMember;
    }

    /* Event Tile Methods */

    /**
     * Set the event's location.
     * @param location A location object representing where the event is held.
     */
    public void setEventLocation(Location location) {
        this.eventLocation = location;
    }

    /**
     * Sets the event name.
     * @param name Name of the event.
     */
    public void setEventName(String name) {
        this.eventName = name;
    }

    /**
     * Sets the event description.
     * @param description Description of the event.
     */
    public void setEventDescription(String description) {
        this.eventDescription = description;
    }

    /**
     * Get the name of the event.
     * @return Event name.
     */
    public String getEventName() {
        return this.eventName;
    }

    /**
     * Get the description of the event.
     * @return Event description.
     */
    public String getEventDescription() {
        return this.eventDescription;
    }

    /**
     * Get the location of the event.
     * @return Event location.
     */
    public Location getEventLocation() {
        return this.eventLocation;
    }

    /**
     * Returns string representation of lat,long coordinates. Example: "69, -420".
     * @return Latitude and longitude in string form.
     */
    public String getEventLocationString() {
        Double lat = this.eventLocation.getLatitude();
        Double lon = this.eventLocation.getLongitude();
        StringBuilder s = new StringBuilder();
        s.append(lat);
        s.append(", ");
        s.append(lon);
        return s.toString();
    }

    /* Discussion Tile Methods */

    /**
     * Get the group name that this discussion post belongs to.
     * @return The name of the group.
     */
    public String getDiscussionGroup() {
        return this.discussionGroup;
    }

    /**
     * Get the subject of the discussion post.
     * @return The discussion post's subject.
     */
    public String getDiscussionSubject() {
        return this.discussionSubject;
    }

    /**
     * Set the discussion post's group name.
     * @param group The group owning the discussion post.
     */
    public void setDiscussionGroup(String group) {
        this.discussionGroup = group;
    }

    /**
     * Set the discussion post's subject.
     * @param subject Discussion subject.
     */
    public void setDiscussionSubject(String subject) {
        this.discussionGroup = subject;
    }
}
