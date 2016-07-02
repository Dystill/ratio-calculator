package com.dystill.app.ratiocalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<double[]> RATIO_LIST;
    private static LinearLayout background;
    private RatioCardRecyclerAdapter mainRatioListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.v("onCreate", "Started");

        // obtain important views
        background = (LinearLayout) findViewById(R.id.backgroundLayout);
        RecyclerView mainRatioList = (RecyclerView) findViewById(R.id.cardList);

        RATIO_LIST = new ArrayList<>();

        // add cards to the recyclerView
        mainRatioListAdapter = new RatioCardRecyclerAdapter(this, RATIO_LIST);
        mainRatioList.setAdapter(mainRatioListAdapter);
        mainRatioList.setLayoutManager(new LinearLayoutManager(this));

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRatio();
            }
        });
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
                addRatio(0);
                return true;

            default:                                                                                // the user's action was not recognized.
                return super.onOptionsItemSelected(item);
        }
    }

    static void removeRatio(int position) {
        Log.v("removeRatio", "Removing " + position);
        RATIO_LIST.remove(position);
        checkRatioImageVisibilityStatus();
    }

    private void addRatio() {
        double newRatioSet[] = {0.0,0.0,0.0,0.0};
        Log.v("addRatio", "Adding to end");
        RATIO_LIST.add(newRatioSet);
        mainRatioListAdapter.notifyItemInserted(0);
        checkRatioImageVisibilityStatus();
    }

    private void addRatio(int position) {
        double newRatioSet[] = {0.0,0.0,0.0,0.0};
        Log.v("addRatio", "Adding to " + position);
        RATIO_LIST.add(position, newRatioSet);
        mainRatioListAdapter.notifyItemInserted(0);
        checkRatioImageVisibilityStatus();
    }

    private static void checkRatioImageVisibilityStatus() {
        if(!background.isShown() && RATIO_LIST.isEmpty())
            background.setVisibility(View.VISIBLE);
        else
            background.setVisibility(View.GONE);
    }

}
