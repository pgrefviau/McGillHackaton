package com.hackaton.findme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Affiche tous les contacts de l'utilisateur dans une liste. Peut en sélectionner un.
public class SelectFriendActivity extends Activity implements IUserProviderService{

    private ListView friendsListView;
    private List<BetaFriend> betaFriendsList;
    private DummyFriendProviderService dummyFriendProviderService = new DummyFriendProviderService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);

        betaFriendsList = getFriendsList();
        populateFriendsListView();  //TODO un jour: verifier que ca populate que une fois
    }

    public void populateFriendsListView() {

        friendsListView = (ListView) findViewById(R.id.friendsList);

        // En attendant de trouver comment custom son Adapter
        List<String> friendsName = new ArrayList<String>();
        for (BetaFriend s : betaFriendsList) {
            friendsName.add(s.name);
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, friendsName);


        // Assign adapter to ListView
        friendsListView.setAdapter(adapter);

        // ListView Item Click Listener
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // TODO:demander au serveur la position du selected friend. Dans un monde ideal on demanderait la permission a ce dit ami.
                BetaFriend selectedFriend = betaFriendsList.get(position);
                setupMapToFriend(selectedFriend);
            }

        });
    }

    private void setupMapToFriend(BetaFriend selectedFriend) {

        Intent intent = new Intent(this, MapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("FriendId", selectedFriend.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public int generateRandomInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public List<BetaFriend> getFriendsList() {
        return dummyFriendProviderService.getFriendsList();
    }
}
