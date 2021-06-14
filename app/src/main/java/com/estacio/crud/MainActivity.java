package com.estacio.crud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

   TextView nome, email, user;
   FirebaseAuth fAuth;
   FirebaseFirestore fStore;
   String userId;

   private CardView financaCard, vistoriaCard, reclamacaoCard, linksCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        financaCard =  (CardView) findViewById(R.id.financas_card);
        vistoriaCard =  (CardView) findViewById(R.id.vistoria_card);
        reclamacaoCard =  (CardView) findViewById(R.id.reclamacao_card);
        linksCard =  (CardView) findViewById(R.id.links_card);


        financaCard.setOnClickListener(this);
        vistoriaCard.setOnClickListener(this);
        reclamacaoCard.setOnClickListener(this);
        linksCard.setOnClickListener(this);


        nome = findViewById(R.id.perfilNome);
        email = findViewById(R.id.profileEmail);
        user = findViewById(R.id.perfilUser);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

  DocumentReference documentReference = fStore.collection("users").document(userId);
    documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
            nome.setText(documentSnapshot.getString("Nome"));
            email.setText(documentSnapshot.getString("Email"));
            user.setText(documentSnapshot.getString("Tipo"));
        }
    });






        Button sair = findViewById(R.id.sairBtn);
        sair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class)
                );
                finish();
            }
        });




    }





    @Override
    public void onClick(View v) {
        Intent telacard;

        switch (v.getId()){
            case R.id.financas_card : telacard = new Intent(this,Financa.class); startActivity(telacard); break;
            case R.id.vistoria_card : telacard = new Intent(this,Vistoria.class); startActivity(telacard); break;
            case R.id.reclamacao_card : telacard = new Intent(this,Reclamacao.class);startActivity(telacard);  break;
            case R.id.links_card : telacard = new Intent(this,Links.class); startActivity(telacard);break;
            default:break;
        }

    }
}