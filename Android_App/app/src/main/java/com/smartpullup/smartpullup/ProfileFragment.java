package com.smartpullup.smartpullup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by bjorn on 1/03/2018.
 */

public class ProfileFragment extends Fragment {
    private static final String TAG = "FragmentProfile";

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
    }

    public void Logout(View v){
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }
}
