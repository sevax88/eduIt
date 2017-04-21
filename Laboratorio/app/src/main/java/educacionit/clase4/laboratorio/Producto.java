package educacionit.clase4.laboratorio;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ignacio on 13/09/15.
 */
@DatabaseTable(tableName = "productos")
public class Producto {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private String description;

    @DatabaseField
    private double price;

    @DatabaseField
    private String image;

    public Producto(){ }

    public Producto(int id, String nombre, double precio, String apellido) {
        this.id = id;
        this.description = nombre;
        this.price = precio;
        this.image = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String descripcion) {
        this.description = descripcion;
    }

    public double getPrecio() {
        return price;
    }

    public void setPrecio(double precio) {
        this.price = precio;
    }

    public String getImagen() {
        return image;
    }

    public void setImagen(String imagen) {
        this.image = imagen;
    }
}
