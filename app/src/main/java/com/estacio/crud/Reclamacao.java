package com.estacio.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Reclamacao extends AppCompatActivity implements View.OnClickListener{


    EditText campoPlaca,campoModelo,campoProblema,campoServico,campoCor;
    Button btnAdd,btnVoltar;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference addforms = db.getReference().child("forms");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamacao);

        campoPlaca = findViewById(R.id.campoPlaca);
        campoModelo = findViewById(R.id.campoModelo);
        campoProblema = findViewById(R.id.campoProblema);
        campoServico = findViewById(R.id.campoServico);
        campoCor = findViewById(R.id.campoCor);
        btnAdd = findViewById(R.id.btnAdd);



        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placa = campoPlaca.getText().toString();
                String modelo = campoModelo.getText().toString();
                String problema = campoProblema.getText().toString();
                String servico = campoServico.getText().toString();
                String cor = campoCor.getText().toString();




                HashMap<String , String> userMap = new HashMap<>();
                userMap.put("placa", placa);
                userMap.put("modelo", modelo);
                userMap.put("problema", problema);
                userMap.put("servico", servico);
                userMap.put("cor", cor);

                addforms.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Reclamacao.this,"Reclamação salva com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(getApplication(),Links.class));
                finish();


            }
        });

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnVoltar) {
            onBackPressed();
        }

    }
}