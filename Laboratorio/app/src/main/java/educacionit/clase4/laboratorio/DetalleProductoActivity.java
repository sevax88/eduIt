package educacionit.clase4.laboratorio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetalleProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detail);

        if (savedInstanceState == null) {

            int id = getIntent().getIntExtra(DetalleProductoFragment.ID, 0);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.producto_detail_container, DetalleProductoFragment.newInstance(id))
                    .commit();
        }
    }
}
