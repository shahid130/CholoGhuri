package com.example.chologhuri;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.appcompat.widget.Toolbar;


import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;


public class Blank extends AppCompatActivity {

    FirebaseAuth auth;
    SearchView mySearchView;
    ListView myList;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        auth = FirebaseAuth.getInstance();

        mySearchView =findViewById(R.id.action_search);
        myList = findViewById(R.id.myList);

        list = new ArrayList<String>();

        list.add("Dhaka");
        list.add("Chittagong");
        list.add("Sylhet");
        list.add("Mymensingh");
        list.add("Khulna");
        list.add("Rajshahi");
        list.add("Rangpur");
        list.add("Barishal");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        myList.setAdapter(adapter);
        mySearchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.signout_menu,menu);
        MenuItem menuItem =menu.findItem(R.id.action_search);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.action_profile)
        {
            startActivity(new Intent(Blank.this,Profile.class));

        }
        if(item.getItemId()==R.id.action_signout)
        {
            auth.signOut();
            startActivity(new Intent(Blank.this,Login.class));
        }
        return super.onOptionsItemSelected(item);
    }
}