package com.example.orgo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.orgo.R;

import org.w3c.dom.Text;

public class OrganizationPage extends AppCompatActivity {

    private TextView t;
    private TextView d;

    public static final String prefs = "examplePrefs";
    public static final String prefs2 = "examplePrefs2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_page);

        t = (TextView) findViewById(R.id.groupName);
        d = (TextView) findViewById(R.id.groupDescription);

        //SharedPreferences mPreferences = getSharedPreferences(prefs,0);
        //String userString = mPreferences.getString("userMessage", "Nothing Found");
        //t.setText(userString);

        //SharedPreferences mPreferences2 = getSharedPreferences(prefs2,0);
        //String userString2 = mPreferences2.getString("userMessage2", "Nothing Found");
        //d.setText(userString2);
    }
}