package com.example.mock_contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelContacts> mlistContacts;

    public ContactsAdapter(Context context, List<ModelContacts> listContacts) {
        this.mContext = context;
        this.mlistContacts = listContacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        layoutInflater = layoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_contacts, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView name, number;

        name = holder.name;
        number = holder.number;

        name.setText(mlistContacts.get(position).getName());
        number.setText(mlistContacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mlistContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, number;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.contact_number);
        }
    }
}