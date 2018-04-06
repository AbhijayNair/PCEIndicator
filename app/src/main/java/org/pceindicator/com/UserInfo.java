package org.pceindicator.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.angmarch.views.NiceSpinner;
import org.pceindicator.com.model.UserModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserInfo extends AppCompatActivity {

    String divison="division",branch="branch",year="year",uid;
    NiceSpinner spinnerYear,spinnerBranch,spinnerDiv;
    FirebaseAuth mAuth;
    List<String> YEAR,BRANCH,DIVISION;
    FirebaseDatabase database;
    SharedPreferences sp;
    DatabaseReference mRef;
    SharedPreferences.Editor editor;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        submit = findViewById(R.id.submit_details);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerBranch = findViewById(R.id.spinnerBranch);
        spinnerDiv = findViewById(R.id.spinnerDivision);
        sp = getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor = sp.edit();
        YEAR = new LinkedList<>(Arrays.asList("--Choose Year--","First Year","Second Year","Third Year","Fourth Year"));
        BRANCH = new LinkedList<>(Arrays.asList("--Choose Branch--","Computer Engineering","Information Technology","Electronics Engineering","Mechanical Engineering","Automobile Engineering"));
        DIVISION = new LinkedList<>(Arrays.asList("--Choose Division--","A","B","C","D","E","F","G","H","I"));
        spinnerYear.attachDataSource(YEAR);
        spinnerBranch.attachDataSource(BRANCH);
        spinnerDiv.attachDataSource(DIVISION);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            uid = currentUser.getUid();
        }
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(year.equals("year")||branch.equals("branch")||divison.equals("division")||year.equals("--Choose Year--")||branch.equals("--Choose Branch--")||divison.equals("--Choose Division--"))
                    Toast.makeText(UserInfo.this,"Please choose an appropriate option",Toast.LENGTH_SHORT).show();
                else {
                    assert currentUser != null;
                    mRef.child("User Info").child(year).child(branch).child(divison).setValue(new UserModel(uid));
                    editor.putString("YEAR",year);
                    editor.putString("BRANCH",branch);
                    editor.putString("DIVISION",divison);
                    editor.apply();
                    startActivity(new Intent(UserInfo.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}
