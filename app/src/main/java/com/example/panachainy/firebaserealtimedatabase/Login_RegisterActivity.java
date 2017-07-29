package com.example.panachainy.firebaserealtimedatabase;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEtEmail;
    private EditText mEtPassword;
    private TextInputLayout mLayoutEmail, mLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);

        Initial();
    }

    void Initial() {
        mAuth = FirebaseAuth.getInstance();
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mEtPassword = (EditText) findViewById(R.id.etPassword);

        mLayoutEmail = (TextInputLayout) findViewById(R.id.layoutEmail);
        mLayoutPassword = (TextInputLayout) findViewById(R.id.layoutPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                } else {
                    //User is signed out
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

//    private void SetEvent() {
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RegisterButton();
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Toast.makeText(Login_RegisterActivity.this, "Waiting for do.", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnRegister:
                findViewById(R.id.btnRegister).setEnabled(false);
                RegisterButton();
                findViewById(R.id.btnRegister).setEnabled(true);
                break;
        }
    }


    private void RegisterButton() {
        if (validateForm()) {
            RegisterEmailPass(mEtEmail.getText().toString(), mEtPassword.getText().toString());
        }
//        else {
//            Toast.makeText(Login_RegisterActivity.this, "Fail Validate", Toast.LENGTH_LONG).show();
//        }
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(mEtEmail.getText().toString())) {
            mLayoutEmail.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
            mLayoutPassword.setError("Required.");
            return false;
        } else {
            mLayoutEmail.setError(null);
            mLayoutPassword.setError(null);
            return true;
        }
    }

    private void RegisterEmailPass(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login_RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Login_RegisterActivity.this,
                                    "Authentication Success.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
