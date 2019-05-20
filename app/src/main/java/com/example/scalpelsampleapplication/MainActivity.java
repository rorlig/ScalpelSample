package com.example.scalpelsampleapplication;

import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;
import static android.app.ActionBar.DISPLAY_SHOW_TITLE;
import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    private static boolean first = true;

    ScalpelFrameLayout scalpelView;
    ViewPager pagerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        scalpelView = findViewById(R.id.scalpel);
        pagerView = findViewById(R.id.item_pager);

        pagerView.setAdapter(new SamplePagerAdapter(this));

        Switch enabledSwitch = new Switch(this);
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (first) {
                    first = false;
                    Toast.makeText(MainActivity.this, R.string.first_run, LENGTH_LONG).show();
                }

                scalpelView.setLayerInteractionEnabled(isChecked);
                invalidateOptionsMenu();
            }
        });

//        scalpelView.setLayerInteractionEnabled(true);



        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(enabledSwitch);
        actionBar.setDisplayOptions(DISPLAY_SHOW_TITLE | DISPLAY_SHOW_CUSTOM);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        if (!scalpelView.isLayerInteractionEnabled()) {
            return false;
        }
        menu.add("Draw Views")
                .setCheckable(true)
                .setChecked(scalpelView.isDrawingViews())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        scalpelView.setDrawViews(checked);
                        return true;
                    }
                });
        menu.add("Draw IDs")
                .setCheckable(true)
                .setChecked(scalpelView.isDrawingIds())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        scalpelView.setDrawIds(checked);
                        return true;
                    }
                });
        return true;
    }
}
