package edu.ucdenver.salimlakhani.phonebook2;

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
        if (type.equals("name"))
            holder.textViewName.setText(contact.getName());
        else
            holder.textViewName.setText(contact.getPhone());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewName;

        public ListItemHolder (View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewName.setClickable(true);
            textViewName.setOnClickListener(this);


        }

        public void onClick (View view) {
            //Action to perform when user click on this view
        }

    }
}
