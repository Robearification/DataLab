package com.example.robertgillis.datalab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import model.UserInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private TextView mDisplay;
    private OnUserInfoInteractionListener mCallback;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_menu, container, false);
        ListView userInfo = (ListView) v.findViewById(R.id.user_info);

        List<UserInfo> list =   MainActivity.getUserList(v.getContext());

        ArrayAdapter<UserInfo> adapter = new ArrayAdapter<UserInfo>(v.getContext(),
                android.R.layout.simple_list_item_1
                , android.R.id.text1, list);
        userInfo.setAdapter(adapter);

        userInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback = (OnUserInfoInteractionListener) getActivity();
                mCallback.showUserFragment(i);

            }
        });

        return v;
    }

    public interface OnUserInfoInteractionListener {
        public void showUserFragment(int position);
    }


}
