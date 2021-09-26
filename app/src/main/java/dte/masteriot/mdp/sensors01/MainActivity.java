package dte.masteriot.mdp.sensors01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String[] REQUIRED_PERMISSIONS = {
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.VIBRATE,
        };

    List<MySensor> sensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForPermissions();

        // Get the reference to the sensor manager:
        MySensor.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensors.add(new MySensor("Light", "Light", Sensor.TYPE_LIGHT, this,
                findViewById(R.id.bLight),
                findViewById(R.id.lightMeasurement)));
        sensors.add(new NewColorChanger3DSensor("Accelerometer", "Accel", Sensor.TYPE_ACCELEROMETER, this,
                findViewById(R.id.bAccel),
                findViewById(R.id.accelMeasurement)));
        sensors.add(new MyVibratingCounterSensor("Step detector", "Steps", Sensor.TYPE_STEP_DETECTOR, this,
                findViewById(R.id.bStep),
                findViewById(R.id.stepMeasurement)));
        sensors.add(new MyAnimatingSensor("Proximity", "Proxy", Sensor.TYPE_PROXIMITY, this,
                findViewById(R.id.bProxy),
                findViewById(R.id.proxyMeasurement),
                findViewById(R.id.imgHand)));
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

    void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : REQUIRED_PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 1);
                    return;
                }
            }
        }
    }
}
