package com.example.testecliente;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;



import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etIdade;
    private ListView listV_dados;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Cliente> listCliente = new ArrayList<Cliente>();
    private ArrayAdapter<Cliente> arrayAdapterClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etIdade = (EditText) findViewById(R.id.etIdade);
        listV_dados = (ListView) findViewById(R.id.listV_dados);


        //Metodos

        inicializarFirebase();





    }





    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
    }

    public void Listar(View view) {


        databaseReference.child("Clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCliente.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Cliente c = objSnapshot.getValue(Cliente.class);
                    listCliente.add(c);
                }
                arrayAdapterClientes = new ArrayAdapter<Cliente>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, listCliente);
                listV_dados.setAdapter(arrayAdapterClientes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void cadastrar(View view) {


        Cliente cli = new Cliente();


        cli.setNome(etNome.getText().toString());
        cli.setEmail(etEmail.getText().toString());
        cli.setIdade(etIdade.getText().toString());

        databaseReference.child("Clientes").push().setValue(cli);

        limparCampos();


    }

    private void limparCampos() {
        etNome.setText("");
        etEmail.setText("");
        etIdade.setText("");
    }


}