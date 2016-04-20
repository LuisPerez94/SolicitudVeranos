package com.example.luis.solicitudveranos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {


    int duracionMensaje=Toast.LENGTH_SHORT;

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
        final Button registrarme = (Button) findViewById(R.id.buttonRegistro);
        final Button regresar=(Button) findViewById(R.id.buttonRegresarRegistro);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                carrera=(String)parent.getItemAtPosition(position);
                pocision=position;
                Log.v("TAG",pocision+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarme.setEnabled(false);
                Toast mensaje;

                if(nombre.getText().toString().equals("")){

                    mensaje=Toast.makeText(Registro.this,"Debes ingresar tu nombre",duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);

                }else if(password.getText().toString().equals("")){

                    mensaje=Toast.makeText(Registro.this,"Debes ingresar una contraseña",duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);
                }else if(correo.getText().toString().equals("")){

                    mensaje=Toast.makeText(Registro.this,"Debes ingresar un correo válido",duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);
                }else if(matricula.getText().toString().equals("")){

                    mensaje=Toast.makeText(Registro.this,"Debes ingresar tu matricula",duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);
                }else if (pocision!=0) {

                    datosAlumno.put("Nombre", nombre.getText().toString());
                    datosAlumno.put("Password", password.getText().toString());
                    datosAlumno.put("Correo", correo.getText().toString());


                    ref = new Firebase("https://blinding-inferno-2140.firebaseio.com/Alumnos/Carreras/" + carrera + "/" + matricula.getText().toString());
                    //agregamos la información y ponemos un oyente que se fije si la información fue guardada en la base de datos
                    ref.updateChildren(datosAlumno, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                Toast mensaje;
                            if (firebaseError != null) {
                                System.out.println("Data could not be saved. " + firebaseError.getMessage());
                                mensaje=Toast.makeText(Registro.this,"Hubo un error intente despues",duracionMensaje);
                                mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                mensaje.getView().setBackgroundColor(Color.BLUE);
                                mensaje.show();
                                registrarme.setEnabled(true);
                            } else {

                                System.out.println("Data saved successfully.");
                                mensaje=Toast.makeText(Registro.this,"Registro Exitoso!",duracionMensaje);
                                mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                mensaje.getView().setBackgroundColor(Color.argb(54, 23, 32, 12));
                                mensaje.show();
                                //Iniciamos un intent que va a llamar a la actividad "Registro"
                                Intent intent = new Intent(Registro.this, MainActivity.class);
                                //llamamos a la actividad
                                startActivity(intent);
                            }


                        }
                    });



                }else{

                    mensaje=Toast.makeText(Registro.this,"Debes seleccionar una carrrea",duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }
            }
        });
    }
}
