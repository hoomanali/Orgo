package com.example.orgo.views.ui.home;

import android.location.Location;
import android.util.Log;

import com.example.orgo.homepage.Tile;

import java.util.ArrayList;

/**
 * Helper class to build list of tiles.
 */
public class TileListBuilder {

    public static ArrayList<Tile> buildDemoListForHomePage() {
        ArrayList<Tile> tileList = new ArrayList<Tile>();

        /* This currently doesn't work.
        tileList.add(new Tile("Beach session", 39.5, -127.8,
                "Bring your board and some brews."));
         */
        tileList.add(new Tile("Heaven's Gate",
                "Dedicated to welcoming Hale.", false));
        tileList.add(new Tile("Slug Gang",
                "Yeah you know what it is, blue and yellow.", true));
        /*
        tileList.add(new Tile("Heaven's Gate",
                "Procedures for Hale's arrival."));
         */
        tileList.add(new Tile("Whiskey Tango Foxtrot",
                "We are the pinnacle of greek life.", false));
        /*
        tileList.add(new Tile("Slug Gang",
                "Has anyone confirmed the existence of Oakes?"));
         */
        /*
        tileList.add(new Tile("Slug Gang",
                "Share your dining hall #lifehacks."));
         */
        tileList.add(new Tile("Hiking club",
                "We go through pogonip on a nice hike.", true));
        tileList.add(new Tile("Baking Club",
                "We meet once a week to talk about all things baking..", false));
        tileList.add(new Tile("Fencing Club",
                "Master or beginner, we are a group of people who love fencing.", true));
        tileList.add(new Tile("Balloon Animals",
                "We meet once every tuesday to show up our newest balloon creations.", false));
        tileList.add(new Tile("Harry styles fan club",
                "We listen to that's what makes you beautiful on a loop for 3 hours every Thrusday!", false));
        tileList.add(new Tile("Basketball club",
                "Come play basketball from 6:30-8:00. We are cool group of guys.", true));
        tileList.add(new Tile("Coding club",
                "Come hack the government with our amazing programming skills", false));




        return tileList;
    }

    public static ArrayList<Tile> buildDemoListForGroupsPage() {
        ArrayList<Tile> tileList = new ArrayList<Tile>();

        /* This currently doesn't work.
        tileList.add(new Tile("Beach session", 39.5, -127.8,
                "Bring your board and some brews."));
         */
        /*
        tileList.add(new Tile("Heaven's Gate",
                "Dedicated to welcoming Hale.", false));
         */
        tileList.add(new Tile("Slug Gang",
                "Yeah you know what it is, blue and yellow.", true));
        /*
        tileList.add(new Tile("Heaven's Gate",
                "Procedures for Hale's arrival."));
         */
        /*
        tileList.add(new Tile("Whiskey Tango Foxtrot",
                "We are the pinnacle of greek life.", false));
         */
        /*
        tileList.add(new Tile("Slug Gang",
                "Has anyone confirmed the existence of Oakes?"));
         */
        /*
        tileList.add(new Tile("Slug Gang",
                "Share your dining hall #lifehacks."));
         */

        return tileList;
    }

    public static ArrayList<Tile> buildDemoListForDiscoverPage() {
        ArrayList<Tile> tileList = new ArrayList<Tile>();

        /* This currently doesn't work.
        tileList.add(new Tile("Beach session", 39.5, -127.8,
                "Bring your board and some brews."));
         */
        tileList.add(new Tile("Heaven's Gate",
                "Dedicated to welcoming Hale.", false));
        /*
        tileList.add(new Tile("Slug Gang",
                "Yeah you know what it is, blue and yellow.", true));
        */
        /*
        tileList.add(new Tile("Heaven's Gate",
                "Procedures for Hale's arrival."));
         */
        tileList.add(new Tile("Whiskey Tango Foxtrot",
                "We are the pinnacle of greek life.", false));
        /*
        tileList.add(new Tile("Slug Gang",
                "Has anyone confirmed the existence of Oakes?"));
         */
        /*
        tileList.add(new Tile("Slug Gang",
                "Share your dining hall #lifehacks."));
         */
        tileList.add(new Tile("Baking Club",
                "We meet once a week to talk about all things baking..", false));
        tileList.add(new Tile("Fencing Club",
                "We meet once every tuesday to show up our newest balloon creations.", false));
        tileList.add(new Tile("Harry styles fan club",
                "We listen to that's what makes you beautiful on a loop for 3 hours every Thrusday!", false));
        tileList.add(new Tile("Basketball club",
                "Come hack the government with our amazing programming skills", false));

        return tileList;
    }


}
