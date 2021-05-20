package com.hap.appchat.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hap.appchat.MainActivity;
import com.hap.appchat.R;
import com.hap.appchat.SettingActivity;

import javax.net.ssl.SSLEngineResult;


public class StatusFragment extends Fragment {



    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // Intent intent=new Intent(StatusFragment.this, SettingActivity.class);
       return inflater.inflate(R.layout.fragment_status, container, false);
    }
}