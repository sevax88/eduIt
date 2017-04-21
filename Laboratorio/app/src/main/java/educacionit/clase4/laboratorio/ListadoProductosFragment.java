package educacionit.clase4.laboratorio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.sql.SQLException;
import java.util.List;

public class ListadoProductosFragment extends Fragment {

    private ProgressBar indicadorProgreso;
    private ListView lista;

    private Callbacks callback;

    public interface Callbacks {
        public void onProductoSelected(Producto producto);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (Callbacks) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.getClass().getName() + " debe implementar la interface Callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado_productos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        indicadorProgreso = (ProgressBar) getView().findViewById(R.id.indicador_progreso);

        lista = (ListView) getView().findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callback.onProductoSelected((Producto) lista.getItemAtPosition(i));
            }
        });

        ocultarIndicadorProgreso();
    }

    public void ocultarIndicadorProgreso(){
        indicadorProgreso.setVisibility(View.GONE);
    }

    public void cargarProductos(){

        ocultarIndicadorProgreso();

        try {
            List<Producto> productos = ProductoFactory.getInstance(getActivity()).obtenerProductos();
            lista.setAdapter(new ProductoAdapter(productos));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
