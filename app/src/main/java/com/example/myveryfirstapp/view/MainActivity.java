package com.example.myveryfirstapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.example.myveryfirstapp.R;
import com.example.myveryfirstapp.domain.Melding;
import com.example.myveryfirstapp.viewmodel.MeldingViewModel;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String INFO_MELDING = "com.example.myfirstapp.MESSAGE";

    // Recycler view
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // tbv MeldingView
    private MeldingViewModel viewModel = null;

    // logging
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        Log.v(TAG, "Create viewModel");
        viewModel = ViewModelProviders.of(this).get(MeldingViewModel.class);

        // attach an observer to the viewmodel methodview
        Log.v(TAG, "Register Observer for viewModel method");

        viewModel.haalMeldingen().observe(this, new Observer<List<Melding>>() {
            @Override
            public void onChanged(@Nullable List<Melding> meldings) {
                initMeldingRecyclerView(meldings);
            }
        });

    }

    public void maakMelding (View view){

        /*
        R.id.editText refers to the id of the textbox, defined in activity_main.xml
         */
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        Date toDay = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String myDateString = sdf.format(toDay);

        Melding nieuweMelding = new Melding (myDateString, "Ron van den Enden", message);
        viewModel.voegMeldingToe(nieuweMelding);


    }


    private void initMeldingRecyclerView(List<Melding> lijstMeldingen) {
        RecyclerView recyclerView = findViewById(R.id.meldingen_recycler_view);
        MeldingRecyclerViewAdapter adapter = new MeldingRecyclerViewAdapter(lijstMeldingen, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



}
