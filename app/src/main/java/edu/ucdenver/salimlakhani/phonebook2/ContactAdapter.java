package edu.ucdenver.salimlakhani.phonebook2;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ListItemHolder> {
    private MainActivity mainActivity;
    private ArrayList<Contact> list;

    private String type;

    public ContactAdapter (MainActivity mainActivity, ArrayList<Contact> list) {
        this.mainActivity = mainActivity;
        this.list = list;
        type = "name";

    }

    public void setType (String type) {
        this.type = type;
    }


    @NonNull
    @Override
    public ContactAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from (parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ListItemHolder holder, int position) {
        Contact contact = list.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewphone.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewName;
        private TextView textViewphone;

        public ListItemHolder (View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewphone = itemView.findViewById(R.id.textViewphone);
            textViewphone.setClickable(true);
            textViewphone.setOnClickListener(this);
            textViewName.setClickable(true);
            textViewName.setOnClickListener(this);


        }

        public void onClick (View view) {
            //Action to perform when user click on this view
            int position = getAdapterPosition();
            Contact contact = list.get(position);

            // Inflate the dialog view
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_view_contact, null);

            // Set contact information in the dialog view
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

            // Set the selected radio button based on contact type


            // Create and show the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setView(dialogView);
            builder.setPositiveButton("Close", null); // Add a close button if needed
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        }

    }

