package org.pceindicator.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.angmarch.views.NiceSpinner;
import org.pceindicator.com.model.NotificationModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PushNotifications extends AppCompatActivity {
    NiceSpinner spinnerYearNoti,spinnerBranchNoti,spinnerDivNoti;
    EditText message;
    List<String> YEAR,BRANCH,DIVISION;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseUser currUser;
    FirebaseDatabase database;
    DatabaseReference mRef;
    String divison="division",branch="branch",year="year",messageStr,name="Anonymous";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notifications);
        submit = findViewById(R.id.push_notiBtn);
        message = findViewById(R.id.editNoti);
        spinnerYearNoti = findViewById(R.id.spinnerYearNoti);
        spinnerBranchNoti = findViewById(R.id.spinnerBranchNoti);
        spinnerDivNoti = findViewById(R.id.spinnerDivisionNoti);
        YEAR = new LinkedList<>(Arrays.asList("--Choose Year--","First Year","Second Year","Third Year","Fourth Year","All"));
        BRANCH = new LinkedList<>(Arrays.asList("--Choose Branch--","Computer Engineering","Information Technology","Electronics Engineering","Mechanical Engineering","Automobile Engineering","All"));
        DIVISION = new LinkedList<>(Arrays.asList("--Choose Division--","A","B","C","D","E","F","G","H","I","All"));
        spinnerYearNoti.attachDataSource(YEAR);
        spinnerBranchNoti.attachDataSource(BRANCH);
        spinnerDivNoti.attachDataSource(DIVISION);

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        if(currUser != null)
            name = currUser.getDisplayName();
        else
            name = "Anonymous";
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        spinnerYearNoti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: year = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 2: year = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 3: year = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 4: year = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 5: year = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 6: year = parent.getItemAtPosition(position-1).toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBranchNoti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 1:branch = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 2:branch = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 3:branch = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 4:branch = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 5:branch = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 6:branch = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 7:branch = parent.getItemAtPosition(position-1).toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDivNoti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 1: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 2: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 3: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 4: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 5: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 6: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 7: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 8: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 9: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 10: divison = parent.getItemAtPosition(position-1).toString();
                        break;

                    case 11: divison = parent.getItemAtPosition(position-1).toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageStr = message.getText().toString();
                if(year.equals("year")||branch.equals("branch")||divison.equals("division"))
                    Toast.makeText(PushNotifications.this,"Please choose an appropriate option",Toast.LENGTH_SHORT).show();
                else{
                    mRef.child("NEWS").push().setValue(new NotificationModel(messageStr,name,year,branch,divison));
                    Toast.makeText(PushNotifications.this,"Message sent successfully!",Toast.LENGTH_SHORT).show();
                    message.setText("");
                    finish();
                }

            }
        });
    }
}
