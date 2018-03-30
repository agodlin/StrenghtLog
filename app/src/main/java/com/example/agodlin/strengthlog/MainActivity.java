package com.example.agodlin.strengthlog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.db.DummyData;
import com.example.agodlin.strengthlog.ui.exercises.ExercisesActivity;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;
import com.example.agodlin.strengthlog.ui.weight.WeightActivity;
import com.example.agodlin.strengthlog.ui.workout.WorkoutActivity;
import com.example.agodlin.strengthlog.utils.FileIO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static String TAG = "MainActivity";
    private static final int REQUEST_READ_PERMISSION_CODE = 0;
    private static final int REQUEST_WRITE_PERMISSION_CODE = 1;
    private boolean readWritePermission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DataManager.init(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.requestReadWritePermissions();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) {
            return;
        }
        if (requestCode == REQUEST_WRITE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Permission successfully granted!", Toast.LENGTH_SHORT).show();
                readWritePermission = true;
            }
        }
        if (readWritePermission) {
        }
    }

    private boolean requestReadWritePermissions() {
        boolean retVal = false;
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION_CODE);
        } else {
            retVal = true;
        }

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION_CODE);
            retVal = false;
        }
        return retVal;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.save_exercises) {
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Save Data to Disk ?").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        {
                            List<Exercise> exercises = DataManager.read();
                            int size = exercises == null ? 0 : exercises.size();
                            Log.d(TAG, "Save exercises size of " + size);
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(exercises);
                            String filename = "exercises.json";
                            FileIO.writeStorage(jsonString.getBytes(), filename);
                        }
                        {
                            Gson gson = new Gson();
                            List<BodyWeightItem> bodyWeightItems = DataManager.readBodyWeight();
                            String jsonString = gson.toJson(bodyWeightItems);
                            int size = bodyWeightItems == null ? 0 : bodyWeightItems.size();
                            Log.d(TAG, "Save bodyweight size of " + size);
                            String filename = "bodyweight.json";
                            FileIO.writeStorage(jsonString.getBytes(), filename);
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
            }
            return true;
        }
        else if(id == R.id.load_exercises)
        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Load Data from Disk ?").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    {
                        String filename = "exercises.json";
                        String jsonString = new String(FileIO.readStorage(filename));
                        Type listType = new TypeToken<ArrayList<Exercise>>() {
                        }.getType();
                        List<Exercise> exercises = new Gson().fromJson(jsonString, listType);
                        int size = exercises == null ? 0 : exercises.size();
                        Log.d(TAG, "Loading exercises size of " + size);
                        DataManager.add(exercises);
                    }
                    {
                        String filename = "bodyweight.json";
                        String jsonString = new String(FileIO.readStorage(filename));
                        Type listType = new TypeToken<ArrayList<BodyWeightItem>>() {
                        }.getType();
                        List<BodyWeightItem> bodyWeightItems = new Gson().fromJson(jsonString, listType);
                        int size = bodyWeightItems == null ? 0 : bodyWeightItems.size();
                        Log.d(TAG, "Loading bodyweight size of " + size);
                        DataManager.addBodyWeight(bodyWeightItems);
                    }
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }).show();
            return true;
        }
        else if(id == R.id.clear_db)
        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Clear All Data ?").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    DataManager.clearAll();
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.weight) {
            Intent intent = new Intent(this, WeightActivity.class);
            startActivity(intent);
        } else if (id == R.id.exercises) {
            Intent intent = new Intent(this, ExercisesActivity.class);
            startActivity(intent);
        } else if (id == R.id.workout) {
            Intent intent = new Intent(this, WorkoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
