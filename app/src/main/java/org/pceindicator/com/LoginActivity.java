package org.pceindicator.com;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by ABHIJAY on 3/14/2018.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    Button signIn,GsignIn;
    TextInputEditText emailText,passwordText;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private RelativeLayout coordinatorLayout;
    private ProgressBar pBar,googleBar;
    private static final String TAG = "GoogleActivity";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent exitIntent=getIntent();
        if(exitIntent==null) {
            finish();
        }
        setContentView(R.layout.login_activity);
        signIn = findViewById(R.id.signIN_btn);
        GsignIn = findViewById(R.id.signINGOOGLE_btn);
        signIn.setOnClickListener(this);
        GsignIn.setOnClickListener(this);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
            String string = null;
            String key = currentUser.getEmail();
            loginIntent.putExtra("USER",key);
            startActivity(loginIntent);
//            startActivity(new Intent(Login/Activity.this, MainActivity.class));
            finish();
        }
        pBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        pBar.setMax(100);
        googleBar = findViewById(R.id.googleProgressBar);
        googleBar.setMax(100);
        coordinatorLayout = findViewById(R.id.login_layout);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
            @Override
            public void onStart() {
                super.onStart();

            }
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        GoogleSignInAccount account = result.getSignInAccount();
                        firebaseAuthWithGoogle(account);
                    }
                }
            }

            private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
                coordinatorLayout.setAlpha(0.5f);
                googleBar.setVisibility(View.VISIBLE);
                googleBar.setProgress(50);
                Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    googleBar.setProgress(0);
                                    googleBar.setVisibility(View.GONE);
                                    coordinatorLayout.setAlpha(1);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    Log.d(TAG,"Authentication failed.");
                                    googleBar.setProgress(0);
                                    googleBar.setVisibility(View.GONE);
                                    coordinatorLayout.setAlpha(1);

                                }

                            }
                        });
            }
            private void signIn() {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d(TAG, "onConnectionFailed:" + connectionResult);
                googleBar.setProgress(0);
                googleBar.setVisibility(View.GONE);
                coordinatorLayout.setAlpha(1);
                Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.signINGOOGLE_btn)
                    signIn();
                else if(i == R.id.signIN_btn)
                    signIn();
            }

}

