package educacionit.clase4.laboratorio;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;

/**
 * Created by ignacio on 27/10/15.
 */
public class ConfiguracionActivity extends AppCompatActivity {

    static final String PREFS_NAME = "SETTINGS";
    static final String INTERVALO = "INTERVALO";

    private AlarmManager manager;

    private PendingIntent alarmIntent;

    private RadioGroup intervaloAlarma;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuracion);

        preferences = getSharedPreferences(PREFS_NAME, 0);

        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmIntent = PendingIntent.getService(this,
                0,
                new Intent(this, SincronizacionService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        initControls();
        setListeners();
    }

    private void initControls(){

        intervaloAlarma = (RadioGroup) findViewById(R.id.intervalo_alarma);
        intervaloAlarma.check(preferences.getInt(INTERVALO, 0));
    }

    private void setListeners(){

        intervaloAlarma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                manager.cancel(alarmIntent);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(INTERVALO, checkedId);
                editor.commit();

                switch (checkedId){
                    case R.id.quince_minutos:
                        establecerAlarma(15);
                        break;
                    case R.id.treinta_minutos:
                        establecerAlarma(30);
                        break;
                    case R.id.sesenta_minutos:
                        establecerAlarma(60);
                        break;
                    case R.id.ciento_veinte_minutos:
                        establecerAlarma(120);
                        break;

                }
            }
        });
    }

    private void establecerAlarma(int minutos){
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000 * 60 * minutos, 1000 * 60 * minutos, alarmIntent);
    }


}
