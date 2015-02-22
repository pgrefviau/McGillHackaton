package com.hackaton.findme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Affiche tous les contacts de l'utilisateur dans une liste. Peut en sélectionner un.
public class SelectFriendActivity extends ActionBarActivity{

    private ListView friendsList;
    private String[] friendsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        /*
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        // Set the text view as the activity layout
        setContentView(textView);
        */

        friendsList = (ListView) findViewById(R.id.friendsList);

        friendsName = new String[] { "jfkkjsfsf",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "HUHUHUHUUH",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "CCCCUL"
        };


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, friendsName);


        // Assign adapter to ListView
        friendsList.setAdapter(adapter);

        // ListView Item Click Listener
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // TODO:selon MainActivity.selectedAction, prendre infos nécessaires du selected friend and do some shits


                // TODO: enlever ce feedback temporaire quand on click sur un item
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) friendsList.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }
}
