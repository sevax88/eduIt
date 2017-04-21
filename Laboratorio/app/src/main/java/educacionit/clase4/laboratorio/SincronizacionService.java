package educacionit.clase4.laboratorio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by ignacio on 27/10/15.
 */
public class SincronizacionService extends Service implements Response.Listener<Producto[]>, Response.ErrorListener{

    public static final String SUCCESS_ACTION = "educacionit.laboratorio.SUCCESS";
    public static final String ERROR_ACTION = "educacionit.laboratorio.ERROR";

    static final String URL = "http://www.webkathon.com/pruebasit/products.php";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        GsonRequest gsonRequest = new GsonRequest<>(URL, Producto[].class, null, this, this);

        Volley.newRequestQueue(this).add(gsonRequest);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ERROR_ACTION));

        stopSelf();
    }

    @Override
    public void onResponse(Producto[] response) {

        try {
            ProductoFactory.getInstance(this).borrarTodosLosProductos();

            ProductoFactory.getInstance(this).guardarProductos(Arrays.asList(response));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SUCCESS_ACTION));

        stopSelf();
    }

}
