package educacionit.clase4.laboratorio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.sql.SQLException;
import java.util.List;

public class ListadoProductosActivity extends AppCompatActivity
        implements ListadoProductosFragment.Callbacks {

    private boolean esDoblePanel;

    private SincronizacionReceiver receiver;

    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_list);

        receiver = new SincronizacionReceiver();

        filter = new IntentFilter();
        filter.addAction(SincronizacionService.SUCCESS_ACTION);
        filter.addAction(SincronizacionService.ERROR_ACTION);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        if (findViewById(R.id.producto_detail_container) != null) {
           esDoblePanel = true;
        }

        startService(new Intent(this, SincronizacionService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_listado_productos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_configuration:
                startActivity(new Intent(this, ConfiguracionActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onProductoSelected(Producto producto) {
        if (esDoblePanel) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.producto_detail_container, DetalleProductoFragment.newInstance(producto.getId()))
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, DetalleProductoActivity.class);
            detailIntent.putExtra(DetalleProductoFragment.ID, producto.getId());

            startActivity(detailIntent);
        }
    }

    public class SincronizacionReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(action.equals(SincronizacionService.SUCCESS_ACTION)){
                ListadoProductosFragment frag =
                        (ListadoProductosFragment) getSupportFragmentManager().findFragmentById(R.id.producto_list);

                frag.cargarProductos();
            }else if(action.equals(SincronizacionService.ERROR_ACTION)){

                ListadoProductosFragment frag =
                        (ListadoProductosFragment) getSupportFragmentManager().findFragmentById(R.id.producto_list);

                try {
                    List<Producto> productos = ProductoFactory.getInstance(ListadoProductosActivity.this).obtenerProductos();

                    if(productos.size() > 0){

                        frag.cargarProductos();
                        return;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                frag.ocultarIndicadorProgreso();

                Toast.makeText(ListadoProductosActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();

            }
        }
    }
}
