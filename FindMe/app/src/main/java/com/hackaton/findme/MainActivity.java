package com.hackaton.findme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public enum ACTIONS_SELECTION {I_FIND_YOU, YOU_FIND_ME, MEET_SOMEWHERE};

    public static ACTIONS_SELECTION selectedAction = ACTIONS_SELECTION.I_FIND_YOU;

    private static final String SYNC_URL = "http://localhost:4984/messaging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manager manager;
        try {
            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
            Database database = manager.getDatabase("messaging");
            Document document = database.createDocument();

            URL syncUrl;
            try {
                syncUrl = new URL(SYNC_URL);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            Replication pullReplication = database.createPullReplication(syncUrl);
            pullReplication.setContinuous(true);

            Replication pushReplication = database.createPushReplication(syncUrl);
            pushReplication.setContinuous(true);

            pullReplication.start();
            pushReplication.start();



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CouchbaseLiteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
/*
    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, SelectContactActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
*/
    public void iFindYouClick(View view)
    {
        selectedAction = ACTIONS_SELECTION.I_FIND_YOU;
        startSelectContactActivity();
    }

    public void youFindMeClick(View view)
    {
        selectedAction = ACTIONS_SELECTION.YOU_FIND_ME;
        startSelectContactActivity();
    }

    public void meetSomewhereClick(View view)
    {
        selectedAction = ACTIONS_SELECTION.MEET_SOMEWHERE;
        startSelectContactActivity();
    }

    private void startSelectContactActivity()
    {
        Intent intent = new Intent(this, SelectFriendActivity.class);
        startActivity(intent);
    }
}
