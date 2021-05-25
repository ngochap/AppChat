package com.hap.appchat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.hap.appchat.Adapters.UsersAdapter;
import com.hap.appchat.Model.Users;
import com.hap.appchat.R;
import com.hap.appchat.databinding.ActivitySettingBinding;
import com.hap.appchat.databinding.FragmentChatsBinding;
import com.hap.appchat.databinding.FragmentStatusBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class StatusFragment extends Fragment {


    public StatusFragment() {
        // Required empty public constructor
    }



    FirebaseDatabase database;
    FragmentStatusBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();

        binding.eStatus.getText().toString();
        binding.eUserName.getText().toString();

        HashMap<String, Object> obj = new HashMap<>();
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .updateChildren(obj);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Users users = snapshot.getValue(Users.class);
                        Picasso.get().load(users.getProfilepic())
                                .placeholder(R.drawable.avatar)
                                .into(binding.profileImage);


                        binding.eStatus.setText(users.getStatus());
                        binding.eUserName.setText(users.getUserName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return binding.getRoot();
    }
}