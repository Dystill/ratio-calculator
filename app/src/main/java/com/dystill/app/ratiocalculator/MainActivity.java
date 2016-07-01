package com.dystill.app.ratiocalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static List<double[]> RATIO_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RATIO_LIST = new ArrayList<>();

        // initialization for testing purposes
        for(int i = 0; i < 4; i++) {

            RATIO_LIST.add(new double[4]);

            for (int j = 0; j < RATIO_LIST.get(0).length; j++) {
                RATIO_LIST.get(i)[j] = 25 * j;
            }
        }

        RecyclerView mainRatioList = (RecyclerView) findViewById(R.id.cardList);
        RatioCardRecyclerAdapter mainRatioListAdapter = new RatioCardRecyclerAdapter(this, RATIO_LIST);

        mainRatioList.setAdapter(mainRatioListAdapter);
        mainRatioList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                                 ////// onCreateOptionsMenu() //////
        getMenuInflater().inflate(R.menu.menu_main, menu);                                          // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                                           ////// onOptionsItemSelected() //////
        switch (item.getItemId()) {

            case R.id.action_settings:                                                              // settings action button
                return true;

            case R.id.action_add:                                                                   // add folder action button
                addRatio();
                return true;

            default:                                                                                // the user's action was not recognized.
                return super.onOptionsItemSelected(item);
        }
    }

    protected static void removeRatio(int folder) {
        RATIO_LIST.remove(folder);
    }

    protected static void addRatio() {
        RATIO_LIST.add(new double[4]);
    }
}
