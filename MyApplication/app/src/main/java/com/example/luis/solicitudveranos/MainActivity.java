package com.example.luis.solicitudveranos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView etiqueta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Obtenemos la referencia del elemento boton, con el metodo que lo busca mediante su ID
        final Button registrarme = (Button) findViewById(R.id.registrarme);
        final Button iniciarSesion = (Button) findViewById(R.id.buttonIniciarSesion);
        etiqueta=(TextView) findViewById(R.id.textViewCorreo);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("I", "INICIA SESSION");
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
