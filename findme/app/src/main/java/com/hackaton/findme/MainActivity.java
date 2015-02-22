package com.hackaton.findme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends Activity {

    public enum ACTIONS_SELECTION {I_FIND_YOU, YOU_FIND_ME, MEET_SOMEWHERE};
    public static ACTIONS_SELECTION selectedAction = ACTIONS_SELECTION.I_FIND_YOU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
