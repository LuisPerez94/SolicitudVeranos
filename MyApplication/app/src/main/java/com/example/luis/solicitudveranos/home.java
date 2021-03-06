package com.example.luis.solicitudveranos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Handler;

import clases.Materia;
import clases.ProveedorInformacion;
import clases.SemestresAdapter;

public class home extends AppCompatActivity {

    TextView txtNombre;
    TextView txtCarrera;
    TextView mat1;
    TextView mat2;
    static String datos[];
    static Firebase sesion;
    static Firebase info;
    static String id;
    static String carrera;
    static HashMap<String, List<Materia>> materias;
    static ExpandableListView lista;
    static SemestresAdapter adapter;
    static HashMap<String,String> materiasAlumno;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);       //Firebase necesita esto :v
        //datos = (String[])getIntent().getExtras().get("usuario");
        Bundle extras=getIntent().getExtras();
        datos=extras.getStringArray("usuario");
        sesion = new Firebase("https://blinding-inferno-2140.firebaseio.com");
        lista=(ExpandableListView) findViewById(R.id.expListMaterias);
        txtNombre=(TextView) findViewById(R.id.textViewUsuarioHome);
        txtCarrera=(TextView) findViewById(R.id.textViewCarreraHome);
        mat1=(TextView) findViewById(R.id.textViewMat1);
        mat2=(TextView) findViewById(R.id.textViewMat2);

        sesion.authWithPassword(datos[0], datos[1], new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                id = authData.getUid();
                System.out.println("exito" + id);
                leerDatosUser();


            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println("error no me puedo conectar");
            }
        });
        //System.out.println("Correo "+datos[0]+" Contraseña "+datos[1]);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void generarLista(){
        ArrayList <String> semestres=new ArrayList<String>(materias.keySet());
        adapter= new SemestresAdapter(this,materias,semestres,id,materiasAlumno);

        for (int i = 0; i < semestres.size() ; i++) {
            System.out.println(semestres.get(i));
        }
        lista.setAdapter(adapter);
    }
    void leerDatosMaterias() {
        if (carrera != null) {
            ProveedorInformacion.llenar(carrera);

            new CountDownTimer(1000, 1000) {
                public void onFinish() {
                    materias=ProveedorInformacion.obtenerInfo();
                    //System.out.println(materias.keySet().size());
                    generarLista();
                }

                public void onTick(long millisUntilFinished) {
                    System.out.println("seconds remaining: " + millisUntilFinished / 1000);
                }
            }.start();




        } else {
            System.out.println("me llamaron sin nada");
        }
    }

    void leerDatosUser() {
        info = new Firebase("https://blinding-inferno-2140.firebaseio.com/users/" + id);

        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //System.out.println(dataSnapshot.child("Carrera").getValue());
                carrera = (String) dataSnapshot.child("Carrera").getValue();
                materiasAlumno = (HashMap) dataSnapshot.child("Materias").getValue();
                txtNombre.setText("Bienvenido  " + dataSnapshot.child("Nombre").getValue() + "    #control:  " + dataSnapshot.child("Matricula").getValue());
                txtCarrera.setText("Estas viendo la reticula de " + dataSnapshot.child("Carrera").getValue());
                mat1.setText("Materia 1 :"+(String) materiasAlumno.get("Materia1"));
                mat2.setText("Materia 2 :"+(String) materiasAlumno.get("Materia2"));
                leerDatosMaterias();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_salir){
            sesion.unauth();
            Intent intent = new Intent(home.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.luis.solicitudveranos/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.luis.solicitudveranos/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
