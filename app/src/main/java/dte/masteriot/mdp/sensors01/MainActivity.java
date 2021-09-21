package dte.masteriot.mdp.sensors01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<MySensor> sensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the reference to the sensor manager:
        MySensor.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensors.add(new MySensor("Light", "Light", Sensor.TYPE_LIGHT, this,
                findViewById(R.id.bLight),
                findViewById(R.id.lightMeasurement)));
        sensors.add(new My3DSensor("Accelerometer", "Accel", Sensor.TYPE_ACCELEROMETER, this,
                findViewById(R.id.bAccel),
                findViewById(R.id.accelMeasurement)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        for (MySensor s : sensors) {
            s.loadState(preferences);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        for (MySensor s : sensors) {
            s.saveState(editor);
        }
        editor.commit();
    }
}
