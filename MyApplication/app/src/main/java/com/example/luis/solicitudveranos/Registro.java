package com.example.luis.solicitudveranos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText nombre;
    EditText correo;
    EditText password;
    EditText matricula;
    String carrera;
    Map<String, Object> datosAlumno;
    Firebase ref;
    Spinner spinner;
    int pocision;
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        spinner = (Spinner) findViewById(R.id.spinnerCarreras);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.carreras, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        nombre=(EditText)findViewById(R.id.editTextNombre);
        correo=(EditText)findViewById(R.id.editTextCorreo);
        password=(EditText)findViewById(R.id.editTextPassword);
        matricula=(EditText) findViewById(R.id.editTextMatricula);
        datosAlumno = new HashMap<String, Object>();
        //Obtenemos la referencia del boton "Registrarme" mediante el metodo que lo busca por su ID
        Button registrarme = (Button) findViewById(R.id.buttonRegistro);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                carrera=(String)parent.getItemAtPosition(position);
                pocision=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mensaje;

                if(nombre.getText().toString().equals("")){
                    mensaje=Toast.makeText(Registro.this,"Debes ingresar tu nombre",Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }
                else if(password.getText().toString().equals("")){
                    mensaje=Toast.makeText(Registro.this,"Debes ingresar una contraseña",Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }
                else if(correo.getText().toString().equals("")){
                    mensaje=Toast.makeText(Registro.this,"Debes ingresar un correo válido",Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }
                else if(matricula.getText().toString().equals("")){
                    mensaje=Toast.makeText(Registro.this,"Debes ingresar tu matricula",Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }

                else if (pocision!=0) {

                    datosAlumno.put("Nombre", nombre.getText().toString());
                    datosAlumno.put("Password", password.getText().toString());
                    datosAlumno.put("Correo", correo.getText().toString());


                    ref = new Firebase("https://blinding-inferno-2140.firebaseio.com/Alumnos/Carreras/" + carrera + "/" + matricula.getText().toString());
                    ref.updateChildren(datosAlumno);
                    mensaje=Toast.makeText(Registro.this,"Debes seleccionar una carrrea",Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }else{
                    mensaje=Toast.makeText(Registro.this,"Debes seleccionar una carrrea",Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }
            }
        });
    }
}
