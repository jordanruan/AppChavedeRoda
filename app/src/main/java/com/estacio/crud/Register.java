package com.estacio.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText registrarNome;
    EditText registrarEmail;
    EditText registrarSenha;
    EditText registrarConfSenha;
    Button registrarUserBtn;
    Button loginBtn;

    RadioGroup rdTipo;
    FirebaseFirestore fStore;
    FirebaseAuth fireAtt;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registrarNome = findViewById(R.id.registerFullName);
        registrarEmail = findViewById(R.id.registerEmail);
        registrarSenha = findViewById(R.id.registerPassword);
        registrarConfSenha = findViewById(R.id.confPassword);
        registrarUserBtn = findViewById(R.id.registrarBtn);
        rdTipo = findViewById(R.id.rdTipoPessoa);
        loginBtn = findViewById(R.id.btnLogin);
        fStore = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });




        registrarUserBtn.setOnClickListener(this);

        fireAtt = FirebaseAuth.getInstance();

    }




    @Override
    public void onClick(View v) {

        String nome = registrarNome.getText().toString();
        String email = registrarEmail.getText().toString();
        String senha = registrarSenha.getText().toString();
        String confsenha = registrarConfSenha.getText().toString();
        int sabor_id = rdTipo.getCheckedRadioButtonId();
        RadioButton rdtipos = findViewById(sabor_id);
        String tipo = rdtipos.getText().toString();

        if(nome.isEmpty()){
            registrarNome.setError("Coloque o nome");
            return;
        }

        if(email.isEmpty()){
            registrarEmail.setError("Coloque o email");
            return;
        }

        if(senha.isEmpty()){
            registrarSenha.setError("Coloque o senha");
            return;
        }


        if(confsenha.isEmpty()){
            registrarConfSenha.setError("Coloque confirmação de senha");
            return;
        }


        if(!senha.equals(confsenha)){
            registrarConfSenha.setError("Senha estão diferentes");
        }


        Toast.makeText(Register.this, "Dados validados", Toast.LENGTH_SHORT).show();

        fireAtt.createUserWithEmailAndPassword(email,senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // enviar user para prox pagina
        userID = fireAtt.getCurrentUser().getUid();
       DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("Nome",nome);
                user.put("Email",email);
                user.put("Tipo",tipo);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Tag", "sucesso"+ userID);
                    }
                });
                startActivity(new Intent(getApplication(),MainActivity.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}