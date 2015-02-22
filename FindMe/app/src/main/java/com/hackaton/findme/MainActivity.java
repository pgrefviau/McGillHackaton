package com.hackaton.findme;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;


public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public enum ACTIONS_SELECTION {I_FIND_YOU, YOU_FIND_ME, MEET_SOMEWHERE};

    public static ACTIONS_SELECTION selectedAction = ACTIONS_SELECTION.I_FIND_YOU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setButtonsSize();
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

    private void setButtonsSize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Button iFindYouBtn = (Button) findViewById(R.id.iFindYouBtn);
        Button youFindMeBtn = (Button) findViewById(R.id.youFindMeBtn);
        Button meetSomewhereBtn = (Button) findViewById(R.id.meetSomewhereBtn);
        
        int h = (int)(height/3.0);
      //  h = (int) (iFindYouBtn.getLayoutParams().height / 3.0);

        iFindYouBtn.setLayoutParams (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h));
        youFindMeBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h));
        meetSomewhereBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h));
/*
        int buttonWidth = (int) (height / 3.0);
        LinearLayout.LayoutParams params = (ActionBar.LayoutParams) iFindYouBtn.getLayoutParams();
        params.height = buttonWidth;
        params.leftMargin = buttonWidth;
        iFindYouBtn.setLayoutParams(params);*/

    }
}
