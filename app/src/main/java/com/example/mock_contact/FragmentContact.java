package com.example.mock_contact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentContact extends Fragment {

    private RecyclerView recyclerView;
    private View v;

    public FragmentContact() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      v = inflater.inflate(R.layout.fragment_contacts, container, false);
      recyclerView = v.findViewById(R.id.rv_contacts);

      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

      RecyclerView.LayoutManager layoutManager = linearLayoutManager;
      recyclerView.setLayoutManager(layoutManager);

      ContactsAdapter adapter = new ContactsAdapter(getContext(),getContacts());

      recyclerView.setAdapter(adapter);

      return v;
    }

        private List<ModelContacts> getContacts(){
            List<ModelContacts> list = new ArrayList<>();

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.READ_CONTACTS}, 1);
            }

            Cursor cursor = getContext().getContentResolver().query
                    (ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,null, null,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            cursor.moveToFirst();

            while(cursor.moveToNext()) {
                list.add(new ModelContacts(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            }

            return list;
    }
}