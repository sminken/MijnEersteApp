package com.example.myveryfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    // tbv Firedatabase
    private DatabaseReference valueRef;
    private ValueEventListener valueListener;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
		Get database instance;
		- See google-services.json for url: "https://myfirstfirebase-7c235.firebaseio.com"
		 */
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        /*
		Get reference naar Collection "Messages"
		 */
        valueRef = database.getReference("messages");

        // Read from the database
        valueListener = valueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                TextView textView = findViewById(R.id.messages);

                /*
                Obtain all messages
                 */
                Map<String, Object> allMessages = new HashMap<String, Object>();
                allMessages = (Map<String, Object>) dataSnapshot.getValue();

                /*
                Iterate to obtain the text of all messages
                 */
                ArrayList<String> lijstTeksten = new ArrayList<String>();
                String inhoudTextView ="";
                for (Map.Entry<String, Object> entry : allMessages.entrySet()){
                    // verwerk één message
                    Map eenMessage = (Map<String, String>) entry.getValue();
                    String text = (String) eenMessage.get("Text");
                    lijstTeksten.add(text );
                    inhoudTextView = inhoudTextView + text + "\n";

                }
             textView.setText(inhoudTextView);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // fail to read messages
                TextView textView = findViewById(R.id.messages);
                textView.setText("");
            }
        });




    }

    /** Called when the user taps the Send button
     * @param view : the View object that was clicked
     */
    public void sendMessage(View view) {
        /*
        The Intent constructor takes two parameters:
        - A Context as its first parameters (this is used because the Activity class is a subclass
          Context)
          Note: AppCompatActivity is a Base class for activities that use the support library action
          bar features.
        - The Class of the app component to to which the system should deliver the Intent (in
          this case, the activity that should be started).
         */
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        /*
        R.id.editText refers to the id of the textbox, defined in activity_main.xml
         */
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        /*
        Add text to database:
        - generate unique key
        - set value message
         */
        String id = valueRef.push().getKey();
        String naam = "Jansen";
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("Text", message);
        dataToSave.put ("User", naam);
        valueRef.child(id).setValue(dataToSave);


        /*
        The putExtra() method adds the EditText's value to the intent. An Intent can carry data types
         as key-value pairs called extras. Your key is a public constant EXTRA_MESSAGE because the
         next activity uses the key to retrieve the text value. It's a good practice to define keys
         for intent extras using your app's package name as a prefix. This ensures the keys are
         unique, in case your app interacts with other apps.
         */
        intent.putExtra(EXTRA_MESSAGE, message);
        /*
        The startActivity() method starts an instance of the DisplayMessageActivity specified
        by the Intent
         */
        startActivity(intent);

    }


}
