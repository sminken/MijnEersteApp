package com.example.myveryfirstapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myveryfirstapp.R;
import com.example.myveryfirstapp.domain.Melding;
import com.example.myveryfirstapp.viewmodel.MeldingViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String INFO_MELDING = "com.example.myfirstapp.MESSAGE";


    // tbv MeldingView
    private MeldingViewModel viewModel = null;



    // tbv Firedatabase
//    private DatabaseReference valueRef;
//    private ValueEventListener valueListener;
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
            // When data is changed in viewmodel: update ui
            public void onChanged(@Nullable List<Melding> lijstMeldingen) {

                String inhoudTextView = "";
                for (Melding melding : lijstMeldingen) {
                    // verwerk één melding
                    inhoudTextView = inhoudTextView + melding.getTekst() + "\n";

                }
                TextView textView = findViewById(R.id.messages);
                textView.setText(inhoudTextView);
            }

        });
    }

    public void maakMelding (View view){

        /*
        R.id.editText refers to the id of the textbox, defined in activity_main.xml
         */
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        Melding nieuweMelding = new Melding ("26-07-2018", "Ron van den Enden", message, "15:08:00");
        viewModel.voegMeldingToe(nieuweMelding);


    }



}
