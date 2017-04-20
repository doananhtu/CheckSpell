package myapp.anhtu.com.checkspell.activity;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import myapp.anhtu.com.checkspell.R;
import myapp.anhtu.com.checkspell.entity.ContentAdapter;
import myapp.anhtu.com.checkspell.entity.Page;
import myapp.anhtu.com.checkspell.entity.Result;
import myapp.anhtu.com.checkspell.utility.FileUtils;
import myapp.anhtu.com.checkspell.utility.SearchUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lv;
    private ArrayList<Page> arr; // Chứa các trang nội dung
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 6384; // onActivityResult request
    private static String path = null;
    private ContentAdapter adapter;
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
                // Display the file chooser dialog
                showChooser();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT).show();
                Intent searchResultActivity = new Intent(MainActivity.this, SearchResultActivity.class);
                ArrayList<Page> listPage = readFile();
                ArrayList<Result> list = SearchUtils.search(listPage,query);
                searchResultActivity.putExtra("listResult",list);
                startActivity(searchResultActivity);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        if(id == R.id.action_search){
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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            path = FileUtils.getPath(this, uri);
                            Toast.makeText(MainActivity.this, "File Selected: " + path, Toast.LENGTH_LONG).show();
                            //Set text for content main
                            setContentMain();
                        } catch (Exception e) {
                            Log.e("FileSelectorActivity", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void setContentMain(){
        lv = (ListView)findViewById(R.id.ListView);
        arr = readFile();
        adapter = new ContentAdapter(MainActivity.this,R.layout.page,arr);
        lv.setAdapter(adapter);
    }
    protected ArrayList<Page> readFile(){
        arr = new ArrayList<>();
        int i = 0; //line
        int j = 0; //page number
        File file = new File(path);
        if(file.exists()){
            StringBuilder content = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    i++;
                    content.append(line);
                    content.append("\n");
                    // add page
                    if(i>=30){
                        j++;
                        Page page = new Page(String.valueOf(content),j);
                        arr.add(page);
                        i = 0;
                        content.setLength(0);
                    }
                }
                br.close();
                j++;
                Page page = new Page(String.valueOf(content),j);
                arr.add(page);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG,"File not exists!");
        }
        return arr;
    }
    protected String readFile2(){
        if(path == null)
            return "";
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        if(file.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(" ");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG,"File not exists!");
        }
        return String.valueOf(content);
    }
}
