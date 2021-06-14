package com.estacio.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button criarBtn,loginBtn,esquecisenhaBtn;

    EditText email,senha;
    AlertDialog.Builder reset_alert;
    FirebaseAuth firebaseAuth;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        inflater = this.getLayoutInflater();



        criarBtn = findViewById(R.id.createAccountBtn);
        criarBtn.setOnClickListener(this);
        reset_alert = new AlertDialog.Builder(this);
        email = findViewById(R.id.loginEmail);
        senha = findViewById(R.id.loginPassword);

        loginBtn = findViewById(R.id.loginButton);
        esquecisenhaBtn = findViewById(R.id.esquecisenhaBtn);

        esquecisenhaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialogo esqueci senha
                View view = inflater.inflate(R.layout.reset_pop,null);
                reset_alert.setTitle("Esqueceu a senha?")
                        .setMessage("Entre com email para pegar sua senha no link")
                        .setPositiveButton("Resetar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // validacao de email
                            EditText email = view.findViewById(R.id.reset_email_pop);
                            if(email.getText().toString().isEmpty()){
                                email.setError("Campo obrigatório");
                                return;
                            }

                                // envia o email para reseta senha

                                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this, "Resete email",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                        }).setNegativeButton("Cancelar", null)
                        .setView(view)
                        .create().show();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PEGAR DADOS
                if(email.getText().toString().isEmpty()){
                    email.setError("Coloque o email");
                    return;
                }

                if(senha.getText().toString().isEmpty()){
                    senha.setError("coloque a senha");
                    return;
                }

    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),senha.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            // login sucesso
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });


            }
        });



    }

    @Override
    public void onClick(View v) {
        Intent tela1 = new Intent(this,Register.class);
        startActivity(tela1);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}