package clases;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Luis on 20/04/2016.
 */
public class ProveedorInformacion {

  static  HashMap<String,List<Materia>> todasLasMat=null;

    public static HashMap<String,List<Materia>> obtenerInfo()
    {


        if(todasLasMat!=null) {
            System.out.println("Total de registros" + todasLasMat.size());
            return todasLasMat;
        }else
            return  null;
    }

    public static void llenar(String carrera){
        Firebase ref = new Firebase("https://blinding-inferno-2140.firebaseio.com/Carreras/"+carrera);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {/*
                System.out.println(snapshot.getValue());
                System.out.println(snapshot.getChildrenCount());
                Iterable ite =snapshot.child("1er semestre").getChildren();*/
                long numHijos = snapshot.getChildrenCount();
                System.out.println("Num Semestres"+snapshot.getChildrenCount());
                todasLasMat=new HashMap<String, List<Materia>>();
                for (long i = 0; i < numHijos; i++) {

                    long numMaterias = snapshot.child((i + 1) + "Semestre").getChildrenCount();
                   // System.out.println("Numero de Materias" + numMaterias);
                    List<Materia> materias = new ArrayList<>();

                    for (long j = 0; j < numMaterias; j++) {

                        String materia = (String) snapshot.child((i + 1) + "Semestre").child("M" + (j + 1)).child("Nombre").getValue();
                        String profesor = (String) snapshot.child((i + 1) + "Semestre").child("M" + (j + 1)).child("Maestro").getValue();


                        if ((materia != null) && (profesor != null)) {
                            //System.out.println("entra " + i + " " + j);
                            Materia mat = new Materia(profesor, materia);
                            materias.add(mat);
                            //  System.out.println(mat.getNombre() + mat.getMateria());
                        }
                        //System.out.println("No entra");
                    }


                    //System.out.println("numMAt= " + materias.size());
                    todasLasMat.put((i + 1) + "Semestre", materias);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });
    }
}
