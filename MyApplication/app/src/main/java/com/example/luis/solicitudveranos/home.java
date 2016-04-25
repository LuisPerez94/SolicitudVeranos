package com.example.luis.solicitudveranos;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import clases.Materia;
import clases.ProveedorInformacion;
import clases.SemestresAdapter;

public class home extends AppCompatActivity {

    TextView usuario;
    TextView carrera;


    HashMap<String,List<Materia>> materias;
    List<String> semestres;
    ExpandableListView exp;

    SemestresAdapter adapter;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);       //Firebase necesita esto :v

        c = this;

        String user = getIntent().getExtras().getString("usuario");
        usuario = (TextView) findViewById(R.id.textViewUsuarioHome);
        carrera=(TextView)findViewById(R.id.textViewCarreraHome);
        //Referecia a la base de datos
        final Firebase ref = new Firebase("https://blinding-inferno-2140.firebaseio.com/users/"+user);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                usuario.setText("Bievenido " + snapshot.child("Nombre").getValue());
                carrera.setText("Estas viendo las materias de " + snapshot.child("Carrera").getValue());
                materias = ProveedorInformacion.obtenerInfo((String) snapshot.child("Carrera").getValue());
                System.out.println("Obtuvo informacion");
                semestres = new ArrayList<String>(materias.keySet());
                exp = (ExpandableListView) findViewById(R.id.expListMaterias);
                adapter = new SemestresAdapter(home.this, materias, semestres);
                System.out.println("Sin adapter");
                exp.setAdapter(adapter);
                System.out.println("Con adapter");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });


    }

}
