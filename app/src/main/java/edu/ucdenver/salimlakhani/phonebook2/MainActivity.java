package edu.ucdenver.salimlakhani.phonebook2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucdenver.salimlakhani.phonebook2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<Contact> list;
    private ContactAdapter contactAdapter;
    private String type;
    private String ty;

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

        list = new ArrayList<>();
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
        } else if (id == R.id.action_name) {
            type = "name";
            editor.putString("type", type);
            editor.commit();
            contactAdapter.setType(type);
            contactAdapter.notifyDataSetChanged();

        } else if (id == R.id.action_phone) {
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

    public void addContact(Contact contact) {
        list.add(contact);
        Log.i("info", "Contact Name: " + contact.getName() + ", Phone Number: " + contact.getPhone());
        contactAdapter.notifyDataSetChanged();

    }

    public void deletecontact(View view) {
        Contact contact = (Contact) view.getTag();
        int position = list.indexOf(contact);
        if (position != -1) {
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_view_contact, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setView(dialogView);
            builder.setPositiveButton("Close", null); // Add a close button if needed
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.dismiss();
            list.remove(position);
            contactAdapter.notifyItemRemoved(position);
        }
    }


    public void showContactDialog(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_view_contact, null);

        TextView textViewName = dialogView.findViewById(R.id.textViewName);
        TextView textViewPhone = dialogView.findViewById(R.id.textViewPhone);
        TextView textViewEmail = dialogView.findViewById(R.id.textViewEmail);
        TextView textViewStreet = dialogView.findViewById(R.id.textViewStreet);
        TextView textViewCity = dialogView.findViewById(R.id.textViewCity);
        TextView textViewState = dialogView.findViewById(R.id.textViewState);
        TextView textViewZip = dialogView.findViewById(R.id.textViewZip);
        TextView textViewType = dialogView.findViewById(R.id.textViewType);

        textViewName.setText(contact.getName());
        textViewPhone.setText(contact.getPhone());
        textViewEmail.setText(contact.getEmail());
        textViewStreet.setText(contact.getAddress());
        textViewCity.setText(contact.getCity());
        textViewState.setText(contact.getState());
        textViewZip.setText(contact.getZip());
        textViewType.setText(contact.getContacttype());


        Button deleteButton = dialogView.findViewById(R.id.buttonDelete);
        deleteButton.setTag(contact);
        deleteButton.setOnClickListener(this::deletecontact);

        builder.setView(dialogView)
                .setPositiveButton("Close", null)
                .show();
    }

}


