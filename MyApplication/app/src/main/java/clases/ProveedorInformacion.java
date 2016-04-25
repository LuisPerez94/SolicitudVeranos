package clases;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Luis on 20/04/2016.
 */
public class ProveedorInformacion {



    public static HashMap<String,List<Materia>> obtenerInfo(String carrera)
    {
        final HashMap<String,List<Materia>> todasLasMat=new HashMap<>();
        Firebase ref = new Firebase("https://blinding-inferno-2140.firebaseio.com/Carreras/"+carrera);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {/*
                System.out.println(snapshot.getValue());
                System.out.println(snapshot.getChildrenCount());
                Iterable ite =snapshot.child("1er semestre").getChildren();*/
                long numHijos = snapshot.getChildrenCount();

                for(long i =0;i<numHijos;i++){
                    long numMaterias=snapshot.child((i+1)+"Semestre").getChildrenCount();
                    List<Materia> materias=new ArrayList<>();
                    for (long j=0;j<numMaterias;j++){
                        String materia=(String)snapshot.child((i+1)+"Semestre").child("M"+(j+1)).child("Nombre").getValue();
                        String profesor=(String)snapshot.child((i+1)+"Semestre").child("M"+(j+1)).child("Profesor").getValue();


                        if((materia!=null) && (profesor!=null)){
                            Materia mat = new Materia(profesor, materia);
                            materias.add(mat);
                        }
                    }

                    todasLasMat.put((i + 1) + "Semestre", materias);
                   /* Iterator it = todasLasMat.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry e = (Map.Entry)it.next();
                        System.out.println(e.getKey() + " " + e.getValue());
                    }*/
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });


        return todasLasMat;
    }
}
