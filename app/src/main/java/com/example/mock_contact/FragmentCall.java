package com.example.mock_contact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentCall extends Fragment {

    private RecyclerView recyclerView;
    private View v;

    public FragmentCall() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_calls,container,false);
        recyclerView = v.findViewById(R.id.rv_calls);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        CallsAdapter adapter = new CallsAdapter(getContext(), getCallLogs());

        recyclerView.setAdapter(adapter);

        return v;
    }

    private List<ModelCalls> getCallLogs() {

        List<ModelCalls> list = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.READ_CALL_LOG}, 1);
        }

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null,null, null,CallLog.Calls.DATE + " ASC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Date dateModify = new Date(Long.valueOf(cursor.getString(date)));

            String mnth_date, week_day, month;
            mnth_date = (String) DateFormat.format("dd",dateModify);
            week_day = (String) DateFormat.format("EEEE",dateModify);
            month = (String) DateFormat.format("MMMM",dateModify);
            list.add(new ModelCalls(cursor.getString(number), cursor.getString(duration),
                    week_day + " " + mnth_date + " " + month));
        }

        return list;
    }
}