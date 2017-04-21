package educacionit.clase4.laboratorio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by ignacio on 13/09/15.
 */
public class ProductoAdapter extends BaseAdapter {

    private List<Producto> productos;

    private DisplayImageOptions options;

    public ProductoAdapter(List<Producto> contactos) {
        this.productos = contactos;

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .resetViewBeforeLoading(true)
                .displayer(new RoundedBitmapDisplayer(100))
                .build();
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int i) {
        return productos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productos.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view;

        if(convertView == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_producto, viewGroup, false);
        }else{
            view = convertView;
        }

        Producto producto = (Producto) getItem(i);

        TextView descripcion = (TextView) view.findViewById(R.id.producto_descripcion);
        descripcion.setText(producto.getDescripcion());

        ImageView imagen = (ImageView) view.findViewById(R.id.producto_imagen);
        ImageLoader.getInstance().displayImage(producto.getImagen(), imagen, options);

        return view;
    }
}
