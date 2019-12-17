package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

public class TaskInfoActivity extends AppCompatActivity implements TaskInfoFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
