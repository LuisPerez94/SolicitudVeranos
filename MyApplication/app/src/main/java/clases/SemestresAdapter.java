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

import java.util.HashMap;
import java.util.List;

/**
 * Created by Luis on 21/04/2016.
 */
public class SemestresAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private HashMap<String, List<Materia>> materias;
    private List<String> semestres;

    public SemestresAdapter(Context ctx, HashMap<String, List<Materia>> materias, List<String> semestres) {
        this.ctx = ctx;
        this.materias = materias;
        this.semestres = semestres;
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
        return semestres.get(arg0);
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
    public View getChildView(int padre, int hijo, boolean esUltimoHijo, View convertView, ViewGroup padreview) {
        String nombreHijo = (String) getChild(padre, hijo);


            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.hijo,padreview,false);

        final TextView hijoTextView=(TextView)convertView.findViewById(R.id.hijo_txt);
        hijoTextView.setText(nombreHijo);

        Button button = (Button)convertView.findViewById(R.id.buttonChild);
        button.setText("Elegir!");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Has elegido esta materia", 5000).show();
                hijoTextView.setBackgroundColor(Color.CYAN);
            }});
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
