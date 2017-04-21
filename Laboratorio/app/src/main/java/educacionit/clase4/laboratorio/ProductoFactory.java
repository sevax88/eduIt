package educacionit.clase4.laboratorio;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ignacio on 12/08/15.
 */
public class ProductoFactory {

    private static ProductoFactory instance;

    private Dao<Producto, Integer> dao;

    public static ProductoFactory getInstance(Context context){
        if(instance == null)
            instance = new ProductoFactory(context);
        return instance;
    }

    private ProductoFactory(Context context){
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

        try {
            dao = helper.getDao(Producto.class);
        } catch (SQLException e) { }
    }

    public void guardarProductos(List<Producto> productos) throws SQLException {

        for (Producto producto:productos){
            dao.create(producto);
        }
    }

    public void guardarProducto(Producto producto) throws SQLException {
        dao.create(producto);
    }

    public Producto buscarProducto(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<Producto> obtenerProductos() throws SQLException {
        return dao.queryForAll();
    }


    public void eliminarProducto(Producto producto) throws SQLException {
        dao.delete(producto);
    }

    public void borrarTodosLosProductos() throws SQLException {
        dao.deleteBuilder().delete();
    }
}
