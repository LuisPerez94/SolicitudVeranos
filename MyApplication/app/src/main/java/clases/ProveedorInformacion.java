package clases;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Luis on 20/04/2016.
 */
public class ProveedorInformacion {



    public static HashMap<String,List<Materia>> obtenerInfo(String carrera)
    {
        Firebase ref = new Firebase("https://blinding-inferno-2140.firebaseio.com/Carreras/"+carrera);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                System.out.println(snapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });
        HashMap<String,List<Materia>> materias=new HashMap<>();

        return materias;
    }
}
