package dte.masteriot.mdp.sensors01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MySensor sLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the reference to the sensor manager:
        MySensor.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sLight = new MySensor("Light", "Light", Sensor.TYPE_LIGHT, this,
                findViewById(R.id.bLight),
                findViewById(R.id.lightMeasurement));
    }
}
