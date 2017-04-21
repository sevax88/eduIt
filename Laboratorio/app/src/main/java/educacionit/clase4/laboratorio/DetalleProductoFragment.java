package educacionit.clase4.laboratorio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.sql.SQLException;

public class DetalleProductoFragment extends Fragment {

    public static final String ID = "id";

    private Producto producto;

    public static DetalleProductoFragment newInstance(int id){

        DetalleProductoFragment frag = new DetalleProductoFragment();

        Bundle args = new Bundle();
        args.putInt(ID, id);

        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ID)) {
            try {
                producto =
                        ProductoFactory.getInstance(getActivity()).buscarProducto(getArguments().getInt(ID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_producto, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView id = (TextView) getView().findViewById(R.id.id);
        id.setText("#" + String.valueOf(producto.getId()));

        TextView descripcion = (TextView) getView().findViewById(R.id.descripcion);
        descripcion.setText(producto.getDescripcion());

        TextView precio = (TextView) getView().findViewById(R.id.precio);
        precio.setText("$" + String.valueOf(producto.getPrecio()));

        ImageView imagen = (ImageView) getView().findViewById(R.id.imagen);
        ImageLoader.getInstance().displayImage(producto.getImagen(), imagen);
    }
}
