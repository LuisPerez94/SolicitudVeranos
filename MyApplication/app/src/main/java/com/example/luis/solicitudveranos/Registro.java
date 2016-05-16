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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registro extends AppCompatActivity {




    //Declaramos un mensaje tipo Toast
     Toast mensaje;
    //Duracion del mensaje que se muestra en caso de error
    int duracionMensaje = Toast.LENGTH_SHORT;

    //Referencias a las vistas
    Button registrarme;
    Button regresar;
    EditText nombre;
    EditText correo;
    EditText password;
    EditText matricula;
    Spinner spinner;    //Combobox
    int pocision;       //Indice del combobox
    String carrera;     //Etiqueta del combobox

    //Objeto json para mandarlo a la db
    Map<String, Object> datosAlumno;

    Map <String,String> materias;


    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);       //Firebase necesita esto :v

         //Referecia a la base de datos
        final Firebase ref = new Firebase("https://blinding-inferno-2140.firebaseio.com");

        //Obtenemos el combobox por su id y le asignamos el adapter
         spinner = (Spinner) findViewById(R.id.spinnerCarreras);
        adapter = ArrayAdapter.createFromResource(this,R.array.carreras, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Obtenemos la referencia del boton "Registrarme" mediante el metodo que lo busca por su ID
        registrarme = (Button) findViewById(R.id.buttonRegistro);
        regresar = (Button) findViewById(R.id.buttonRegresarRegistro);

        //Obtenemos la referencia de las demas vistas
        nombre = (EditText) findViewById(R.id.editTextNombre);
        correo = (EditText) findViewById(R.id.editTextCorreo);
        password = (EditText) findViewById(R.id.editTextPassword);
        matricula = (EditText) findViewById(R.id.editTextMatricula);

        //inicializamos el Json
        datosAlumno = new HashMap<String, Object>();

        //Le asignamos un oyente al boton regresar
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //si se presiona cerramos la actividad
                finish();
            }
        });

        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {

                } else {
                    System.out.println("Dato no agregado");
                }
            }
        });

        //Le agregamos un oyente al combobox
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Si se selecciona algo , obtenemos el indice de la seleccion y la etiqueta
                carrera = (String) parent.getItemAtPosition(position);
                pocision = position;
                //Log.v("TAG", pocision + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    //si no se selecciona nada lo dejamos en 0
                    pocision=0;
            }
        });

        //Le asignamos un oyente al boton registrarme
        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //Si lo presionan
                //Desactivamos el boton registrar para que no lo vuelvan a presionar mientras se da respuesta
                registrarme.setEnabled(false);


                //Validamos que los campos no esten vacios
                if (nombre.getText().toString().equals("")) {

                    mensaje = Toast.makeText(Registro.this, "Debes ingresar tu nombre", duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);

                } else if (password.getText().toString().equals("")) {

                    mensaje = Toast.makeText(Registro.this, "Debes ingresar una contraseña", duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);
                } else if (correo.getText().toString().equals("")) {

                    mensaje = Toast.makeText(Registro.this, "Debes ingresar un correo válido", duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);

                } else if (matricula.getText().toString().equals("")) {

                    mensaje = Toast.makeText(Registro.this, "Debes ingresar tu matricula", duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                   mensaje.getView().setBackgroundColor(Color.BLUE);
                    mensaje.show();
                    registrarme.setEnabled(true);

                } else if (pocision != 0) {  //si si eligio una carrera

                    //Guardamos los datos en el Json
                    datosAlumno.put("Nombre", nombre.getText().toString());
                    datosAlumno.put("Matricula",matricula.getText().toString());
                    datosAlumno.put("Carrera", carrera);
                    materias=new HashMap<String, String>();
                    materias.put("Materia1","Vacio");
                    materias.put("Materia2","Vacio");
                    datosAlumno.put("Materias",materias);

                    //Creamos el usuario con su correo y contraseña y le agregamos un oyente de callback
                    ref.createUser(correo.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {

                        @Override
                        public void onSuccess(Map<String, Object> result) { //si la insercion fue exitosa

                            //nos autenticamos para meter los datos del JSON a través del oyente de ref
                            ref.authWithPassword(correo.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    //MANDAMOS A PAGINA PRINCIPAL
                                    mensaje = Toast.makeText(Registro.this, "Registro Exitoso", duracionMensaje);
                                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    mensaje.getView().setBackgroundColor(Color.WHITE);
                                    mensaje.show();

                                    ref.child("users").child(authData.getUid()).setValue(datosAlumno);
                                    System.out.println("Dato agregado");
                                    //Iniciamos un intent que va a llamar a la actividad "Registro"
                                    String []datos={correo.getText().toString(),password.getText().toString()};
                                    Intent intent = new Intent(Registro.this, home.class);
                                    intent.putExtra("usuario", datos);
                                    //llamamos a la actividad
                                    startActivity(intent);
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                        //MADAMOS A INICIAR SESION
                                }
                            });

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // si el usuario no pudo ser creado, debemos hacer un switch que
                            //deacuerdo a los codigos de error nos muestre un mensaje toast que le diga
                            //al usuario cual fue el motivo del no registro

                            System.out.println("Error" + firebaseError.getMessage());
                            System.out.println("Error"+firebaseError.getDetails());
                        }
                    });

                } else {    //si no , debe escojer una materia

                    mensaje = Toast.makeText(Registro.this, "Debes seleccionar una carrrea", duracionMensaje);
                    mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mensaje.getView().setBackgroundColor(Color.WHITE);
                    mensaje.show();
                }
            }
        });
    }
}
