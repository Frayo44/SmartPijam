package pijama.com.pijamasan.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import pijama.com.pijamasan.R;

/**
 * Created by Yoav on 11/30/2015.
 */
public class MorningActivity extends ActionBarActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
    }


}

