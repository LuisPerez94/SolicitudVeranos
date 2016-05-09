package clases;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.solicitudveranos.R;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Luis on 21/04/2016.
 */
public class SemestresAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private HashMap<String, List<Materia>> materias;
    private List<String> semestres;
    private int opc=0;
    private HashMap<String,String> elecciones;
    private String id;
    private Firebase mate;
    public SemestresAdapter(Context ctx, HashMap<String, List<Materia>> materias, List<String> semestres,String id) {
        this.ctx = ctx;
        this.materias = materias;
        this.semestres = semestres;
        elecciones=new HashMap<>();
        this.id= id;
        mate=new Firebase("https://blinding-inferno-2140.firebaseio.com/users/"+id);
    }

    @Override
    public int getGroupCount() {
        return semestres.size();
    }

    @Override
    public int getChildrenCount(int arg0) {
        return materias.get((arg0+1)+"Semestre").size();

    }

    @Override
    public Object getGroup(int arg0) {

        return (arg0+1)+" Semestre";
    }

    @Override
    public Object getChild(int padre, int hijo) {
        return materias.get((padre+1)+"Semestre").get(hijo).getMateria()+"\n"+materias.get((padre+1)+"Semestre").get(hijo).getNombre();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int padre, int hijo) {
        return hijo;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int padre, boolean estaExpandido, View convertView, ViewGroup padreview) {
        String nombreGrupo = (String) getGroup(padre);
        System.out.println(nombreGrupo);
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.padre, padreview, false);


        TextView padreTextview = (TextView) convertView.findViewById(R.id.padre_txt);
        padreTextview.setTypeface(null, Typeface.BOLD);
        padreTextview.setText(nombreGrupo);

        return convertView;
    }

    @Override
    public View getChildView(final int padre, final int hijo, boolean esUltimoHijo, View convertView, ViewGroup padreview) {
        String nombreHijo = (String) getChild(padre, hijo);


            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.hijo,padreview,false);

        final TextView hijoTextView=(TextView)convertView.findViewById(R.id.hijo_txt);
        hijoTextView.setText(nombreHijo);
        if (elecciones.containsKey(getGroup(padre)+""+getChildId(padre, hijo))){
            hijoTextView.setBackgroundColor(Color.CYAN);
        }
        Button button = (Button)convertView.findViewById(R.id.buttonChild);
            button.setText("Elegir!");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("opcion " + opc);
                    if (opc != 2) {
                        if (elecciones.containsKey(getGroup(padre) + "" + getChildId(padre, hijo))) {
                            elecciones.remove(getGroup(padre) + "" + getChildId(padre, hijo));
                            hijoTextView.setBackgroundColor(Color.TRANSPARENT);
                            Toast.makeText(ctx, "Deseleccionada ", 5000).show();
                            opc--;
                        } else {
                            Toast.makeText(ctx, "Has seleccionado esta materia ", 5000).show();
                            hijoTextView.setBackgroundColor(Color.CYAN);
                            elecciones.put((String) getGroup(padre) + getChildId(padre, hijo), (String) getChild(padre, hijo));
                            opc++;
                            mate.child("materias").push().setValue(elecciones);
                        }
                    } else {
                        if (elecciones.containsKey(getGroup(padre) + "" + getChildId(padre, hijo))) {
                            elecciones.remove(getGroup(padre) + "" + getChildId(padre, hijo));
                            hijoTextView.setBackgroundColor(Color.TRANSPARENT);
                            Toast.makeText(ctx, "Deseleccionada ", 5000).show();
                            opc--;
                        } else {
                            Toast.makeText(ctx, "Ya has elegido dos materias ", 5000).show();
                    }
               }
            }});
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
