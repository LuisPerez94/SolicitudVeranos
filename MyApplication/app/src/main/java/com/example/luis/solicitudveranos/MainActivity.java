package com.example.luis.solicitudveranos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView correo;
    TextView pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);       //Firebase necesita esto :v
        setTitle("Solicitud de Veranos");
        //Referecia a la base de datos
        final Firebase ref = new Firebase("https://blinding-inferno-2140.firebaseio.com");



        //Obtenemos la referencia del elemento boton, con el metodo que lo busca mediante su ID
        final Button registrarme = (Button) findViewById(R.id.registrarme);
        final Button iniciarSesion = (Button) findViewById(R.id.buttonIniciarSesion);
        correo=(TextView) findViewById(R.id.editTextCorreoLogin);
        pass=(TextView) findViewById(R.id.editTextPassLogin);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //nos autenticamos para meter los datos del JSON a través del oyente de ref
                ref.authWithPassword(correo.getText().toString(), pass.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //Iniciamos un intent que va a llamar a la actividad "Registro"
                        Intent intent = new Intent(MainActivity.this, home.class);
                        intent.putExtra("usuario",authData.getUid());
                        //llamamos a la actividad
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        //MADAMOS A INICIAR SESION
                        System.out.println("ERROR ;D"+firebaseError.getMessage());
                    }
                });
            }
        });

        //evento onclick del boton
        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iniciamos un intent que va a llamar a la actividad "Registro"
                Intent intent = new Intent(MainActivity.this, Registro.class);
                //llamamos a la actividad
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
