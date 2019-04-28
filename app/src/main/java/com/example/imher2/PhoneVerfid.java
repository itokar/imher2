package com.example.imher2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerfid extends AppCompatActivity {
   private String VerificationId;
   private FirebaseAuth auth;
   private ProgressBar progressBar;
   private EditText    editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verfid);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pro);
        editText    = findViewById(R.id.vrifidecode);
        String PhoneNumber = getIntent().getStringExtra("phoneNumber");
        sandVerifcationCode(PhoneNumber);

        findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6){
                    editText.setError("Enter Code ...");
                    editText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                verfactionCode(code);

            }
        });
    }

    private void verfactionCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(PhoneVerfid.this,NamePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else{
                            Toast.makeText(PhoneVerfid.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void sandVerifcationCode (String Number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback

        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    VerificationId = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if(code != null){

                        progressBar.setVisibility(View.VISIBLE);
                        verfactionCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Toast.makeText(PhoneVerfid.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            };
}