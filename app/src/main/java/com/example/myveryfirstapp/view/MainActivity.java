package com.example.myveryfirstapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myveryfirstapp.R;
import com.example.myveryfirstapp.domain.Melding;
import com.example.myveryfirstapp.viewmodel.MeldingViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final String GESELECTEERDE_MELDING = "com.example.myfirstapp.GESELECTEERDE_MELDING";

    /*
    Public abstract class FirebaseAuth extends Object
    The entry point of the Firebase Authentication SDK.
     */
    // Firebase Auth instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    // Data user
    private String mUsername;
    public static final String ANONYMOUS = "anonymous";

    /*
        Accessing Google APIs
        When you want to make a call to one of the Google APIs provided in the Google Play services library (such as Google Sign-in and Drive),
        you need to create an instance of one the API client objects, which are subclasses of GoogleApi.
        These objects automatically manage the connection to Google Play services, queueing requests when offline,
        and executing them in order when a connection is available.
        GoogleApi objects are also cheap to create, so you can instantiate them as needed to access Google services.
         */
    private GoogleApiClient mGoogleApiClient;


    private DatabaseReference mFirebaseDatabaseReference;


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

        // Initialize Firebase Auth
        Log.v(TAG, "Initialize Firebase Auth");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Check if user is already signed in
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();

        }

        /*
        Access Google Play services integration
         */
        Log.v(TAG, "Create mGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        /* Create a ViewModel the first time the system calls an activity's onCreate() method.
        Re-created activities receive the same MyViewModel instance created by the first activity.
         */
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

    /*
    Process when signOut button is clicked
     */
    public void signOut(View view) {

        // Signs out the current user and clears it from the disk cache.
        mFirebaseAuth.signOut();

        // Signs out the current signed-in user if any.
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);

        // Set User to ANONYMOUS
        mUsername = ANONYMOUS;

        // Restart signIn
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private void initMeldingRecyclerView(List<Melding> lijstMeldingen) {
        RecyclerView recyclerView = findViewById(R.id.meldingen_recycler_view);
        MeldingRecyclerViewAdapter adapter = new MeldingRecyclerViewAdapter(lijstMeldingen, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /*
    Provides callbacks for scenarios that result in a failed attempt to connect the client to the service.
    See ConnectionResult for a list of error codes and suggestions for resolution.

    Public Method Summary
    abstract void	onConnectionFailed(ConnectionResult result)
                    Called when there was an error connecting the client to the service.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
