package edu.ucdenver.salimlakhani.phonebook2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucdenver.salimlakhani.phonebook2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<Contact> list;
    private ContactAdapter contactAdapter;
    private String type;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactDialog addContactDialog = new AddContactDialog();
                addContactDialog.show(getSupportFragmentManager(), "");

            }
        });

        list = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(this, list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        binding.content.recyclerView.setLayoutManager(layoutManager);
        binding.content.recyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.content.recyclerView.setAdapter(contactAdapter);

        prefs = getSharedPreferences("phonebook", Context.MODE_PRIVATE);
        editor = prefs.edit();

        type = prefs.getString("type", "name");
        contactAdapter.setType(type);


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            AddContactDialog addContactDialog = new AddContactDialog();
            addContactDialog.show(getSupportFragmentManager(), "");
        }
        else if (id == R.id.action_name) {
            type = "name";
            editor.putString("type", type);
            editor.commit();
            contactAdapter.setType(type);
            contactAdapter.notifyDataSetChanged();

        }
        else if (id == R.id.action_phone) {
            type = "phone";
            editor.putString("type", type);
            editor.commit();
            contactAdapter.setType(type);
            contactAdapter.notifyDataSetChanged();
        }

            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    public void addContact (Contact contact) {
        list.add (contact);
        Log.i ("info", "Contact Name:" + contact.getName());
        contactAdapter.notifyDataSetChanged();
    }
}