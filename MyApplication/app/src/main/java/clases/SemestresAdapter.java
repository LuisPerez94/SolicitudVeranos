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
    private HashMap materiasAlumno;
    public SemestresAdapter(Context ctx, HashMap<String, List<Materia>> materias, List<String> semestres,String id,HashMap materiasAlumno) {
        this.ctx = ctx;
        this.materias = materias;
        this.semestres = semestres;
        elecciones=new HashMap<>();
        this.id= id;
        this.materiasAlumno=materiasAlumno;
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
        return materias.get((padre + 1) + "Semestre").get(hijo).getMateria();
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
        final Button button = (Button)convertView.findViewById(R.id.buttonChild);
        button.setText("Elegir!");
        System.out.println("materia1 " + materiasAlumno.get("Materia1") + " Materia 2 " + materiasAlumno.get("Materia2"));
        if((materiasAlumno.get("Materia1").equals("Vacio")) && (materiasAlumno.get("Materia2").equals("Vacio"))){
            opc=0;
        }else{
            if((materiasAlumno.get("Materia1").equals("Vacio")) || (materiasAlumno.get("Materia2").equals("Vacio"))){
                opc=1;
            }else{
                opc=2;
            }

            if ((materiasAlumno.get("Materia1").equals(getGroup(padre)+""+getChild(padre, hijo))) || (materiasAlumno.get("Materia2").equals(getGroup(padre)+""+getChild(padre, hijo)))){
                hijoTextView.setBackgroundColor(Color.CYAN);
                button.setText("Quitar");
            }

        }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // System.out.println("opcion " + opc);

                    if (opc != 2) {
                        if ((materiasAlumno.get("Materia1").equals(getGroup(padre) + "" + getChild(padre, hijo)))) {
                            materiasAlumno.put("Materia1", "Vacio");
                            hijoTextView.setBackgroundColor(Color.TRANSPARENT);
                            button.setText("Elegir!");
                            Toast.makeText(ctx, "Deseleccionada ", 5000).show();
                            opc--;
                        } else {
                            if ((materiasAlumno.get("Materia2").equals(getGroup(padre) + "" + getChild(padre, hijo)))) {
                                materiasAlumno.put("Materia2", "Vacio");
                                hijoTextView.setBackgroundColor(Color.TRANSPARENT);
                                Toast.makeText(ctx, "Deseleccionada ", 5000).show();
                                button.setText("Elegir!");
                                opc--;
                            } else {

                                Toast.makeText(ctx, "Has seleccionado esta materia ", 5000).show();
                                hijoTextView.setBackgroundColor(Color.CYAN);
                                button.setText("Quitar");
                                if (materiasAlumno.get("Materia1").equals("Vacio")) {
                                    materiasAlumno.put("Materia1", (String) getGroup(padre) + getChild(padre, hijo));
                                    opc++;

                                } else {
                                    materiasAlumno.put("Materia2", (String) getGroup(padre) + getChild(padre, hijo));
                                    opc++;
                                }
                                mate.child("Materias").setValue(materiasAlumno);
                            }
                        }
                    } else {

                        if ((materiasAlumno.get("Materia1").equals(getGroup(padre) + "" + getChild(padre, hijo)))) {
                          //  materias.remove("Materia1");
                            materiasAlumno.put("Materia1", "Vacio");
                            hijoTextView.setBackgroundColor(Color.TRANSPARENT);
                            Toast.makeText(ctx, "Deseleccionada ", 5000).show();
                            button.setText("Elegir!");
                            mate.child("Materias").setValue(materiasAlumno);
                            opc--;
                        } else {
                            if ((materiasAlumno.get("Materia2").equals(getGroup(padre) + "" + getChild(padre, hijo)))) {
                            //    materias.remove("Materia2");
                                materiasAlumno.put("Materia2", "Vacio");
                                hijoTextView.setBackgroundColor(Color.TRANSPARENT);
                                Toast.makeText(ctx, "Deseleccionada ", 5000).show();
                                button.setText("Elegir!");
                                mate.child("Materias").setValue(materiasAlumno);
                                opc--;
                            } else {
                                Toast.makeText(ctx, "Ya has elegido dos materias ", 5000).show();

                            }
            }}}});
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
